package eu.openiict.client.async;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

import eu.openiict.client.R;
import eu.openiict.client.api.AuthorizationsApi;
import eu.openiict.client.api.CloudletsApi;
import eu.openiict.client.api.ObjectsApi;
import eu.openiict.client.api.PermissionsApi;
import eu.openiict.client.api.SearchApi;
import eu.openiict.client.async.models.IAuthTokenResponse;
import eu.openiict.client.async.models.ICloudletIdResponse;
import eu.openiict.client.async.models.ICloudletObjectCall;
import eu.openiict.client.async.models.ICreateCloudletObjectResult;
import eu.openiict.client.async.models.IOPENiAPiCall;
import eu.openiict.client.async.models.IPermissionsResult;
import eu.openiict.client.async.models.IPostPermissionsResponse;
import eu.openiict.client.async.models.ISearchCloudletsResults;
import eu.openiict.client.async.models.ISearchOneCloudletResults;
import eu.openiict.client.model.OPENiObject;
import eu.openiict.client.model.Permissions;
import eu.openiict.client.permissions.ProcessAppPermissions;

//import org.apache.commons.codec.binary.Base64;

/**
 * Created by dmccarthy on 14/11/14.
 */
public class OPENiAsync {

    private static OPENiAsync openiAsync;
    private String authTokenValue;
    private Context context;
    private String api_key;
    private String secret;
    private String appPermsFile;
    //   private              SessionApi        sessionApi;
    private AuthorizationsApi authorizeApi;
    private CloudletsApi cloudletsApi;
    private ObjectsApi objectsApi;
    private SearchApi searchApi;
    private PermissionsApi permissionsApi;

    public static final String OPENi_PUBLIC_PREFERENCES = "OPENi_prefs";
    private static final String OPENi_PREFERENCE_MISSING = "UNKNOWN";
    private static final String OPENi_PREFERENCE_CLOUDLET = "cloudlet_id";
    private static final String OPENi_PREFERENCE_TOKEN = "security_token";
    private static final String openi_security_token = "";

    private SharedPreferences prefs;

    private OPENiAsync(String api_key, String secret, Context context, String appPermsFile) {
        this.secret = secret;
        this.api_key = api_key;
        this.context = context;
        this.prefs = context.getSharedPreferences(OPENi_PUBLIC_PREFERENCES, Context.MODE_PRIVATE);
        this.appPermsFile = appPermsFile;


//      this.sessionApi     = new SessionApi();
        this.authorizeApi = new AuthorizationsApi();
        this.cloudletsApi = new CloudletsApi();
        this.searchApi = new SearchApi();
        this.objectsApi = new ObjectsApi();
        this.permissionsApi = new PermissionsApi();

//      this.sessionApi.getInvoker().ignoreSSLCertificates(true);
        this.authorizeApi.getInvoker().ignoreSSLCertificates(true);
        this.cloudletsApi.getInvoker().ignoreSSLCertificates(true);
        this.searchApi.getInvoker().ignoreSSLCertificates(true);
        this.objectsApi.getInvoker().ignoreSSLCertificates(true);
        this.permissionsApi.getInvoker().ignoreSSLCertificates(true);
    }

    public static void init(String api_key, String secret, Context context, String appPermsFile) {

        if (null == openiAsync) {
            openiAsync = new OPENiAsync(api_key, secret, context, appPermsFile);
        }

    }

    public static OPENiAsync instance(Context context) {

        if (null == openiAsync) {
            throw new RuntimeException("OPENiAsync object hasn't been created, call initOPENiAsync(String clientId, Context context) first.");
        }
        if (context != null)
            openiAsync.context = context;
        return openiAsync;
    }

