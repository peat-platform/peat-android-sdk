package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import eu.openiict.client.api.ObjectsApi;
import eu.openiict.client.async.models.ISearchOneCloudletResults;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObjectList;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncSearchOneCloudletOperation extends AsyncTask<String, Void, Object> {

    private String with_property, property_filter, type, only;
    private Integer offset, limit;
    private Boolean id_only;
    private ObjectsApi searchApi;
    private String cloudletID,token, only_show_properties;
    private ISearchOneCloudletResults IsearchResponse;

    public AsyncSearchOneCloudletOperation(ObjectsApi searchApi, String cloudletID, String with_property, String property_filter, String type, Boolean id_only, String only_show_properties, Integer offset, Integer limit, String token, ISearchOneCloudletResults searchResponse) {
        this.searchApi = searchApi;
        this.token = token;

        this.IsearchResponse = searchResponse;
        this.cloudletID = cloudletID;
        this.with_property = with_property;
        this.property_filter = property_filter;
        this.type = type;
        this.id_only = id_only;
        this.offset = offset;
        this.limit = limit;
        this.only_show_properties = only_show_properties;
    }


    @Override
    protected Object doInBackground(String... params) {

        Log.d("AsyncGetCloudlet", token);

        try {
            //Boolean id_only = true;
            return searchApi.listObjects(cloudletID, offset, limit, type, id_only, with_property, property_filter, only_show_properties, token);
        } catch (ApiException e) {
            Log.d("AsyncSearchOneClou", e.toString());
            return e;
        }

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (null == o) {
            IsearchResponse.onFailure("empty response");
        }
        else if( o instanceof ApiException){

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
                Log.d("AsyncSearchOneCloudl", "search results: " + o.toString());
                IsearchResponse.onSuccess((OPENiObjectList) o);
            } catch (Exception e) {
                IsearchResponse.onFailure(e.getMessage());
            }
            Log.d("sessionToken", o.toString());
        }
    }
}
