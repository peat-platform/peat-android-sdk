package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.api.AuthorizeApi;
import eu.openiict.client.api.SessionApi;
import eu.openiict.client.api.UsersApi;
import eu.openiict.client.async.models.ILoginResponse;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.AuthorizationRequest;
import eu.openiict.client.model.Credentials;
import eu.openiict.client.model.Session;
import eu.openiict.client.model.UserRegisterRequest;


public class AsyncSignupOperation extends AsyncTask<String, Void, String> {

    private SessionApi sessionApi;
    private AuthorizeApi authorizeApi;
    private UsersApi usersApi;
    private String clientId;
    private ILoginResponse iLoginResponse;


    public AsyncSignupOperation(SessionApi sessionApi, AuthorizeApi authorizeApi, String clientId, ILoginResponse iLoginResponse) {
        this.sessionApi = sessionApi;
        this.authorizeApi = authorizeApi;
        this.clientId = clientId;
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
        UserRegisterRequest user = new UserRegisterRequest();
        user.setName(userName);
        user.setPassword(password);

        try {
            usersApi = new UsersApi();
            usersApi.createUser(user);

            final Session session = sessionApi.login(credentials);

            Log.d("sessionToken", session.toString());

            final AuthorizationRequest authReq = new AuthorizationRequest();

            authReq.setClientId(clientId);
            authReq.setSession(session.getSession());

            final String sessionToken = authorizeApi.authorizeClient(authReq).getToken();

            Log.d("sessionToken", sessionToken);

            return sessionToken;
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
