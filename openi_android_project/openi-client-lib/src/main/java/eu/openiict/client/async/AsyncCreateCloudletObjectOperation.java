package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import eu.openiict.client.api.ObjectsApi;
import eu.openiict.client.async.models.ICreateCloudletObjectResult;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObject;
import eu.openiict.client.model.ObjectResponse;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncCreateCloudletObjectOperation extends AsyncTask<String, Void, Object> {

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
    protected Object doInBackground(String... params) {

        Log.d(TAG, token);

        try {
            return objectsApi.createObjectWithAuth(objectBody, token);
        } catch (ApiException e) {
            Log.d(TAG, e.toString());
            return e;
        }

    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (null == o) {
            IcreateObjectResult.onFailure("null response");
        }
        else if( o instanceof ApiException){

            try {
                final JSONObject jo = new JSONObject(((ApiException) o).getMessage());
                if (null != jo.get("error") && jo.get("error").equals("permission denied")){
                    IcreateObjectResult.onPermissionDenied();
                }
                else {
                    IcreateObjectResult.onFailure(((ApiException) o).getMessage());
                }
            }
            catch (JSONException e){
                IcreateObjectResult.onFailure(((ApiException) o).getMessage());
            }
        }
        else{
            IcreateObjectResult.onSuccess((ObjectResponse) o);
        }
    }
}
