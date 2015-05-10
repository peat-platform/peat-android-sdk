package org.peatplatform.client.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import org.peatplatform.client.api.ObjectsApi;
import org.peatplatform.client.async.models.IPeatResponse;
import org.peatplatform.client.common.ApiException;
import org.peatplatform.client.model.PeatObjectList;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncGetLatestObjectOperation extends AsyncTask<String, Void, Object> {

    private String type;
    private String auth;
    private IPeatResponse cb;

    private ObjectsApi objectsApi;


    public AsyncGetLatestObjectOperation(ObjectsApi objectsApi, String type, String auth, IPeatResponse cb) {
        this.auth                 = auth;
        this.type                 = type;
        this.objectsApi           = objectsApi;
        this.cb                   = cb;
    }


    @Override
    protected Object doInBackground(String... params) {

        try {
            return objectsApi.listObjectsWithAuthToken(0, 1, type, false, null, null, null, auth, "descending");
        } catch (ApiException e) {
            Log.d("AsyncGetCloudletOper", e.toString());
            return e;
        }

    }


    @Override
    protected void onPostExecute(Object o) {
        //Log.d("AsyncGetCloudletObjectOperation", "token " + auth.toString());
        Log.d("AsyncGetCloudletObje", "" + o);

        if ( o instanceof ApiException){

            try {
                final JSONObject jo = new JSONObject(((ApiException) o).getMessage());
                if (null != jo.get("error") && jo.get("error").equals("permission denied")){
                    cb.onPermissionDenied();
                }
                else {
                    cb.onFailure(((ApiException) o).getMessage());
                }
            }
            catch (JSONException e){
                cb.onFailure(((ApiException) o).getMessage());
            }
        }
        else if (null == o || ((PeatObjectList)o).getMeta().getTotalCount() != 1) {
            cb.onFailure("error");
        } else {
            cb.onSuccess(((PeatObjectList )o).getResult().get(0));
        }
    }
}
