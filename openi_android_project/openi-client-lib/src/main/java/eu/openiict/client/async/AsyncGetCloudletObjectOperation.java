package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.async.models.ICloudletObjectCall;
import eu.openiict.client.common.ApiException;

/**
 * Created by dmccarthy on 15/11/14.
 */
public class AsyncGetCloudletObjectOperation extends AsyncTask<String, Void, Object> {

   private String              auth;
   private ICloudletObjectCall iCloudletObjectCall;


   public AsyncGetCloudletObjectOperation(String auth, ICloudletObjectCall iCloudletObjectCall){
      this.auth                = auth;
      this.iCloudletObjectCall = iCloudletObjectCall;
   }


   @Override
   protected Object doInBackground(String... params) {

      try {
         return iCloudletObjectCall.doProcess(auth);
      }
      catch (ApiException e){
         return null;
      }

   }


   @Override
   protected void onPostExecute(Object o) {
      super.onPostExecute(o);

      Log.d("AsyncGetCloudletObjectOperation", "token " + auth.toString());
      Log.d("AsyncGetCloudletObjectOperation", "" + o);

      if (null == o){
         iCloudletObjectCall.onFailure();
      }
      else{
         iCloudletObjectCall.onSuccess(o);
      }
   }
}
