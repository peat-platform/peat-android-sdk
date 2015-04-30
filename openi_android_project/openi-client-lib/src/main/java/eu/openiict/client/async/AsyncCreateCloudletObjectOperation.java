package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.api.ObjectsApi;
import eu.openiict.client.async.models.ICreateCloudletObjectResult;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObject;
import eu.openiict.client.model.ObjectResponse;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncCreateCloudletObjectOperation extends AsyncTask<String, Void, ObjectResponse> {

    private static final String TAG = "AsyncCreateCloudlet";

    private OPENiObject objectBody;
    private ObjectsApi  objectsApi;
    private String      token;
    private ICreateCloudletObjectResult IcreateObjectResult;

    public AsyncCreateCloudletObjectOperation(ObjectsApi objectsApi, OPENiObject objectBody,  String token, ICreateCloudletObjectResult IcreateObjectResult) {
        this.objectsApi = objectsApi;
        this.objectBody = objectBody;
        this.token      = token;

        this.IcreateObjectResult = IcreateObjectResult;
    }


    @Override
    protected ObjectResponse doInBackground(String... params) {

        Log.d(TAG, token);

        try {
            return objectsApi.createObjectWithAuth(objectBody, token);
        } catch (ApiException e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }


    @Override
    protected void onPostExecute(ObjectResponse objectResponse) {
        super.onPostExecute(objectResponse);

        if (null == objectResponse) {
            IcreateObjectResult.onFailure();
        } else {
            try {
                Log.d(TAG, "create object results: " + objectResponse.toString());
                IcreateObjectResult.onSuccess(objectResponse);
            } catch (Exception e) {
                IcreateObjectResult.onFailure();
            }
            //Log.d("sessionToken", objectResponse.toString());
        }
    }
}
