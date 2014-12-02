package eu.openiict.client.async;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import eu.openiict.client.R;
import eu.openiict.client.api.AuthorizeApi;
import eu.openiict.client.api.CloudletsApi;
import eu.openiict.client.api.ObjectsApi;
import eu.openiict.client.api.PermissionsApi;
import eu.openiict.client.api.SearchApi;
import eu.openiict.client.api.SessionApi;
import eu.openiict.client.async.models.IAuthTokenResponse;
import eu.openiict.client.async.models.ICloudletIdResponse;
import eu.openiict.client.async.models.ICloudletObjectCall;
import eu.openiict.client.async.models.ICreateCloudletObjectResult;
import eu.openiict.client.async.models.ILoginResponse;
import eu.openiict.client.async.models.IPostPermissionsResponse;
import eu.openiict.client.async.models.ISearchCloudletsResults;
import eu.openiict.client.async.models.ISearchOneCloudletResults;
import eu.openiict.client.model.OPENiObject;
import eu.openiict.client.model.Permissions;

/**
 * Created by dmccarthy on 14/11/14.
 */
public class OPENiAsync {

    public static final String OPENi_PUBLIC_PREFERENCES = "OPENi_prefs";
    private static final String OPENi_PREFERENCE_MISSING = "UNKNOWN";
    private static final String OPENi_PREFERENCE_CLOUDLET = "cloudlet_id";
    private static final String OPENi_PREFERENCE_TOKEN = "security_token";
    private static final String openi_security_token = "";
    private static OPENiAsync openiAsync;
    private static Context context;
    private SharedPreferences prefs;
    //private Boolean validToken;
    private String clientId;
    private SessionApi sessionApi;
    private AuthorizeApi authorizeApi;
    private CloudletsApi cloudletsApi;
    private ObjectsApi objectsApi;
    private SearchApi searchApi;
    private PermissionsApi permissionsApi;


    private OPENiAsync(String clientId, Context context) {
        this.clientId = clientId;
        this.context = context;
        this.sessionApi = new SessionApi();
        this.authorizeApi = new AuthorizeApi();
        this.cloudletsApi = new CloudletsApi();
        this.searchApi = new SearchApi();
        this.objectsApi = new ObjectsApi();
        this.permissionsApi = new PermissionsApi();
        this.prefs = context.getSharedPreferences(OPENi_PUBLIC_PREFERENCES, Context.MODE_WORLD_READABLE);

//      final SharedPreferences.Editor e = prefs.edit();
//      e.clear();
//      e.commit();

        this.sessionApi.getInvoker().ignoreSSLCertificates(true);
        this.authorizeApi.getInvoker().ignoreSSLCertificates(true);
        this.cloudletsApi.getInvoker().ignoreSSLCertificates(true);
        this.searchApi.getInvoker().ignoreSSLCertificates(true);
        this.objectsApi.getInvoker().ignoreSSLCertificates(true);
        this.permissionsApi.getInvoker().ignoreSSLCertificates(true);
    }

    public static void initOPENiAsync(String clientId, Context context) {

        if (context != OPENiAsync.context) {
            openiAsync = new OPENiAsync(clientId, context);
        }

    }

    public static OPENiAsync getOPENiAsync() {

        if (null == openiAsync) {
            throw new RuntimeException("OPENiAsync object hasn't been created, call initOPENiAsync(String clientId, Context context) first.");
        }

        return openiAsync;
    }

    private void openLoginDialog(final IAuthTokenResponse authTokenResponse) {
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
                        setPref(OPENi_PREFERENCE_TOKEN, authToken);
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
    }

    private void openSignUpDialog(final IAuthTokenResponse authTokenResponse) {
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
                        setPref(OPENi_PREFERENCE_TOKEN, authToken);
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
    }

    public void setPref(String key, String value) {
        //if (!OPENi_PREFERENCE_MISSING.equals(getPref(key))) {
            final SharedPreferences.Editor edit = this.prefs.edit();
            edit.putString(key, value);
            edit.commit();
        //}
    }

    public String getPref(String key) {

        Log.d("prefs", this.prefs.getAll().toString());
        return this.prefs.getString(key, OPENi_PREFERENCE_MISSING);
    }

    public void logout() {
        final SharedPreferences.Editor edit = this.prefs.edit();
        edit.clear();
        edit.commit();
    }

