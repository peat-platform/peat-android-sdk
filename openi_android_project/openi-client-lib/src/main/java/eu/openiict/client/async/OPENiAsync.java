package eu.openiict.client.async;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import eu.openiict.client.R;
import eu.openiict.client.api.AuthorizationsApi;
import eu.openiict.client.api.CloudletsApi;
import eu.openiict.client.api.ObjectsApi;
import eu.openiict.client.api.PermissionsApi;
import eu.openiict.client.api.SearchApi;
import eu.openiict.client.async.models.IAuthTokenResponse;
import eu.openiict.client.async.models.ICloudletIdResponse;
import eu.openiict.client.async.models.ICloudletObjectCall;
import eu.openiict.client.async.models.ICloudletObjectsCall;
import eu.openiict.client.async.models.ICreateCloudletObjectResult;
import eu.openiict.client.async.models.IOPENiAPiCall;
import eu.openiict.client.async.models.ISearchCloudletsResults;
import eu.openiict.client.async.models.ISearchOneCloudletResults;
import eu.openiict.client.model.OPENiObject;


/**
 * Created by dmccarthy on 14/11/14.
 */
public class OPENiAsync {

    private static final String TAG = "OPENiAsync";

    private static OPENiAsync openiAsync;
    private Context context;
    private String api_key;
    private String secret;

    private AuthorizationsApi authorizeApi;
    private CloudletsApi      cloudletsApi;
    private ObjectsApi        objectsApi;
    private SearchApi         searchApi;
    private PermissionsApi    permissionsApi;

    public static final String OPENi_PUBLIC_PREFERENCES   = "OPENi_prefs";
    private static final String OPENi_PREFERENCE_MISSING  = "UNKNOWN";
    private static final String OPENi_PREFERENCE_TOKEN    = "security_token";


    private SharedPreferences prefs;

    private OPENiAsync(String api_key, String secret, Context context, boolean ignoreSSL) {
        this.secret = secret;
        this.api_key = api_key;
        this.context = context;
        this.prefs = context.getSharedPreferences(OPENi_PUBLIC_PREFERENCES, Context.MODE_PRIVATE);


        this.authorizeApi   = new AuthorizationsApi();
        this.cloudletsApi   = new CloudletsApi();
        this.searchApi      = new SearchApi();
        this.objectsApi     = new ObjectsApi();
        this.permissionsApi = new PermissionsApi();

        this.authorizeApi.getInvoker().ignoreSSLCertificates(ignoreSSL);
        this.cloudletsApi.getInvoker().ignoreSSLCertificates(ignoreSSL);
        this.searchApi.getInvoker().ignoreSSLCertificates(ignoreSSL);
        this.objectsApi.getInvoker().ignoreSSLCertificates(ignoreSSL);
        this.permissionsApi.getInvoker().ignoreSSLCertificates(ignoreSSL);
    }

    public static void init(String api_key, String secret, Context context, boolean ignoreSSL) {

        if (null == openiAsync) {
            openiAsync = new OPENiAsync(api_key, secret, context, ignoreSSL);
        }

    }

    public static OPENiAsync instance(Context context) {

        if (null == openiAsync) {
            throw new RuntimeException("OPENiAsync object hasn't been created, call initOPENiAsync(String clientId, Context context) first.");
        }
        if (context != null) {
           openiAsync.context = context;
        }
        return openiAsync;
    }


    private void openAuthDialog(final IAuthTokenResponse authTokenResponse) {

        final Dialog auth_dialog = new Dialog(context);
        auth_dialog.setContentView(R.layout.auth_dialog);
        final WebView web = (WebView) auth_dialog.findViewById(R.id.webv);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(true);
//        }

        if (Build.VERSION.SDK_INT >= 19) {
          web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
          web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // TODO: get server ip dynamically

       //final String basePathURL = cloudletsApi.getBasePath().replace("/api/v1", "").replaceAll("https:", "http:");
        final String basePathURL = cloudletsApi.getBasePath().replace("/api/v1", "") ;
        final String URI        = basePathURL + "/auth/account?api_key=" + api_key + "&secret=" + secret + "&redirectURL=" + "http://localhost";

        web.setWebViewClient(new WebViewClient() {

           boolean authComplete = false;
           //Intent resultIntent = new Intent();
           String authCode;

           @Override
           public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
              super.onReceivedSslError(view, handler, error);
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


              if (URI.equals(url) || url.contains("data:text/html,chromewebdata")) {
                 Log.e(TAG, "show dialog = " + url);
                 auth_dialog.show();
              }
              else if (url.contains("?OUST=") && url.startsWith("http://localhost") && authComplete != true) {
                 final Uri uri = Uri.parse(url);
                 authCode = uri.getQueryParameter("OUST");

                 setPref(OPENi_PREFERENCE_TOKEN, authCode);
                 Log.i(TAG, "CODE : " + authCode);
                 authComplete = true;
                 authTokenResponse.onSuccess(authCode);
                 auth_dialog.dismiss();
              }
              else {
                 //auth_dialog.dismiss();
                 //authTokenResponse.onFailure("error");
              }
           }
        });

       web.loadUrl(URI);

       Log.d(TAG, URI);
        auth_dialog.setTitle("OPENi");
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

        Log.d(TAG, this.prefs.getAll().toString());
        return this.prefs.getString(key, OPENi_PREFERENCE_MISSING);
    }

