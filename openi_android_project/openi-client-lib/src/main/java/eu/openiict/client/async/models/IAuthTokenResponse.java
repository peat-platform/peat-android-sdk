package eu.openiict.client.async.models;

import org.json.JSONException;

import eu.openiict.client.common.ApiException;

/**
 * Created by dmccarthy on 14/11/14.
 */
public interface IAuthTokenResponse {

   public void onSuccess(String authToken);
   public void onFailure();

}
