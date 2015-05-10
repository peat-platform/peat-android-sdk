package org.peatplatform.client.async.models;

import org.peatplatform.client.common.ApiException;
import org.peatplatform.client.model.PermissionsResponse;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface IPostPermissionsResponse<ProcessObject> {

    public void onSuccess(PermissionsResponse premPostResp);

    public void onPermissionDenied();

    public void onFailure(String message);

}
