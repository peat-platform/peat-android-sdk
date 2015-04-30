package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.api.ObjectsApi;
import eu.openiict.client.async.models.ICloudletObjectResponse;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObjectList;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncGetLatestObjectOperation extends AsyncTask<String, Void, OPENiObjectList> {

    private String type;
    private String auth;
    private ICloudletObjectResponse cb;

    private ObjectsApi objectsApi;


    public AsyncGetLatestObjectOperation(ObjectsApi objectsApi, String type, String auth, ICloudletObjectResponse cb) {
        this.auth                 = auth;
        this.type                 = type;
        this.objectsApi           = objectsApi;
        this.cb                   = cb;
    }


    @Override
    protected OPENiObjectList doInBackground(String... params) {

        try {
            return objectsApi.listObjectsWithAuthToken(0, 1, type, false, null, null, null, auth, "descending");
        } catch (ApiException e) {
            Log.d("AsyncGetCloudletOper", e.toString());
            return null;
        }

    }


    @Override
    protected void onPostExecute(OPENiObjectList l) {
        //Log.d("AsyncGetCloudletObjectOperation", "token " + auth.toString());
        Log.d("AsyncGetCloudletObje", "" + l);

        if (null == l || l.getMeta().getTotalCount() != 1) {
            cb.onFailure();
        } else {
            cb.onSuccess(l.getResult().get(0));
        }
    }
}
