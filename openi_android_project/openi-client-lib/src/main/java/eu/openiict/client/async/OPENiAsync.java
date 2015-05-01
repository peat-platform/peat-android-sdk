package eu.openiict.client.async;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import eu.openiict.client.R;
import eu.openiict.client.api.AuthorizationsApi;
import eu.openiict.client.api.CloudletsApi;
import eu.openiict.client.api.ObjectsApi;
import eu.openiict.client.api.PermissionsApi;
import eu.openiict.client.api.SearchApi;
import eu.openiict.client.async.models.IAuthTokenResponse;
import eu.openiict.client.async.models.ICloudletIdResponse;
import eu.openiict.client.async.models.ICloudletObjectResponse;
import eu.openiict.client.async.models.IListObjectsResponse;
import eu.openiict.client.async.models.ICreateCloudletObjectResult;
import eu.openiict.client.async.models.IOPENiAPiCall;
import eu.openiict.client.async.models.ISearchCloudletsResults;
import eu.openiict.client.async.models.ISearchOneCloudletResults;
import eu.openiict.client.model.OPENiObject;
import eu.openiict.client.utils.OPENiUtils;


/**
 * Created by dmccarthy on 14/11/14.
 */
public final class OPENiAsync {

    private static final String TAG = "OPENiAsync";

    private static OPENiAsync openiAsync;
    private Context context;
    private String  api_key;
    private String  secret;
    private boolean cacheToken;
    private String  authCode;
    private volatile String tmpToken;

    private AuthorizationsApi authorizeApi;
    private CloudletsApi      cloudletsApi;
    private ObjectsApi        objectsApi;
    private SearchApi         searchApi;
    private PermissionsApi    permissionsApi;

    public static final String OPENi_PUBLIC_PREFERENCES   = "OPENi_prefs";
    private static final String OPENi_PREFERENCE_MISSING  = "UNKNOWN";
    private static final String OPENi_PREFERENCE_TOKEN    = "security_token";


    private SharedPreferences prefs;

    private OPENiAsync(String api_key, String secret, Context context) {

        this.secret     = secret;
        this.api_key    = api_key;
        this.context    = context;
        this.prefs      = context.getSharedPreferences(OPENi_PUBLIC_PREFERENCES, Context.MODE_PRIVATE);

        this.authorizeApi   = new AuthorizationsApi();
        this.cloudletsApi   = new CloudletsApi();
        this.searchApi      = new SearchApi();
        this.objectsApi     = new ObjectsApi();
        this.permissionsApi = new PermissionsApi();

        attachMenu(context);
    }


   public static OPENiAsync init(String api_key, String secret, Context context) {
      if (null == openiAsync) {
         openiAsync = new OPENiAsync(api_key, secret, context);
      }

      return openiAsync;
   }


   public void setIgnoreSSL(boolean ignoreSSL){

      if (null == openiAsync){
         throw new RuntimeException("OPENiAsync object hasn't been created, call init method first.");
      }
      this.authorizeApi.getInvoker().ignoreSSLCertificates(ignoreSSL);
      this.cloudletsApi.getInvoker().ignoreSSLCertificates(ignoreSSL);
      this.searchApi.getInvoker().ignoreSSLCertificates(ignoreSSL);
      this.objectsApi.getInvoker().ignoreSSLCertificates(ignoreSSL);
      this.permissionsApi.getInvoker().ignoreSSLCertificates(ignoreSSL);

   }


   public void setCacheToken(boolean cacheToken){

      if (null == openiAsync){
         throw new RuntimeException("OPENiAsync object hasn't been created, call init method first.");
      }
      this.cacheToken = cacheToken;

   }


