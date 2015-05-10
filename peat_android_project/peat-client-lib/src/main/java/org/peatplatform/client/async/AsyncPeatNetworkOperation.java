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

   private IPeatAPiCall iOPENiApiCall;
   private String        authToken;


   public AsyncPeatNetworkOperation(final IPeatAPiCall iOPENiApiCall, final String authToken) {
      this.iOPENiApiCall = iOPENiApiCall;
      this.authToken     = authToken;
   }


   @Override
   protected Object doInBackground(String... params) {
      Log.d("AsyncOpeniOperation", "doInBackground");
       try {
           return iOPENiApiCall.doProcess(authToken);
       } catch (Exception e) {
           Log.d("AsyncCreateCloud", e.toString());
           return e;
       }
   }


   @Override
   protected void onPostExecute(Object o) {
      super.onPostExecute(o);

      Log.d("AsyncOpeniOperation", "" + o);

      if (null == o) {
         iOPENiApiCall.onFailure("empty object");
      }
      else if( o instanceof ApiException){

         try {
            final JSONObject jo = new JSONObject(((ApiException) o).getMessage());
            if (null != jo.get("error") && jo.get("error").equals("permission denied")){
               iOPENiApiCall.onPermissionDenied();
            }
            else {
               iOPENiApiCall.onFailure(((ApiException) o).getMessage());
            }
         }
         catch (JSONException e){
            iOPENiApiCall.onFailure(((ApiException) o).getMessage());
         }
      }
      else {
         iOPENiApiCall.onSuccess(o);
      }
   }
}
