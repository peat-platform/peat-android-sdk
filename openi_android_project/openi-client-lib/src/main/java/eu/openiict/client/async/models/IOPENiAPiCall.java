package eu.openiict.client.async.models;

import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObject;

/**
 * Created by dmccarthy on 16/11/14.
 */
public interface IOPENiAPiCall<ProcessObject> {

    public ProcessObject doProcess(String authToken);

    public void onSuccess(Object object);

    public void onFailure();

}
