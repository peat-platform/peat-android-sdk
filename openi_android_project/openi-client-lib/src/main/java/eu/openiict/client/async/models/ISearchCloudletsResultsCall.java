package eu.openiict.client.async.models;

import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObjectList;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface ISearchCloudletsResultsCall<ProcessObject> {

    public void onSuccess(OPENiObjectList objectList);

    public void onPermissionDenied();

    public void onFailure(String message);

}
