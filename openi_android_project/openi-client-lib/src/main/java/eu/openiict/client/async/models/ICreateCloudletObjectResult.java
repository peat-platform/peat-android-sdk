package eu.openiict.client.async.models;

import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.ObjectResponse;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface ICreateCloudletObjectResult<ProcessObject> {

    public ProcessObject doProcess(String authToken) throws ApiException;

    public void onSuccess(ObjectResponse createObjResp);

    public void onFailure();

}