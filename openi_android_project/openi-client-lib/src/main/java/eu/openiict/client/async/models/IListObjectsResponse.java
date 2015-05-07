package eu.openiict.client.async.models;

import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObject;
import eu.openiict.client.model.OPENiObjectList;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface IListObjectsResponse<ProcessObject> {

    public void onSuccess(OPENiObjectList openiObjectList);

    public void onPermissionDenied();

    public void onFailure(String message);

}
