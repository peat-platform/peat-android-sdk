package eu.openiict.client.async;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
import eu.openiict.client.async.models.IOPENiAPiCall;
import eu.openiict.client.async.models.IPermissionsResult;
import eu.openiict.client.async.models.IPostPermissionsResponse;
import eu.openiict.client.async.models.ISearchCloudletsResults;
import eu.openiict.client.async.models.ISearchOneCloudletResults;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObject;
import eu.openiict.client.model.Permissions;
import eu.openiict.client.model.PermissionsResponse;
import eu.openiict.client.permissions.ProcessAppPermissions;

/**
 * Created by dmccarthy on 14/11/14.
 */
public class OPENiAsync {

   private              String            authTokenValue;
   private static       OPENiAsync        openiAsync;
   private              Context           context;
   private              String            clientId;
   private              SessionApi        sessionApi;
   private              AuthorizeApi      authorizeApi;
   private              CloudletsApi      cloudletsApi;
   private              ObjectsApi        objectsApi;
   private              SearchApi         searchApi;
   private              PermissionsApi    permissionsApi;


   private OPENiAsync(String clientId, Context context) {
      this.clientId       = clientId;
      this.context        = context;
      this.sessionApi     = new SessionApi();
      this.authorizeApi   = new AuthorizeApi();
      this.cloudletsApi   = new CloudletsApi();
      this.searchApi      = new SearchApi();
      this.objectsApi     = new ObjectsApi();
      this.permissionsApi = new PermissionsApi();

      this.sessionApi.getInvoker().ignoreSSLCertificates(true);
      this.authorizeApi.getInvoker().ignoreSSLCertificates(true);
      this.cloudletsApi.getInvoker().ignoreSSLCertificates(true);
      this.searchApi.getInvoker().ignoreSSLCertificates(true);
      this.objectsApi.getInvoker().ignoreSSLCertificates(true);
      this.permissionsApi.getInvoker().ignoreSSLCertificates(true);
   }

   public static void init(String clientId, Context context){

      if (null == openiAsync){
         openiAsync = new OPENiAsync(clientId, context);
      }

   }

   public static OPENiAsync instance() {

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
   }

   public void appPermissions(){
      getAuthToken(new IAuthTokenResponse() {
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
         public void onFailure() {
            Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT);
         }
      });

   }


   public void logout() {
      Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show();
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


   public void checkTokenValidity(final ICloudletIdResponse iTokenValidity) {

      new AsyncGetCloudletOperation(cloudletsApi, authTokenValue, iTokenValidity).execute();

   }

   public void getAuthToken(final IAuthTokenResponse authTokenResponse) {

      Log.d("sessionToken", ""+authTokenValue);

      if (null == authTokenValue) {
         Log.d("sessionToken", "token not in prefs");
         //kick off login process
         openLoginDialog(authTokenResponse); // TODO : open AuthMainActitity
      } else {
         Log.d("sessionToken", "token in prefs");
         // check token validity TODO use timestamps too
         checkTokenValidity( new ICloudletIdResponse() {
            @Override
            public void onSuccess(String cloudletid) {
               Log.d("OPENi", "Got cloudletid: " + cloudletid);
               authTokenResponse.onSuccess(authTokenValue);
            }

            @Override
            public void onFailure() {
               Log.d("OPENi", "Not valid token in prefs");
               openLoginDialog(authTokenResponse); // TODO : open AuthMainActitity
            }
         });
      }
   }


   public void getAuthTokenRegister(final IAuthTokenResponse authTokenResponse) {

      Log.d("sessionToken", authTokenValue);

      if (null == authTokenValue) {
         Log.d("sessionToken", "token not in prefs");
         //kick off login process
         openSignUpDialog(authTokenResponse);
      } else {
         Log.d("sessionToken", "token in prefs");
         authTokenResponse.onSuccess(authTokenValue);
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


   public void execOpeniApiCall(final IOPENiAPiCall iOPENiApiCall) {

      getAuthToken(new IAuthTokenResponse() {
         @Override
         public void onSuccess(String authToken) {
            Log.d("execOpeniApiCall", authToken);
            new AsyncOpeniNetworkOperation(iOPENiApiCall, authToken).execute();
         }

         @Override
         public void onFailure() {
            iOPENiApiCall.onFailure();
         }
      });

   }


   public void processPermissions(Context context, String permsFileName, IPermissionsResult permissionsResult){
      new ProcessAppPermissions(context, permissionsResult, permsFileName ).execute();
   }
}
