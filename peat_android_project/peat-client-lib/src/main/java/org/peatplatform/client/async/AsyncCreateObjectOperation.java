package org.peatplatform.client.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import org.peatplatform.client.api.ObjectsApi;
import org.peatplatform.client.async.models.ICreateObjectResult;
import org.peatplatform.client.common.ApiException;
import org.peatplatform.client.model.ObjectResponse;
import org.peatplatform.client.model.PeatObject;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncCreateObjectOperation extends AsyncTask<String, Void, Object> {

    private static final String TAG = "AsyncCreateCloudlet";

    private PeatObject  objectBody;
    private ObjectsApi objectsApi;
    private String      token;
    private ICreateObjectResult IcreateObjectResult;

    public AsyncCreateObjectOperation(ObjectsApi objectsApi, PeatObject objectBody, String token, ICreateObjectResult IcreateObjectResult) {
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
