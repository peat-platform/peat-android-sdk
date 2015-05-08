package eu.openiict.client.async.models;

import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.ObjectResponse;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface ICreateObjectResult {

    public void onSuccess(ObjectResponse createObjResp);

    public void onPermissionDenied();

    public void onFailure(String message);

}
