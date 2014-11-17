package eu.openiict.client.async;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import eu.openiict.client.R;

import eu.openiict.client.api.AuthorizeApi;
import eu.openiict.client.api.CloudletsApi;
import eu.openiict.client.api.SessionApi;
import eu.openiict.client.async.models.IAuthTokenResponse;
import eu.openiict.client.async.models.ICloudletObjectCall;
import eu.openiict.client.async.models.ILoginResponse;

/**
 * Created by dmccarthy on 14/11/14.
 */
public class OPENiAsync {

   public  static final String OPENi_PUBLIC_PREFERENCES  = "OPENi_prefs";
   private static final String OPENi_PREFERENCE_MISSING  = "UNKNOWN";
   private static final String OPENi_PREFERENCE_CLOUDLET = "cloudlet_id";
   private static final String OPENi_PREFERENCE_TOKEN    = "security_token";


   private        Context           context;
   private        SharedPreferences prefs;
   private static OPENiAsync        openiAsync;
   private        String            clientId;
   private        SessionApi        sessionApi;
   private        AuthorizeApi      authorizeApi;
   private        CloudletsApi      cloudletsApi;


   private OPENiAsync(String clientId, Context context){
      this.clientId     = clientId;
      this.context      = context;
      this.sessionApi   = new SessionApi();
      this.authorizeApi = new AuthorizeApi();
      this.cloudletsApi = new CloudletsApi();
      this.prefs        = context.getSharedPreferences(OPENi_PUBLIC_PREFERENCES, Context.MODE_WORLD_READABLE);

//      final SharedPreferences.Editor e = prefs.edit();
//      e.clear();
//      e.commit();

      this.sessionApi.getInvoker().ignoreSSLCertificates(true);
      this.authorizeApi.getInvoker().ignoreSSLCertificates(true);
      this.cloudletsApi.getInvoker().ignoreSSLCertificates(true);
   }


   private void openLoginDialog(final IAuthTokenResponse authTokenResponse){
      final Dialog login = new Dialog(context);
      // Set GUI of login screen
      login.setContentView(R.layout.login_dialog);
      login.setTitle("Login to OPENi");

      final EditText username = (EditText) login.findViewById(R.id.txtUsername);
      final EditText password = (EditText) login.findViewById(R.id.txtPassword);

      final Button btnLogin  = (Button) login.findViewById(R.id.btnLogin);
      final Button btnCancel = (Button) login.findViewById(R.id.btnCancel);

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


   private void setPref(String key, String value){
      if (OPENi_PREFERENCE_MISSING.equals(getPref(key))){
         final SharedPreferences.Editor edit = this.prefs.edit();
         edit.putString(key, value);
         edit.commit();
      }
   }


   private String getPref(String key){

      Log.d("prefs", this.prefs.getAll().toString());
      return this.prefs.getString(key, OPENi_PREFERENCE_MISSING);
   }


   public static void initOPENiAsync(String clientId, Context context){

      if (null == openiAsync){
         openiAsync = new OPENiAsync(clientId, context);
      }

   }


   public static OPENiAsync getOPENiAsync(){

      if (null == openiAsync){
         throw new RuntimeException("OPENiAsync object hasn't been creates, call initOPENiAsync(String clientId, Context context) first.");
      }

      return openiAsync;
   }


   public void logout(){
      final SharedPreferences.Editor edit = this.prefs.edit();
      edit.clear();
      edit.commit();
   }


   public void getCloudletObject(final ICloudletObjectCall iCloudletObjectCall){

      getAuthToken(new IAuthTokenResponse() {
         @Override
         public void onSuccess(String authToken) {
            new AsyncGetCloudletObjectOperation(authToken, iCloudletObjectCall).execute();
         }

         @Override
         public void onFailure() {
            iCloudletObjectCall.onFailure();
         }
      });

   }


   public void getAuthToken(final IAuthTokenResponse authTokenResponse){

      final String prefTokenValue = getPref(OPENi_PREFERENCE_TOKEN);
      Log.d("sessionToken", prefTokenValue);

      if ( OPENi_PREFERENCE_MISSING.equals(prefTokenValue) ){
         Log.d("sessionToken", "token not in prefs");
         //kick off login process
         openLoginDialog(authTokenResponse);
      }
      else{
         Log.d("sessionToken", "token in prefs");
         authTokenResponse.onSuccess(prefTokenValue);
      }
   }
}