    public Boolean getPrefBool(String key) {

        Log.d(TAG, this.prefs.getAll().toString());
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
       if (Build.VERSION.SDK_INT >= 19) {
          web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
       }
       else {
          web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
       }
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


   public void getCloudletObject(final String cloudletId, final String objectid, final ICloudletObjectCall iCloudletObjectCall) {

      openiConnect(new IAuthTokenResponse() {
         @Override
         public void onSuccess(String authToken) {
            new AsyncGetCloudletObjectOperation(objectsApi, cloudletId, objectid, true, authToken, iCloudletObjectCall).execute();
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
            iCloudletObjectCall.onFailure();
         }
      });

   }

   public void getCloudletObject(final String objectid, final ICloudletObjectCall iCloudletObjectCall) {

      openiConnect(new IAuthTokenResponse() {
         @Override
         public void onSuccess(String authToken) {
            new AsyncGetCloudletObjectOperation(objectsApi, objectid, true, authToken, iCloudletObjectCall).execute();
         }

         @Override
         public void onAppPermsDenied(String error) {
            iCloudletObjectCall.onFailure();
         }

         @Override
         public void onAppPermsCancelled(String perms) {
            Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG);
            iCloudletObjectCall.onFailure();
         }

         @Override
         public void onFailure(String error) {
            iCloudletObjectCall.onFailure();
         }
      });

   }


   public void getCloudletObjects(final Integer offset, final Integer limit, final String type,
                                  final Boolean id_only, final String with_property,
                                  final String property_filter, final String only_show_properties,
                                  final String auth, final ICloudletObjectsCall iCloudletObjectsCall) {

      openiConnect(new IAuthTokenResponse() {
         @Override
         public void onSuccess(String authToken) {
            new AsyncGetCloudletObjectsOperation(objectsApi, offset, limit, type, id_only,
                  with_property, property_filter, only_show_properties, auth,
                  iCloudletObjectsCall).execute();
         }

         @Override
         public void onAppPermsDenied(String error) {
            iCloudletObjectsCall.onFailure();
         }

         @Override
         public void onAppPermsCancelled(String perms) {
            Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG);
         }

         @Override
         public void onFailure(String error) {
            iCloudletObjectsCall.onFailure();
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

    public void checkTokenValidity(final ICloudletIdResponse iTokenValidity) {

        final String prefTokenValue = getPref(OPENi_PREFERENCE_TOKEN);
        new AsyncGetCloudletOperation(cloudletsApi, prefTokenValue, iTokenValidity).execute();

    }

    public void openiConnect(final IAuthTokenResponse authTokenResponse) {

        final String prefTokenValue = getPref(OPENi_PREFERENCE_TOKEN);
        Log.d(TAG, "" + prefTokenValue);

        if (OPENi_PREFERENCE_MISSING.equals(prefTokenValue)) {
            Log.d(TAG, "token not in prefs");
            //kick off login process
            openAuthDialog(authTokenResponse); // TODO : open AuthMainActitity
        } else {
            Log.d(TAG, "token in prefs");
            // check token validity TODO use timestamps too
            checkTokenValidity(new ICloudletIdResponse() {
                @Override
                public void onSuccess(String cloudletid) {
                    Log.d(TAG, "Got cloudletid: " + cloudletid);
                    authTokenResponse.onSuccess(prefTokenValue);
                }

                @Override
                public void onFailure() {
                    Log.d(TAG, "Not valid token in prefs");
                    openAuthDialog(authTokenResponse); // TODO : open AuthMainActitity
                }
            });
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


    public void createCloudletObj(final String cloudletID, final OPENiObject objectBody, final ICreateCloudletObjectResult IcreateCloudletObject) {
        openiConnect(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                new AsyncCreateCloudletObjectOperation(objectsApi, cloudletID, objectBody, authToken, IcreateCloudletObject);
            }

            @Override
            public void onAppPermsCancelled(String perms) {
              Toast.makeText(context, "Unable to Continue without permissions", Toast.LENGTH_LONG);
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
