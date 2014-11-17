package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.api.CloudletsApi;
import eu.openiict.client.async.models.IAuthTokenResponse;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiCloudlet;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncGetCloudletOperation extends AsyncTask<String, Void, OPENiCloudlet> {

   private CloudletsApi      cloudletsApi;
   private String            token;
   private IAuthTokenResponse iAuthTokenResponse;

   public AsyncGetCloudletOperation(CloudletsApi cloudletsApi, String token, IAuthTokenResponse iAuthTokenResponse){
      this.cloudletsApi      = cloudletsApi;
      this.token             = token;
      this.iAuthTokenResponse = iAuthTokenResponse;
   }


   @Override
   protected OPENiCloudlet doInBackground(String... params) {

      Log.d("AsyncGetCloudletOperation", token);

      try {
         return cloudletsApi.getCloudletId(token);
      }
      catch (ApiException e){
         Log.d("AsyncGetCloudletOperation", e.toString());
         return null;
      }

   }


   @Override
   protected void onPostExecute(OPENiCloudlet cloudlet) {
      super.onPostExecute(cloudlet);

      if (null == cloudlet){
         iAuthTokenResponse.onFailure();
      }
      else{
         try {
            Log.d("AsyncGetCloudletOperation", "cloudlet " + cloudlet.toString());
            iAuthTokenResponse.onSuccess(cloudlet.getId());
         } catch (Exception e) {
            iAuthTokenResponse.onFailure();
         }
         Log.d("sessionToken", cloudlet.getId());
      }
   }
}
