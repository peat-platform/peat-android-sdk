package eu.openiict.client.async.models;

import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.PermissionsResponse;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface IPostPermissionsResponse<ProcessObject> {

    public void onSuccess(PermissionsResponse premPostResp);

    public void onPermissionDenied();

    public void onFailure(String message);

}
