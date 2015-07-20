package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import eu.openiict.client.api.PermissionsApi;
import eu.openiict.client.async.models.IPostPermissionsResponse;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.Permissions;
import eu.openiict.client.model.PermissionsResponse;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncPostPermissionsOperation extends AsyncTask<String, Void, PermissionsResponse> {

    List<Permissions> permissionsBody;
    private PermissionsApi permissionsApi;
    private String token;
    private IPostPermissionsResponse iPostPermissionsResponse;

    public AsyncPostPermissionsOperation(PermissionsApi permissionsApi, List<Permissions> permissionsBody, String token, IPostPermissionsResponse iPostPermissionsResponse) {
        this.permissionsApi = permissionsApi;
        this.permissionsBody = permissionsBody;
        this.token = token;
        this.iPostPermissionsResponse = iPostPermissionsResponse;


    }


    @Override
    protected PermissionsResponse doInBackground(String... params) {

        Log.d("AsyncPostPermissionsOperation", token);

        try {
            return permissionsApi.updatePermissions(permissionsBody, token);
        } catch (ApiException e) {
            Log.d("AsyncPostPermissionsOperation", e.toString());
            return null;
        }

    }


    @Override
    protected void onPostExecute(PermissionsResponse permissionsResponse) {
        super.onPostExecute(permissionsResponse);

        if (null == permissionsResponse) {
            iPostPermissionsResponse.onFailure("Perms Not Found");
        } else {
            try {
                Log.d("AsyncPostPermissionsOperation", "permissionsPostResult " + permissionsResponse.toString());
                iPostPermissionsResponse.onSuccess(permissionsResponse);
            } catch (Exception e) {
                iPostPermissionsResponse.onFailure(e.getMessage());
            }
            //Log.d("sessionToken", searchResults.toString());
        }
    }
}
