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
public class AsyncSearchCloudletsOperation extends AsyncTask<String, Void, Object> {

    String with_property, property_filter, type, offset, limit;
    Boolean id_only;
    private SearchApi searchApi;
    private String token;
    private ISearchCloudletsResults IsearchResponse;

    public AsyncSearchCloudletsOperation(SearchApi searchApi, String with_property, String property_filter, String type, Boolean id_only, String offset, String limit, String token, ISearchCloudletsResults searchResponse) {
        this.searchApi = searchApi;
        this.token = token;

        this.IsearchResponse = searchResponse;
        this.with_property = with_property;
        this.property_filter = property_filter;
        this.type = type;
        this.id_only = id_only;
        this.offset = offset;
        this.limit = limit;
    }


    @Override
    protected Object doInBackground(String... params) {

        Log.d("AsyncGetClo", token);

        try {
            //Boolean id_only = true;
            return searchApi.search(with_property, property_filter, type, id_only, offset, limit, token);
        } catch (ApiException e) {
            Log.d("AsyncGetClo", e.toString());
            return e;
        }

    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (null == o) {
            IsearchResponse.onFailure("empty search result");
        } else if( o instanceof ApiException){

            try {
                final JSONObject jo = new JSONObject(((ApiException) o).getMessage());
                if (null != jo.get("error") && jo.get("error").equals("permission denied")){
                    IsearchResponse.onPermissionDenied();
                }
                else {
                    IsearchResponse.onFailure(((ApiException) o).getMessage());
                }
            }
            catch (JSONException e){
                IsearchResponse.onFailure(((ApiException) o).getMessage());
            }
        }
        else {
            try {
                Log.d("AsyncGetSearchClou", "search results: " + o.toString());
                IsearchResponse.onSuccess((OPENiObjectList) o);
            } catch (Exception e) {
                IsearchResponse.onFailure(e.getMessage());
            }
            Log.d("sessionToken", o.toString());
        }
    }
}
