package eu.openiict.client.async.models;

import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObject;
import eu.openiict.client.model.OPENiObjectList;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface ICloudletObjectsCall<ProcessObject> {

    public ProcessObject doProcess(String authToken) throws ApiException;

    public void onSuccess(OPENiObjectList openiObjectList);

    public void onFailure();

}
