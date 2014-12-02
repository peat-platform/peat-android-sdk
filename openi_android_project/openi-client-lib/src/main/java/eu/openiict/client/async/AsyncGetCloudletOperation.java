package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.api.CloudletsApi;
import eu.openiict.client.async.models.ICloudletIdResponse;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiCloudlet;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncGetCloudletOperation extends AsyncTask<String, Void, OPENiCloudlet> {

    private CloudletsApi cloudletsApi;
    private String token;
    private ICloudletIdResponse IcloudletIdResponse;

    public AsyncGetCloudletOperation(CloudletsApi cloudletsApi, String token, ICloudletIdResponse cloudletIdResponse) {
        this.cloudletsApi = cloudletsApi;
        this.token = token;
        this.IcloudletIdResponse = cloudletIdResponse;
    }


    @Override
    protected OPENiCloudlet doInBackground(String... params) {

        Log.d("AsyncGetCloudletOperation", token);

        try {
            return cloudletsApi.getCloudletId(token);
        } catch (ApiException e) {
            Log.d("AsyncGetCloudletOperation", e.toString());
            return null;
        }

    }


    @Override
    protected void onPostExecute(OPENiCloudlet cloudlet) {
        super.onPostExecute(cloudlet);

        if (null == cloudlet) {
            IcloudletIdResponse.onFailure();
        } else {
            try {
                Log.d("AsyncGetCloudletOperation", "cloudlet " + cloudlet.toString());
                IcloudletIdResponse.onSuccess(cloudlet.getId());
            } catch (Exception e) {
                IcloudletIdResponse.onFailure();
            }
        }
    }
}
