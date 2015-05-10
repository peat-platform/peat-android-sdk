package org.peatplatform.client.async.models;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface IPeatAPiCall<ProcessObject> {

    public ProcessObject doProcess(String authToken);

    public void onSuccess(Object object);

    public void onPermissionDenied();

    public void onFailure(String message);

}