    private String readPermissionsFromFile(){

        try {
            final InputStream is = context.getAssets().open(appPermsFile);

            final int size = is.available();

            final byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            final String json = new String(buffer, "UTF-8");

            //return new JSONArray( json );
            return json;
        }
        catch (Exception ex) {
            Log.d("A", ex.toString());
            ex.printStackTrace();
            //permissionsResult.onFailure("Error reading permissions file.");
            return null;
        }

    }

/*   private void openLoginDialog(final IAuthTokenResponse authTokenResponse) {
      final Dialog login = new Dialog(context);
      // Set GUI of login screen
      login.setContentView(R.layout.auth_activity_sign_in_screen);
      login.setTitle("Login to OPENi");

      final EditText username = (EditText) login.findViewById(R.id.etUserName);
      final EditText password = (EditText) login.findViewById(R.id.etPass);

      final Button btnLogin = (Button) login.findViewById(R.id.btnSingIn);
      final Button btnCancel = (Button) login.findViewById(R.id.btnSingInCancel);

      // Attached listener for login GUI button
      btnLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Log.d("Login dialog", "start login");
            btnLogin.setEnabled(false);
            final String name = username.getText().toString();
            final String pass = password.getText().toString();

            new AsyncLoginOperation(sessionApi, authorizeApi, clientId, new ILoginResponse() {
               @Override
               public void onSuccess(final String authToken) {

                  Log.d("Login dialog", "got token " + authToken);
                  authTokenValue = authToken;
                  Toast toast = Toast.makeText(context, "OPENi: Logged In!", Toast.LENGTH_SHORT);
                  toast.show();
                  authTokenResponse.onSuccess(authToken);
                  login.dismiss();
               }

               @Override
               public void onFailure() {
                  btnLogin.setEnabled(true);
                  authTokenResponse.onFailure();
               }
            }).execute(name, pass);
         }
      });

      btnCancel.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            btnLogin.setEnabled(true);
            login.dismiss();
         }
      });

      // Make dialog box visible.
      login.show();
   }*/

