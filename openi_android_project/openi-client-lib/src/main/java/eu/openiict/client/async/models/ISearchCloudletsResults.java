package eu.openiict.client.async.models;

import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObjectList;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface ISearchCloudletsResults<ProcessObject> {

    public void onSuccess(OPENiObjectList objList);

    public void onPermissionDenied();

    public void onFailure(String message);

}