    public void getCloudletObject(final String cloudletId, final String objectid, final ICloudletObjectCall iCloudletObjectCall) {

        getAuthToken(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                new AsyncGetCloudletObjectOperation(objectsApi, cloudletId, objectid, true, authToken, iCloudletObjectCall).execute();
            }

            @Override
            public void onFailure() {
                iCloudletObjectCall.onFailure();
            }
        });

    }

    public void getCloudletID(final ICloudletIdResponse cloudletIdResponse) {

        getAuthToken(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                new AsyncGetCloudletOperation(cloudletsApi, authToken, cloudletIdResponse).execute();
            }

            @Override
            public void onFailure() {
                cloudletIdResponse.onFailure();
            }
        });

    }

    public void checkTokenValidity(final ICloudletIdResponse iTokenValidity) {

        final String prefTokenValue = getPref(OPENi_PREFERENCE_TOKEN);
        new AsyncGetCloudletOperation(cloudletsApi, prefTokenValue, iTokenValidity).execute();

    }

    public void getAuthToken(final IAuthTokenResponse authTokenResponse) {

        final String prefTokenValue = getPref(OPENi_PREFERENCE_TOKEN);
        Log.d("sessionToken", prefTokenValue);

        if (OPENi_PREFERENCE_MISSING.equals(prefTokenValue)) {
            Log.d("sessionToken", "token not in prefs");
            //kick off login process
            openLoginDialog(authTokenResponse); // TODO : open AuthMainActitity
        } else {
            Log.d("sessionToken", "token in prefs");
            // check token validity TODO use timestamps too
            checkTokenValidity( new ICloudletIdResponse() {
                @Override
                public void onSuccess(String cloudletid) {
                    Log.d("OPENi", "Got cloudletid: "+cloudletid);
                    authTokenResponse.onSuccess(prefTokenValue);
                    //Toast toast = Toast.makeText(context, "Auth Token in prefs", Toast.LENGTH_SHORT);
                    //toast.show();
                }

                @Override
                public void onFailure() {
                    Log.d("OPENi", "Not valid token in prefs");
                    openLoginDialog(authTokenResponse); // TODO : open AuthMainActitity
                }
            });
            /*if(isAuthTokenValid(prefTokenValue)){
                authTokenResponse.onSuccess(prefTokenValue);
                Toast toast = Toast.makeText(context, "Auth Token in prefs", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                openLoginDialog(authTokenResponse); // TODO : open AuthMainActitity
            }*/

        }
    }

    public void getAuthTokenRegister(final IAuthTokenResponse authTokenResponse) {

        final String prefTokenValue = getPref(OPENi_PREFERENCE_TOKEN);
        Log.d("sessionToken", prefTokenValue);

        if (OPENi_PREFERENCE_MISSING.equals(prefTokenValue)) {
            Log.d("sessionToken", "token not in prefs");
            //kick off login process
            openSignUpDialog(authTokenResponse);
        } else {
            Log.d("sessionToken", "token in prefs");
            authTokenResponse.onSuccess(prefTokenValue);
            //Toast toast = Toast.makeText(context, "Auth Token in prefs", Toast.LENGTH_SHORT);
            //toast.show();
        }
    }

    public void searchInAllCloudlets(final String with_property, final String property_filter, final String type, final Boolean id_only, final String offset, final String limit, final ISearchCloudletsResults searchCloudletsResults) {

        getAuthToken(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                new AsyncSearchCloudletsOperation(searchApi, with_property, property_filter, type, id_only, offset, limit, authToken, searchCloudletsResults).execute();
            }

            @Override
            public void onFailure() {
                searchCloudletsResults.onFailure();
            }
        });
    }

    public void searchInOneCloudlet(final String cloudletId, final String with_property, final String property_filter, final String type,  final Boolean id_only, final String only_show_properties, final Integer offset, final Integer limit, final ISearchOneCloudletResults searchCloudletResults) {

        getAuthToken(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                new AsyncSearchOneCloudletOperation(objectsApi, cloudletId, with_property, property_filter, type, id_only, only_show_properties, offset, limit, authToken, searchCloudletResults).execute();
            }

            @Override
            public void onFailure() {
                searchCloudletResults.onFailure();
            }
        });
    }

    public void createCloudletObj(final String cloudletID, final OPENiObject objectBody, final ICreateCloudletObjectResult IcreateCloudletObject) {
        getAuthToken(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                new AsyncCreateCloudletObjectOperation(objectsApi, cloudletID, objectBody, authToken, IcreateCloudletObject);
            }

            @Override
            public void onFailure() {
                IcreateCloudletObject.onFailure();
            }
        });
    }

    public void postPermissions(final List<Permissions> permissionsList, final IPostPermissionsResponse IpostPermissionsResponse){
        getAuthToken(new IAuthTokenResponse() {
            @Override
            public void onSuccess(String authToken) {
                new AsyncPostPermissionsOperation(permissionsApi, permissionsList, authToken, IpostPermissionsResponse).execute();
            }

            @Override
            public void onFailure() {
                IpostPermissionsResponse.onFailure();
            }
        });
    }

}
