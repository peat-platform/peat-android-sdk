package eu.openiict.client.permissions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.openiict.client.api.TypesApi;
import eu.openiict.client.async.OPENiAsync;
import eu.openiict.client.async.models.IOPENiAPiCall;
import eu.openiict.client.async.models.IPermissionsResult;
import eu.openiict.client.async.models.IPostPermissionsResponse;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.Badge;
import eu.openiict.client.model.OPENiType;
import eu.openiict.client.model.Permissions;
import eu.openiict.client.model.PermissionsResponse;
import eu.openiict.client.utils.OPENiUtils;


public class ProcessAppPermissions {

   private IPermissionsResult permissionsResult;
   private Context            context;
   private String             permsFile;

   public ProcessAppPermissions(Context context, IPermissionsResult permissionsResult, String permsFile){
      this.permissionsResult = permissionsResult;
      this.context           = context;
      this.permsFile         = permsFile;
   }


   public void execute(){

      final List<Permissions> perms        = new ArrayList<Permissions>();
      final JSONArray         permsJsonArr = readPermissionsFromFile();

      try {
         for (int i = 0; i < permsJsonArr.length(); i++) {
            final JSONObject o  = (JSONObject) permsJsonArr.getJSONObject(i);
            final Permissions p = new Permissions();


            p.setType(o.getString("type"));
            p.setRef(o.getString("ref"));
            p.setAccessLevel(o.getString("access_level"));
            p.setAccessType(o.getString("access_type"));

            perms.add(p);
         }
      }
      catch (JSONException e){
         permissionsResult.onFailure("error parsing JSON");
         return;
      }

      OPENiAsync.instance().execOpeniApiCall(new IOPENiAPiCall() {
         @Override
         public Object doProcess(String authToken) {

            final Map<Permissions, OPENiType> types = new HashMap<Permissions, OPENiType>();
            final TypesApi                    tApi  = new TypesApi();

            for (Permissions p : perms) {
               try {
                  if ( "type".equals(p.getType()) ) {
                     final OPENiType type = tApi.getType(p.getRef(), null);
                     types.put(p, type);
                  }
                  else{
                     types.put(p, null);
                  }
               } catch (ApiException e) {
                  e.printStackTrace();
               }
            }
            return types;
         }


         @Override
         public void onSuccess(Object object) {
            final Map<Permissions, OPENiType> perms = (Map<Permissions, OPENiType>) object;

            AlertDialog.Builder   builder    = new AlertDialog.Builder(context);
            builder.setTitle("Cloudlet Access Request");

            final TextView text = new TextView(context);

            text.append("This app wants the following access to your data:\n\n\n" );

            for ( Map.Entry<Permissions, OPENiType> e : perms.entrySet()){
               if (null == e.getValue()){
                  continue;
               }
               text.append(Character.toUpperCase(e.getKey().getAccessLevel().charAt(0)) + e.getKey().getAccessLevel().substring(1)
                     + " level, " +
                     e.getKey().getAccessType() + " access for " +
                     e.getValue().getReference() + " object.\n");
            }

            text.append("\n\n");

            builder.setView(text);

            builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  sendPermsToServer(new ArrayList<Permissions>(perms.keySet()));
               }
            });
            builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  dialog.cancel();
               }
            });

            builder.show();
         }

         @Override
         public void onFailure() {
            permissionsResult.onFailure("Error getting types..");
         }
      });

   }

   private void sendPermsToServer(final List<Permissions> perms){
      OPENiAsync.instance().postPermissions(perms, new IPostPermissionsResponse() {
         @Override
         public Object doProcess(String s) throws ApiException {
            return null;
         }

         @Override
         public void onSuccess(PermissionsResponse permissionsResponse) {
            Log.d("A", "" + permissionsResponse);
            permissionsResult.onSuccess(perms);
         }

         @Override
         public void onFailure() {
            permissionsResult.onFailure("Error setting permissions..");
         }
      });
   }


   private JSONArray readPermissionsFromFile(){

      try {
         final InputStream is = context.getAssets().open(permsFile);

         final int size = is.available();

         final byte[] buffer = new byte[size];

         is.read(buffer);

         is.close();

         final String json = new String(buffer, "UTF-8");

         return new JSONArray( json );
      }
      catch (Exception ex) {
         Log.d("A", ex.toString());
         ex.printStackTrace();
         permissionsResult.onFailure("Error reading permissions file.");
         return null;
      }

   }


//   private void updateWebView(String result){
//
//      final String html = "<html><body>" + result + "</body></html>";
//
//      webView.getSettings().setJavaScriptEnabled(true);
//      webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
//   }
}