   public void attachMenu(final Context activity) {

      final SlidingMenu menu = new SlidingMenu(activity);
      menu.setMode(SlidingMenu.LEFT);
      menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
      menu.setFadeDegree(0.35f);
      menu.attachToActivity((Activity) activity, SlidingMenu.SLIDING_CONTENT);
      menu.setMenu(R.layout.menu);
      menu.setSlidingEnabled(true);

      final Button logout = (Button) menu.findViewById(R.id.logoutOfOPENi);

      logout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            OPENiAsync.instance(activity).logout();
         }
      });

      final Button dash = (Button) menu.findViewById(R.id.openUserDash);

      final String basePathURL = cloudletsApi.getBasePath().replace("/api/v1", "") ;

      dash.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(basePathURL + "/user/login"));
            activity.startActivity(browserIntent);
         }
      });

      final Button closeButton = (Button) menu.findViewById(R.id.closeOpeniMenu);

      closeButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            menu.toggle();
         }
      });

   }


    public static OPENiAsync instance(Context context) {

        if (null == openiAsync) {
            throw new RuntimeException("OPENiAsync object hasn't been created, call init method first.");
        }

        openiAsync.context = context;

        openiAsync.attachMenu((Activity) context);

        return openiAsync;
    }



   private void openAuthDialog(final IAuthTokenResponse authTokenResponse) {

      if (null != authCode && isTokenValid(authCode)){
         authTokenResponse.onSuccess(authCode);
         return;
      }

      final Dialog auth_dialog = new Dialog(context, eu.openiict.client.R.style.full_screen_dialog){
         @Override
         protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(eu.openiict.client.R.layout.auth_dialog);
            getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT,
                  WindowManager.LayoutParams.FILL_PARENT);
         }
      };

      auth_dialog.show();

      final WebView web = (WebView) auth_dialog.findViewById(eu.openiict.client.R.id.webv);

      if (Build.VERSION.SDK_INT >= 19) {
         web.setWebContentsDebuggingEnabled(true);
         web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
      }
      else {
         web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
      }

      web.getSettings().setJavaScriptEnabled(true);
      // TODO: get server ip dynamically

      //final String basePathURL = cloudletsApi.getBasePath().replace("/api/v1", "").replaceAll("https:", "http:");
      final String basePathURL = cloudletsApi.getBasePath().replace("/api/v1", "") ;
      //final String basePathURL = "https://demo2.openi-ict.eu";
      //final String URI        = basePathURL + "/auth/account?api_key=" + OPENI_API_KEY + "&secret=" + OPENI_SECRET_KEY + "&redirectURL=" + "http://localhost";

      final String URI        = basePathURL + "/auth/account?api_key=" + api_key + "&secret=" + secret + "&redirectURL=" + "http://localhost";

      web.setWebViewClient(new WebViewClient() {

         boolean authComplete = false;
         //Intent resultIntent = new Intent();

         @Override
         public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

            handler.proceed();

            Log.d(TAG, "SSL Error " + handler.toString());
            Log.d(TAG, "SSL Error " + error.toString());
         }

         @Override
         public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.e(TAG, "onPageStarted = " + url);
            super.onPageStarted(view, url, favicon);
         }

         @Override
         public void onReceivedError(WebView view, int errorCode,
                                     String description, String failingUrl) {
            Log.e(TAG, "onReceivedError = " + errorCode);
            Log.e(TAG, "onReceivedError = " + description);
            Log.e(TAG, "onReceivedError = " + failingUrl);

            //404 : error code for Page Not found
            if (errorCode == -2) {
               authTokenResponse.onFailure("error");
            }
            else if (failingUrl.contains("ERROR=error_cancelled")) {
               Log.i(TAG, "USER_CANCELLED_HERE");
               //resultIntent.putExtra("code", authCode);
               authComplete = true;
                   /*this.setResult(Activity.RESULT_CANCELED, resultIntent);*/
               //Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
               authTokenResponse.onAppPermsCancelled("App Permissions Cancelled");
               auth_dialog.dismiss();
            }
            else if (failingUrl.contains("ERROR=error_permissions")) {
               Log.i(TAG, "ACCESS_DENIED_HERE");
               //resultIntent.putExtra("code", authCode);
               authComplete = true;
                   /*this.setResult(Activity.RESULT_CANCELED, resultIntent);*/
               //Toast.makeText(context, "Error Occured", Toast.LENGTH_SHORT).show();
               authTokenResponse.onAppPermsDenied("App Permissions Denied");
               auth_dialog.dismiss();
            }
         }


         @Override
         public void onPageFinished(WebView view, String url) {
            Log.e(TAG, "onPageFinished = " + url);
            super.onPageFinished(view, url);
            //auth_dialog.show();
            Log.e(TAG, "show dialog = " + URI);
            Log.e(TAG, "show dialog = " + url);
            Log.e(TAG, "show dialog = " + URI.equals(url));

            if (url.contains("?OUST=") && url.startsWith("http://localhost") && authComplete != true) {
               final Uri uri = Uri.parse(url);
               authCode = uri.getQueryParameter("OUST");

               if (!cacheToken){
                  tmpToken = authCode;
               }
               else {
                  setPref(OPENi_PREFERENCE_TOKEN, authCode);
               }
               Log.i(TAG, "CODE : " + authCode);
               authComplete = true;
               authTokenResponse.onSuccess(authCode);
               auth_dialog.dismiss();
            }
            else if (URI.equals(url) || url.contains("data:text/html,chromewebdata")) {
               Log.e(TAG, "show dialog = " + url);
               if (!authComplete){
                  auth_dialog.show();
               }
            }
         }
      });

      web.setBackgroundColor(Color.parseColor("#323234"));

      web.loadUrl(URI);

      Log.d(TAG, URI);
   }


    public void setPref(String key, String value) {
        final SharedPreferences.Editor edit = this.prefs.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public void setPrefBool(String key, Boolean value) {
        final SharedPreferences.Editor edit = this.prefs.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public String getPref(String key) {

       if (!cacheToken){
          if (null != tmpToken){
             return tmpToken;
          }
          return OPENi_PREFERENCE_MISSING;
       }

        Log.d(TAG, this.prefs.getAll().toString());
        return this.prefs.getString(key, OPENi_PREFERENCE_MISSING);
    }

    public Boolean getPrefBool(String key) {

        Log.d(TAG, this.prefs.getAll().toString());
       if (!cacheToken){
          return true;
       }
        return this.prefs.getBoolean(key, true);
    }

    public void logout() {
        //final Dialog auth_dialog = new Dialog(context);
        //auth_dialog.setContentView(R.layout.auth_dialog);
        final SharedPreferences.Editor edit = this.prefs.edit();
        final WebView web = new WebView(context);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(true);
//        }
//       if (Build.VERSION.SDK_INT >= 19) {
//          web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//       }
//       else {
//          web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//       }
         web.getSettings().setJavaScriptEnabled(true);
         // TODO: get server ip dynamically
         final String basePathURL = cloudletsApi.getBasePath().replace("/api/v1", "");
         web.loadUrl(basePathURL +  "/auth/logout");

         web.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Log.e(TAG, "onReceivedError = " + errorCode);
                //404 : error code for Page Not found
                if (errorCode == -2) {
                    Toast.makeText(context, "Error: Couldn't Log Out", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                edit.clear();
                edit.commit();
                Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

    }


   public void getCloudletObject(final String cloudletId, final String objectid, final ICloudletObjectResponse iCloudletObjectResponse) {

      openiConnect(new IAuthTokenResponse() {
         @Override
         public void onSuccess(String authToken) {
            new AsyncGetCloudletObjectOperation(objectsApi, cloudletId, objectid, true, authToken, iCloudletObjectResponse).execute();
         }


         @Override
         public void onAppPermsDenied(String error) {
         }


         @Override
         public void onAppPermsCancelled(String perms) {
            Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG);
         }


         @Override
         public void onFailure(String error) {
            iCloudletObjectResponse.onFailure();
         }
      });

   }


   public void getLatestObject(final String type, final ICloudletObjectResponse iCloudletObjectResponse) {

      openiConnect(new IAuthTokenResponse() {
         @Override
         public void onSuccess(String authToken) {
            new AsyncGetLatestObjectOperation(objectsApi, type, authToken, iCloudletObjectResponse).execute();
         }

         @Override
         public void onAppPermsDenied(String error) {
            iCloudletObjectResponse.onFailure();
         }

         @Override
         public void onAppPermsCancelled(String perms) {
            Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG);
            iCloudletObjectResponse.onFailure();
         }

         @Override
         public void onFailure(String error) {
            iCloudletObjectResponse.onFailure();
         }
      });

   }


   public void getCloudletObject(final String objectid, final ICloudletObjectResponse iCloudletObjectResponse) {

      openiConnect(new IAuthTokenResponse() {
         @Override
         public void onSuccess(String authToken) {
            new AsyncGetCloudletObjectOperation(objectsApi, objectid, true, authToken, iCloudletObjectResponse).execute();
         }

         @Override
         public void onAppPermsDenied(String error) {
            iCloudletObjectResponse.onFailure();
         }

         @Override
         public void onAppPermsCancelled(String perms) {
            Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG);
            iCloudletObjectResponse.onFailure();
         }

         @Override
         public void onFailure(String error) {
            iCloudletObjectResponse.onFailure();
         }
      });

   }


   public void listCloudletObjects(final Integer offset, final Integer limit, final String type,
                                   final Boolean id_only, final String with_property,
                                   final String property_filter, final String only_show_properties,
                                   final IListObjectsResponse iListObjectsResponse) {

      openiConnect(new IAuthTokenResponse() {
         @Override
         public void onSuccess(String authToken) {
            new AsyncListCloudletObjectsOperation(objectsApi, offset, limit, type, id_only,
                  with_property, property_filter, only_show_properties, authToken, "ascending",
                  iListObjectsResponse).execute();
         }

         @Override
         public void onAppPermsDenied(String error) {
            iListObjectsResponse.onFailure();
         }

         @Override
         public void onAppPermsCancelled(String perms) {
            Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG);
         }

         @Override
         public void onFailure(String error) {
            iListObjectsResponse.onFailure();
         }
      });

   }

    public void getCloudletID(final ICloudletIdResponse cloudletIdResponse){
        openiConnect(new IAuthTokenResponse() {
           @Override
           public void onSuccess(String authToken) {
              new AsyncGetCloudletOperation(cloudletsApi, authToken, cloudletIdResponse).execute();
           }

           @Override
           public void onAppPermsCancelled(String perms) {
              Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG);
           }
           @Override
           public void onAppPermsDenied(String perms) {
              cloudletIdResponse.onFailure();
           }

           @Override
           public void onFailure(String error) {
              cloudletIdResponse.onFailure();
           }
        });
    }


   public boolean isTokenValid(String prefTokenValue) {

      try {
//         Log.d(TAG, "prefTokenValue " + prefTokenValue);
//         Log.d(TAG, "prefTokenValue " + prefTokenValue.split("\\."));
//         Log.d(TAG, "(prefTokenValue.split(\".\"))[1] " + (prefTokenValue.split("\\."))[1]);

         final byte[] data = Base64.decode((prefTokenValue.split("\\."))[1]);
         final String text = new String(data, "UTF-8");

//         Log.d(TAG, "text " + text);

         final JSONObject jo = new JSONObject(text);

//         Log.d(TAG, "jo " + jo);

         final long expTime = jo.getLong("exp");
//         Log.d(TAG, "expTime " + expTime);
//         Log.d(TAG, "deviceT " + System.currentTimeMillis()/1000);

         if (expTime >= System.currentTimeMillis()/1000){
            return true;
         }

         return false;
      }
      catch (UnsupportedEncodingException e) {
         e.printStackTrace();
         return false;
      }
      catch (JSONException e) {
         e.printStackTrace();
         return false;
      }
   }


   private void openiConnect(final IAuthTokenResponse authTokenResponse) {

      final String prefTokenValue = getPref(OPENi_PREFERENCE_TOKEN);
      Log.d(TAG, "" + prefTokenValue);

      if (OPENi_PREFERENCE_MISSING.equals(prefTokenValue)) {
         Log.d(TAG, "token not in prefs");
         //kick off login process
         openAuthDialog(authTokenResponse); // TODO : open AuthMainActitity
      } else {
         Log.d(TAG, "token in prefs");

         if (isTokenValid(prefTokenValue)){
            Log.d(TAG, "Valid Token, proceed: " );
            authTokenResponse.onSuccess(prefTokenValue);
         }
         else {
            openAuthDialog(authTokenResponse); // TODO : open AuthMainActitity
         }
      }
   }


    public void searchInAllCloudlets(final String with_property, final String property_filter, final String type, final Boolean id_only, final String offset, final String limit, final ISearchCloudletsResults searchCloudletsResults) {

        openiConnect(new IAuthTokenResponse() {
           @Override
           public void onSuccess(String authToken) {
              new AsyncSearchCloudletsOperation(searchApi, with_property, property_filter, type, id_only, offset, limit, authToken, searchCloudletsResults).execute();
           }

           @Override
           public void onAppPermsCancelled(String perms) {
              Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG);
           }

           @Override
           public void onAppPermsDenied(String error) {
              searchCloudletsResults.onFailure();
           }

           @Override
           public void onFailure(String error) {
              searchCloudletsResults.onFailure();
           }
        });
    }

    public void searchInOneCloudlet(final String cloudletId, final String with_property, final String property_filter, final String type, final Boolean id_only, final String only_show_properties, final Integer offset, final Integer limit, final ISearchOneCloudletResults searchCloudletResults) {

        openiConnect(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                new AsyncSearchOneCloudletOperation(objectsApi, cloudletId, with_property, property_filter, type, id_only, only_show_properties, offset, limit, authToken, searchCloudletResults).execute();
            }

            @Override
            public void onAppPermsCancelled(String perms) {
              Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG);
            }

            @Override
            public void onAppPermsDenied(String error) {
                searchCloudletResults.onFailure();
            }

            @Override
            public void onFailure(String error) {
                searchCloudletResults.onFailure();
            }
        });
    }


   public void createCloudletObj(final String typeId, final JSONObject jo, final ICreateCloudletObjectResult IcreateCloudletObject) {
      openiConnect(new IAuthTokenResponse() {
         @Override
         public void onSuccess(String authToken) {
            Log.d(TAG, "Create Object");
            final OPENiObject oo = new OPENiObject();
            oo.setOpeniType(typeId);
            try {
               oo.setData(OPENiUtils.jsonObjectToMap(jo));
               new AsyncCreateCloudletObjectOperation(objectsApi, oo, authToken, IcreateCloudletObject).execute();
            } catch (JSONException e) {
              onFailure(e.getMessage());
            }
         }

         @Override
         public void onAppPermsCancelled(String perms) {
            Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG).show();
         }

         @Override
         public void onAppPermsDenied(String error) {
            IcreateCloudletObject.onFailure();
         }

         @Override
         public void onFailure(String error) {
            IcreateCloudletObject.onFailure();
         }
      });
   }


    public void createCloudletObj(final OPENiObject objectBody, final ICreateCloudletObjectResult IcreateCloudletObject) {
        openiConnect(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                Log.d(TAG, "Create Object");
                new AsyncCreateCloudletObjectOperation(objectsApi, objectBody, authToken, IcreateCloudletObject).execute();
            }

            @Override
            public void onAppPermsCancelled(String perms) {
              Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAppPermsDenied(String error) {
                IcreateCloudletObject.onFailure();
            }

            @Override
            public void onFailure(String error) {
                IcreateCloudletObject.onFailure();
            }
        });
    }


    public void execOpeniApiCall(final IOPENiAPiCall iOPENiApiCall) {

        openiConnect(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                Log.d(TAG, authToken);
                new AsyncOpeniNetworkOperation(iOPENiApiCall, authToken).execute();
            }

            @Override
            public void onAppPermsCancelled(String perms) {
              Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG);
            }

            @Override
            public void onAppPermsDenied(String error) {
                iOPENiApiCall.onFailure();
            }

            @Override
            public void onFailure(String error) {
                iOPENiApiCall.onFailure();
            }
        });

    }

}
