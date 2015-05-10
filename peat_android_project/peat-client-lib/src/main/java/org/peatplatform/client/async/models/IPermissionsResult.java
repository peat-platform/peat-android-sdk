package org.peatplatform.client.async.models;

import java.util.List;

import org.peatplatform.client.model.Permissions;

/**
 * Created by dmccarthy on 04/12/14.
 */
public interface IPermissionsResult {
   public void onSuccess(List<Permissions> permissions);

   public void onFailure(String message);

   public void onPermissionDenied();
}