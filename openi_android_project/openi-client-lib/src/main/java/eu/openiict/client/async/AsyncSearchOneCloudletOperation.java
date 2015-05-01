package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.api.ObjectsApi;
import eu.openiict.client.async.models.ISearchOneCloudletResults;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObjectList;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncSearchOneCloudletOperation extends AsyncTask<String, Void, OPENiObjectList> {

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
    protected OPENiObjectList doInBackground(String... params) {

        Log.d("AsyncGetCloudlet", token);

        try {
            //Boolean id_only = true;
            return searchApi.listObjects(cloudletID, offset, limit, type, id_only, with_property, property_filter, only_show_properties, token);
        } catch (ApiException e) {
            Log.d("AsyncSearchOneClou", e.toString());
            return null;
        }

    }

    @Override
    protected void onPostExecute(OPENiObjectList searchResults) {
        super.onPostExecute(searchResults);

        if (null == searchResults) {
            IsearchResponse.onFailure();
        } else {
            try {
                Log.d("AsyncSearchOneCloudl", "search results: " + searchResults.toString());
                IsearchResponse.onSuccess(searchResults);
            } catch (Exception e) {
                IsearchResponse.onFailure();
            }
            Log.d("sessionToken", searchResults.toString());
        }
    }
}
