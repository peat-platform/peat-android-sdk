package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.api.AuthorizationsApi;
import eu.openiict.client.api.CloudletsApi;
import eu.openiict.client.async.models.ILoginResponse;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.Auth_Credentials;
import eu.openiict.client.model.Session;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncLoginOperation extends AsyncTask<String, Void, String> {

    private AuthorizationsApi authorizeApi;
    private CloudletsApi cloudletsApi;
    private String api_key;
    private String secret;
    private ILoginResponse iLoginResponse;


    public AsyncLoginOperation( AuthorizationsApi authorizeApi, String api_key, String secret, ILoginResponse iLoginResponse) {

        this.authorizeApi = authorizeApi;
        this.api_key = api_key;
        this.secret = secret;
        this.iLoginResponse = iLoginResponse;
    }


    @Override
    protected String doInBackground(String... params) {

        final String userName = params[0];
        final String password = params[1];

        Log.d("sessionToken", userName);

        final Auth_Credentials credentials = new Auth_Credentials();
        credentials.setUsername(userName);
        credentials.setPassword(password);
        credentials.setApiKey(api_key);
        credentials.setSecret(secret);

        try {
            final Session session = authorizeApi.getAuthToken(credentials);

            Log.d("sessionToken", session.toString());

            /*final AuthorizationRequest authReq = new AuthorizationRequest();

            authReq.setClientId(clientId);
            authReq.setSession(session.getSession());

            final String sessionToken = authorizeApi.authorizeClient(authReq).getToken();

            Log.d("sessionToken", sessionToken);*/

            return session.getSession();

        } catch (ApiException e) {

            Log.d("sessionToken", "got token " + e.getMessage());
            return "error";
        }

    }


    @Override
    protected void onPostExecute(String sessionToken) {
        super.onPostExecute(sessionToken);

        if ("error".equals(sessionToken)) {
            iLoginResponse.onFailure();
        } else {
            iLoginResponse.onSuccess(sessionToken);
            Log.d("sessionToken", sessionToken);
        }
    }
}
