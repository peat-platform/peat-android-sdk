package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import eu.openiict.client.api.SearchApi;
import eu.openiict.client.async.models.ISearchCloudletsResults;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObjectList;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncGetSearchCloudletsOperation extends AsyncTask<String, Void, Object> {

    private static final String TAG = "AsyncSearchCloudlets";

    private SearchApi searchApi;
    private String token;
    private ISearchCloudletsResults iSearchResponse;
    private String with_property;
    private String property_filter;
    private String type, offset;
    private String limit;
    private Boolean id_only;

    public AsyncGetSearchCloudletsOperation(SearchApi searchApi, String with_property, String property_filter, String type, Boolean id_only, String offset, String limit, String token, ISearchCloudletsResults searchResponse) {
        this.searchApi = searchApi;
        this.token = token;

        this.iSearchResponse = searchResponse;
        this.with_property = with_property;
        this.property_filter = property_filter;
        this.type = type;
        this.id_only = id_only;
        this.offset = offset;
        this.limit = limit;
    }


    @Override
    protected OPENiObjectList doInBackground(String... params) {

        Log.d(TAG, token);

        try {
            //Boolean id_only = true;
            return searchApi.search(with_property, property_filter, type, id_only, offset, limit, token);
        } catch (ApiException e) {
            Log.d(TAG, e.toString());
            return null;
        }

    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (null == o) {
            iSearchResponse.onFailure("empty search result");
        }
        else if( o instanceof ApiException){

            try {
                final JSONObject jo = new JSONObject(((ApiException) o).getMessage());
                if (null != jo.get("error") && jo.get("error").equals("permission denied")){
                    iSearchResponse.onPermissionDenied();
                }
                else {
                    iSearchResponse.onFailure(((ApiException) o).getMessage());
                }
            }
            catch (JSONException e){
                iSearchResponse.onFailure(((ApiException) o).getMessage());
            }
        }
        else {
            try {
                Log.d(TAG, "cloudlet " + o.toString());
                iSearchResponse.onSuccess((OPENiObjectList) o);
            } catch (Exception e) {
                iSearchResponse.onFailure(e.getMessage());
            }
            Log.d("sessionToken", o.toString());
        }
    }
}
