package org.peatplatform.client.async.models;


/**
 * Created by dmccarthy on 16/11/14.
 */
public interface IPeatResponse<ProcessObject> {

    public void onSuccess(ProcessObject o);

    public void onPermissionDenied();

    public void onFailure(String message);

}
