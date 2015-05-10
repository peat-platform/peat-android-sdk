package org.peatplatform.client.async.models;

import org.peatplatform.client.common.ApiException;
import org.peatplatform.client.model.ObjectResponse;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface ICreateObjectResult {

    public void onSuccess(ObjectResponse createObjResp);

    public void onPermissionDenied();

    public void onFailure(String message);

}
