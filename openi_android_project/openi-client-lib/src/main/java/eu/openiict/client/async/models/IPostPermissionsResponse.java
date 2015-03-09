package eu.openiict.client.async.models;

import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.PermissionsResponse;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface IPostPermissionsResponse<ProcessObject> {

    //public ProcessObject doProcess(String authToken) throws ApiException;

    public void onSuccess(PermissionsResponse premPostResp);

    public void onFailure();

}
