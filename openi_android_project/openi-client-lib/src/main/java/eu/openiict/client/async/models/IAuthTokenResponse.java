package eu.openiict.client.async.models;

/**
 * Created by dmccarthy on 14/11/14.
 */
public interface IAuthTokenResponse {

    public void onSuccess(String authToken);

    public void onAppPermsDenied(String perms);

    public void onFailure(String error);

}
