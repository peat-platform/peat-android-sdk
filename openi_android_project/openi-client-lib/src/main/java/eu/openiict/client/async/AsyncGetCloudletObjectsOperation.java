package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.api.ObjectsApi;
import eu.openiict.client.async.models.ICloudletObjectCall;
import eu.openiict.client.async.models.ICloudletObjectsCall;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObject;
import eu.openiict.client.model.OPENiObjectList;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncGetCloudletObjectsOperation extends AsyncTask<String, Void, OPENiObjectList> {

    private int    offset;
    private int    limit;
    private String type;
    private String auth;
    private boolean id_only;
    private String with_property;
    private String property_filter;
    private String only_show_properties;
    private ICloudletObjectsCall iCloudletObjectsCall;

    private ObjectsApi objectsApi;


    public AsyncGetCloudletObjectsOperation(ObjectsApi objectsApi, Integer offset, Integer limit, String type, Boolean id_only, String with_property, String property_filter, String only_show_properties,  String auth, ICloudletObjectsCall iCloudletObjectsCall) {
        this.offset               = offset;
        this.limit                = limit;
        this.auth                 = auth;
        this.type                 = type;
        this.id_only              = id_only;
        this.with_property        = with_property;
        this.property_filter      = property_filter;
        this.only_show_properties = only_show_properties;
        this.objectsApi           = objectsApi;
        this.iCloudletObjectsCall = iCloudletObjectsCall;
    }


    @Override
    protected OPENiObjectList doInBackground(String... params) {

        try {
            return objectsApi.getObjectsWithAuthToken(offset, limit, type, id_only, with_property, property_filter, only_show_properties, auth);
        } catch (ApiException e) {
            Log.d("AsyncGetCloudletOper", e.toString());
            return null;
        }

    }


    @Override
    protected void onPostExecute(OPENiObjectList o) {
        super.onPostExecute(o);

        //Log.d("AsyncGetCloudletObjectOperation", "token " + auth.toString());
        Log.d("AsyncGetCloudletObje", "" + o);

        if (null == o) {
            iCloudletObjectsCall.onFailure();
        } else {
            iCloudletObjectsCall.onSuccess(o);
        }
    }
}
