package org.peatplatform.client.async.models;

import org.peatplatform.client.common.ApiException;
import org.peatplatform.client.model.PeatObjectList;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface ISearchCloudletsResultsCall<ProcessObject> {

    public void onSuccess(PeatObjectList objectList);

    public void onPermissionDenied();

    public void onFailure(String message);

}
