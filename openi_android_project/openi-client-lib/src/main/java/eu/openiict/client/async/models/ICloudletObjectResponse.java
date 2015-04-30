package eu.openiict.client.async.models;

import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObject;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface ICloudletObjectResponse<ProcessObject> {

    public void onSuccess(OPENiObject opeNiObjectobj);

    public void onFailure();

}
