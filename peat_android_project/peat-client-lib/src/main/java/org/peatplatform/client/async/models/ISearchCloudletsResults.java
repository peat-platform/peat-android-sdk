package org.peatplatform.client.async.models;


import org.peatplatform.client.model.PeatObjectList;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface ISearchCloudletsResults<ProcessObject> {

    public void onSuccess(PeatObjectList objList);

    public void onPermissionDenied();

    public void onFailure(String message);

}
