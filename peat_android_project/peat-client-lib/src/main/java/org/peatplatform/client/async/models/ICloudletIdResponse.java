package org.peatplatform.client.async.models;

/**
 * Created by dmccarthy on 14/11/14.
 */
public interface ICloudletIdResponse {

    public void onSuccess(String cloudletID);

    public void onPermissionDenied();

    public void onFailure(String error);

}
