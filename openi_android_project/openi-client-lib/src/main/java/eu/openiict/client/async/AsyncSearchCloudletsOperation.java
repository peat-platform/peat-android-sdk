package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.api.SearchApi;
import eu.openiict.client.async.models.ISearchCloudletsResults;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObjectList;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncSearchCloudletsOperation extends AsyncTask<String, Void, OPENiObjectList> {

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
    protected OPENiObjectList doInBackground(String... params) {

        Log.d("AsyncGetCloudletOperation", token);

        try {
            //Boolean id_only = true;
            return searchApi.search(with_property, property_filter, type, id_only, offset, limit, token);
        } catch (ApiException e) {
            Log.d("AsyncGetCloudletOperation", e.toString());
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
                Log.d("AsyncGetSearchCloudletsOperation", "search results: " + searchResults.toString());
                IsearchResponse.onSuccess(searchResults);
            } catch (Exception e) {
                IsearchResponse.onFailure();
            }
            Log.d("sessionToken", searchResults.toString());
        }
    }
}
