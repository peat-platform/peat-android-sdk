package eu.openiict.client.async;

import android.os.AsyncTask;
import android.util.Log;

import eu.openiict.client.async.models.IOPENiAPiCall;

/**
 * Created by dmccarthy on 04/12/14.
 */
public class AsyncOpeniNetworkOperation extends AsyncTask<String, Void, Object> {

   private IOPENiAPiCall iOPENiApiCall;
   private String        authToken;


   public AsyncOpeniNetworkOperation( final IOPENiAPiCall iOPENiApiCall, final String authToken ) {
      this.iOPENiApiCall = iOPENiApiCall;
      this.authToken     = authToken;
   }


   @Override
   protected Object doInBackground(String... params) {
      Log.d("AsyncOpeniOperation", "doInBackground");
       try {
           return iOPENiApiCall.doProcess(authToken);
       } catch (Exception e) {
           Log.d("AsyncCreateCloudletObjectOperation", e.toString());
           return null;
       }
   }


   @Override
   protected void onPostExecute(Object o) {
      super.onPostExecute(o);

      Log.d("AsyncOpeniOperation", "" + o);

      if (null == o) {
         iOPENiApiCall.onFailure();
      } else {
         iOPENiApiCall.onSuccess(o);
      }
   }
}
