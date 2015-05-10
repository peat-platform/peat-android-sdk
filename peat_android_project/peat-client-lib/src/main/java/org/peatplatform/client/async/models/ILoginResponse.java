package org.peatplatform.client.async.models;

/**
 * Created by dmccarthy on 15/11/14.
 */
public interface ILoginResponse {

    public void onSuccess(String authToken);

    public void onPermissionDenied();

    public void onFailure(String message);

}
