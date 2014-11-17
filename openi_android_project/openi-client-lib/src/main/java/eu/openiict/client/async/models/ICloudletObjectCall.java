package eu.openiict.client.async.models;

import eu.openiict.client.common.ApiException;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface ICloudletObjectCall<ProcessObject> {

   public ProcessObject doProcess(String authToken) throws ApiException;
   public void          onSuccess(ProcessObject obj);
   public void          onFailure();

}