    private void openAuthDialog(final IAuthTokenResponse authTokenResponse) {

        final Dialog auth_dialog = new Dialog(context);
        auth_dialog.setContentView(R.layout.auth_dialog);
        WebView web = (WebView) auth_dialog.findViewById(R.id.webv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        web.getSettings().setJavaScriptEnabled(true);
        // TODO: get server ip dynamically
        String perms = readPermissionsFromFile();
        String bJson = Base64.encodeToString(perms .getBytes(), Base64.URL_SAFE);
        final String basePathURL = cloudletsApi.getBasePath().replace("/api/v1", "").replace("https://","http://");
        web.loadUrl(basePathURL+":3000" + "/auth/account?api_key=" + api_key + "&secret=" + secret + "&redirectURL=" + "http://localhost" + "&appPerms=" + bJson);
        web.setWebViewClient(new WebViewClient() {

            boolean authComplete = false;
            //Intent resultIntent = new Intent();
            String authCode;

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
                Log.e("ProcessPayment", "onReceivedError = " + errorCode);

                //404 : error code for Page Not found
                if (errorCode == -2)
                    authTokenResponse.onFailure("error");
                else if (failingUrl.contains("ERROR=error_permissions")) {
                    Log.i("", "ACCESS_DENIED_HERE");
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
                super.onPageFinished(view, url);
                //auth_dialog.show();
                if (url.contains(basePathURL)){
                    auth_dialog.show();
                }
                if (url.contains("?OUST=") && url.startsWith("http://localhost") && authComplete != true) {
                    Uri uri = Uri.parse(url);
                    authCode = uri.getQueryParameter("OUST");
                    setPref(OPENi_PREFERENCE_TOKEN, authCode);
                    Log.i("", "CODE : " + authCode);
                    authComplete = true;
                    /*resultIntent.putExtra("code", authCode);
                    context.setResult(Activity.RESULT_OK, resultIntent);
                    context.setResult(Activity.RESULT_CANCELED, resultIntent);*/

                    /*SharedPreferences.Editor edit = pref.edit();
                    edit.putString("Code", authCode);
                    edit.commit();*/
                    authTokenValue = authCode;
                    authTokenResponse.onSuccess(authCode);
                    auth_dialog.dismiss();
                    //new TokenGet().execute();
                } else{
                    //auth_dialog.dismiss();
                    //authTokenResponse.onFailure("error");
                }
            }
        });
        //auth_dialog.show();
        auth_dialog.setTitle("OPENi");
        //auth_dialog.setCancelable(false);

/*
      final Dialog login = new Dialog(context);
      // Set GUI of login screen
      login.setContentView(R.layout.auth_activity_sign_up_screen);
      login.setTitle("Sign Up to OPENi");

      final EditText username = (EditText) login.findViewById(R.id.etUserName);
      final EditText password = (EditText) login.findViewById(R.id.etPass);

      final Button btnLogin = (Button) login.findViewById(R.id.btnSingIn);
      final Button btnCancel = (Button) login.findViewById(R.id.btnSingInCancel);

      // Attached listener for login GUI button
      btnLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Log.d("Login dialog", "start login");
            btnLogin.setEnabled(false);
            final String name = username.getText().toString();
            final String pass = password.getText().toString();

            new AsyncSignupOperation(sessionApi, authorizeApi, clientId, new ILoginResponse() {
               @Override
               public void onSuccess(final String authToken) {

                  Log.d("Login dialog", "got token " + authToken);
                  authTokenValue = authToken;
                  Toast toast = Toast.makeText(context, "OPENi: Logged In!", Toast.LENGTH_SHORT);
                  toast.show();
                  //authTokenResponse.onSuccess(authToken);
                  login.dismiss();
                  authTokenResponse.onSuccess(authToken);
                  appPermissions();
               }

               @Override
               public void onFailure() {
                  btnLogin.setEnabled(true);
                  authTokenResponse.onFailure();
               }
            }).execute(name, pass);
         }
      });

      btnCancel.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            btnLogin.setEnabled(true);
            login.dismiss();
         }
      });

      // Make dialog box visible.
      login.show();
*/
    }

   /* public void appPermissions() {
        openiConnect(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                final Dialog appPerm = new Dialog(context);
                appPerm.setContentView(R.layout.app_perms_screen);
                appPerm.setTitle("OPENi App Permissions");

                Button btnAcceptPerms = (Button) appPerm.findViewById(R.id.btnAcceptAppPerms);
                TextView permsView = (TextView) appPerm.findViewById(R.id.permsView);

                CharSequence text = Html.fromHtml("App <b>OPENi</b> needs access to the following objects of yours:</br>" +
                        "<b> User , Photo , Video </b>");

                permsView.setText(text);

                btnAcceptPerms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Permissions permissions = new Permissions();
                        permissions.setAccessLevel("APP");
                        permissions.setAccessType("READ");
                        permissions.setRef("t_402d94dd3b59ecd8cce7e037f32932cb-1363");
                        permissions.setType("type");

                        List<Permissions> permissionsList = new ArrayList<Permissions>();

                        permissionsList.add(permissions);
                        permissions.setAccessType("CREATE");
                        permissionsList.add(permissions);
                        permissions.setAccessType("UPDATE");
                        permissionsList.add(permissions);
                        permissions.setAccessType("DELETE");
                        permissionsList.add(permissions);

                        postPermissions(permissionsList, new IPostPermissionsResponse() {
                            @Override
                            public Object doProcess(String authToken) throws ApiException {
                                return null;
                            }

                            @Override
                            public void onSuccess(PermissionsResponse obj) {
                                Toast.makeText(context, "App Permissions: Granted", Toast.LENGTH_SHORT).show();
                                //authTokenResponse.onSuccess(getPref(OPENi_PREFERENCE_TOKEN));
                                appPerm.dismiss();
                            }

                            @Override
                            public void onFailure() {
                                Toast.makeText(context, "App Permissions: Error", Toast.LENGTH_SHORT).show();
                                appPerm.dismiss();
                            }
                        });
                    }
                });

                appPerm.show();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT);
            }
        });

    }*/

    public void setPref(String key, String value) {
        //if (!OPENi_PREFERENCE_MISSING.equals(getPref(key))) {
        final SharedPreferences.Editor edit = this.prefs.edit();
        edit.putString(key, value);
        edit.commit();
        //}
    }

    public void setPrefBool(String key, Boolean value) {
        //if (!OPENi_PREFERENCE_MISSING.equals(getPref(key))) {
        final SharedPreferences.Editor edit = this.prefs.edit();
        edit.putBoolean(key, value);
        edit.commit();
        //}
    }

    public String getPref(String key) {

        Log.d("prefs", this.prefs.getAll().toString());
        return this.prefs.getString(key, OPENi_PREFERENCE_MISSING);
    }

    public Boolean getPrefBool(String key) {

        Log.d("prefs", this.prefs.getAll().toString());
        return this.prefs.getBoolean(key, true);
    }

    public void logout() {
        //final Dialog auth_dialog = new Dialog(context);
        //auth_dialog.setContentView(R.layout.auth_dialog);
        final SharedPreferences.Editor edit = this.prefs.edit();
        WebView web = new WebView(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        web.getSettings().setJavaScriptEnabled(true);
        // TODO: get server ip dynamically
        //String perms = readPermissionsFromFile();
        //String bJson = Base64.encodeToString(perms .getBytes(), Base64.URL_SAFE);
        String basePathURL = cloudletsApi.getBasePath().replace("/api/v1", "").replace("https://","http://");
        web.loadUrl(basePathURL+":3000" + "/auth/logout");
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
                Log.e("WebViewAuth", "onReceivedError = " + errorCode);
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
        //auth_dialog.show();
        //auth_dialog.setTitle("OPENi");

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
            public void onFailure(String error) {
                iCloudletObjectCall.onFailure();
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
        Log.d("sessionToken", "" + prefTokenValue);

        if (OPENi_PREFERENCE_MISSING.equals(prefTokenValue)) {
            Log.d("sessionToken", "token not in prefs");
            //kick off login process
            openAuthDialog(authTokenResponse); // TODO : open AuthMainActitity
        } else {
            Log.d("sessionToken", "token in prefs");
            // check token validity TODO use timestamps too
            checkTokenValidity(new ICloudletIdResponse() {
                @Override
                public void onSuccess(String cloudletid) {
                    Log.d("OPENi", "Got cloudletid: " + cloudletid);
                    authTokenResponse.onSuccess(prefTokenValue);
                }

                @Override
                public void onFailure() {
                    Log.d("OPENi", "Not valid token in prefs");
                    openAuthDialog(authTokenResponse); // TODO : open AuthMainActitity
                }
            });
        }
    }

    /*public void getAuthTokenRegister(final IAuthTokenResponse authTokenResponse) {

        final String prefTokenValue = getPref(OPENi_PREFERENCE_TOKEN);
        Log.d("sessionToken", authTokenValue);

        if (OPENi_PREFERENCE_MISSING.equals(prefTokenValue)) {
            Log.d("sessionToken", "token not in prefs");
            //kick off login process
            openAuthDialog(authTokenResponse);
        } else {
            Log.d("sessionToken", "token in prefs");
            authTokenResponse.onSuccess(authTokenValue);
        }
    }*/

    public void searchInAllCloudlets(final String with_property, final String property_filter, final String type, final Boolean id_only, final String offset, final String limit, final ISearchCloudletsResults searchCloudletsResults) {

        openiConnect(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                new AsyncSearchCloudletsOperation(searchApi, with_property, property_filter, type, id_only, offset, limit, authToken, searchCloudletsResults).execute();
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
            public void onAppPermsDenied(String error) {
                IcreateCloudletObject.onFailure();
            }

            @Override
            public void onFailure(String error) {
                IcreateCloudletObject.onFailure();
            }
        });
    }

    public void postPermissions(final List<Permissions> permissionsList, final IPostPermissionsResponse IpostPermissionsResponse) {
        openiConnect(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                new AsyncPostPermissionsOperation(permissionsApi, permissionsList, authToken, IpostPermissionsResponse).execute();
            }

            @Override
            public void onAppPermsDenied(String error) {
                IpostPermissionsResponse.onFailure();
            }

            @Override
            public void onFailure(String error) {
                IpostPermissionsResponse.onFailure();
            }
        });
    }

    public void execOpeniApiCall(final IOPENiAPiCall iOPENiApiCall) {

        openiConnect(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                Log.d("execOpeniApiCall", authToken);
                new AsyncOpeniNetworkOperation(iOPENiApiCall, authToken).execute();
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

    public void processPermissions(Context context, String permsFileName, IPermissionsResult permissionsResult) {
        new ProcessAppPermissions(context, permissionsResult, permsFileName).execute();
    }
}
