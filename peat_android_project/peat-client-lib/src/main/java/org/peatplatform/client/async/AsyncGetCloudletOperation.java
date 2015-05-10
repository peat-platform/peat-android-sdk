package org.peatplatform.client.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import org.peatplatform.client.api.CloudletsApi;
import org.peatplatform.client.async.models.ICloudletIdResponse;
import org.peatplatform.client.common.ApiException;
import org.peatplatform.client.model.UserCloudlet;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncGetCloudletOperation extends AsyncTask<String, Void, Object> {

   private CloudletsApi cloudletsApi;
   private String token;
   private ICloudletIdResponse IcloudletIdResponse;

   public AsyncGetCloudletOperation(CloudletsApi cloudletsApi, String token, ICloudletIdResponse cloudletIdResponse) {
      this.cloudletsApi = cloudletsApi;
      this.token = token;
      this.IcloudletIdResponse = cloudletIdResponse;
   }


   @Override
   protected Object doInBackground(String... params) {

      Log.d("AsyncGetCloudletOperati", token);

      try {
         return cloudletsApi.getCloudletId(token);
      } catch (ApiException e) {
         Log.d("AsyncGetCloudletOpe", e.toString());
         return e;
      }

   }

   @Override
   protected void onPostExecute(Object o) {

      if (null == o) {
         IcloudletIdResponse.onFailure("empty message");
      }
      else if( o instanceof ApiException){

         try {
            final JSONObject jo = new JSONObject(((ApiException) o).getMessage());
            if (null != jo.get("error") && jo.get("error").equals("permission denied")){
               IcloudletIdResponse.onPermissionDenied();
            }
            else {
               IcloudletIdResponse.onFailure(((ApiException) o).getMessage());
            }
         }
         catch (JSONException e){
            IcloudletIdResponse.onFailure(((ApiException) o).getMessage());
         }
      }
      else {
         try {
            Log.d("AsyncGetCloudletOpe", "cloudlet " + o.toString());
            IcloudletIdResponse.onSuccess(((UserCloudlet) o).getId());
         } catch (Exception e) {
            IcloudletIdResponse.onFailure(e.getMessage());
         }
      }
   }
}