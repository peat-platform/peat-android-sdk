package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.api.AuthorizeApi;
import eu.openiict.client.api.SessionApi;
import eu.openiict.client.async.models.ILoginResponse;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.AuthorizationRequest;
import eu.openiict.client.model.Credentials;
import eu.openiict.client.model.Session;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncLoginOperation extends AsyncTask<String, Void, String> {

   private SessionApi     sessionApi;
   private AuthorizeApi   authorizeApi;
   private String         clientId;
   private ILoginResponse iLoginResponse;


   public AsyncLoginOperation(SessionApi sessionApi, AuthorizeApi authorizeApi, String clientId, ILoginResponse iLoginResponse){
      this.sessionApi     = sessionApi;
      this.authorizeApi   = authorizeApi;
      this.clientId       = clientId;
      this.iLoginResponse = iLoginResponse;
   }


   @Override
   protected String doInBackground(String... params) {

      final String userName = params[0];
      final String password = params[1];

      Log.d("sessionToken", userName);

      final Credentials credentials = new Credentials();
      credentials.setName(userName);
      credentials.setPassword(password);

      try {
         final Session session = sessionApi.login(credentials);

         Log.d("sessionToken", session.toString());

         final AuthorizationRequest authReq = new AuthorizationRequest();

         authReq.setClientId(clientId);
         authReq.setSession(session.getSession());

         final String sessionToken = authorizeApi.authorizeClient(authReq).getToken();

         Log.d("sessionToken", sessionToken);

         return sessionToken;
      }
      catch (ApiException e){
         return "error";
      }

   }


   @Override
   protected void onPostExecute(String sessionToken) {
      super.onPostExecute(sessionToken);

      if ("error".equals(sessionToken)){
         iLoginResponse.onFailure();
      }
      else{
         iLoginResponse.onSuccess(sessionToken);
         Log.d("sessionToken", sessionToken);
      }
   }
}
