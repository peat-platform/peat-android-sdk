package org.peatplatform.client.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import org.peatplatform.client.async.models.IPeatAPiCall;
import org.peatplatform.client.common.ApiException;

/**
 * Created by dmccarthy on 04/12/14.
 */
public class AsyncPeatNetworkOperation extends AsyncTask<String, Void, Object> {

   private IPeatAPiCall iPEATApiCall;
   private String        authToken;


   public AsyncPeatNetworkOperation(final IPeatAPiCall iPEATApiCall, final String authToken) {
      this.iPEATApiCall = iPEATApiCall;
      this.authToken     = authToken;
   }


   @Override
   protected Object doInBackground(String... params) {
      Log.d("AsyncPeatOperation", "doInBackground");
       try {
           return iPEATApiCall.doProcess(authToken);
       } catch (Exception e) {
           Log.d("AsyncCreateCloud", e.toString());
           return e;
       }
   }


   @Override
   protected void onPostExecute(Object o) {
      super.onPostExecute(o);

      Log.d("AsyncPeatOperation", "" + o);

      if (null == o) {
         iPEATApiCall.onFailure("empty object");
      }
      else if( o instanceof ApiException){

         try {
            final JSONObject jo = new JSONObject(((ApiException) o).getMessage());
            if (null != jo.get("error") && jo.get("error").equals("permission denied")){
               iPEATApiCall.onPermissionDenied();
            }
            else {
               iPEATApiCall.onFailure(((ApiException) o).getMessage());
            }
         }
         catch (JSONException e){
            iPEATApiCall.onFailure(((ApiException) o).getMessage());
         }
      }
      else {
         iPEATApiCall.onSuccess(o);
      }
   }
}
