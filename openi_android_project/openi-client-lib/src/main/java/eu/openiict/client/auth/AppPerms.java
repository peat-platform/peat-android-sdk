package eu.openiict.client.auth;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.openiict.client.R;
import eu.openiict.client.async.OPENiAsync;
import eu.openiict.client.async.models.IPostPermissionsResponse;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.Permissions;
import eu.openiict.client.model.PermissionsResponse;

public class AppPerms extends Activity implements OnClickListener {

    Button btnAcceptPerms;
    TextView permsView;
    OPENiAsync openi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openi = OPENiAsync.getOPENiAsync();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_perms_screen);

        btnAcceptPerms = (Button) findViewById(R.id.btnAcceptAppPerms);
        permsView = (TextView) findViewById(R.id.permsView);
        permsView.setText("{\n" +
                "  \"_current\": {\n" +
                "    \"_date_created\": \"2014-11-29T22:57:12.503Z\",\n" +
                "    \"status\": \"propagated\",\n" +
                "    \"perms\": {\n" +
                "      \"@objects\": {\n" +
                "        \"00000001-5203-4f5b-df3e-7f06c795775d\": {\n" +
                "          \"UPDATE\": true,\n" +
                "          \"CREATE\": true,\n" +
                "          \"DELETE\": true\n" +
                "        }\n" +
                "      },\n" +
                "      \"@types\": {\n" +
                "        \"t_e782776271a49e49d1e1dc3f32ee59b1-535\": {\n" +
                "          \"@app_level\": {\n" +
                "            \"READ\": true,\n" +
                "            \"CREATE\": true,\n" +
                "            \"UPDATE\": true,\n" +
                "            \"DELETE\": true\n" +
                "          },\n" +
                "          \"@cloudlet_level\": {}\n" +
                "        },\n" +
                "        \"t_e1c5150dfd72365d9a34b04ee7c0f2a0-154\": {\n" +
                "          \"@app_level\": {\n" +
                "            \"READ\": true,\n" +
                "            \"CREATE\": true,\n" +
                "            \"UPDATE\": true,\n" +
                "            \"DELETE\": true\n" +
                "          },\n" +
                "          \"@cloudlet_level\": {}\n" +
                "        }\n" +
                "      }\n");

        btnAcceptPerms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Permissions permissions = new Permissions();
        permissions.setAccessLevel("APP");
        permissions.setAccessType("READ");
        permissions.setRef("t_402d94dd3b59ecd8cce7e037f32932cb-1363");
        permissions.setType("type");

        List<Permissions> permissionsList = new ArrayList<Permissions>();

        permissionsList.add(permissions);
        permissions.setAccessType("CREATE");
        permissionsList.add(permissions);
        permissions.setAccessType("UPDATE");
        permissionsList.add(permissions);
        permissions.setAccessType("DELETE");
        permissionsList.add(permissions);

        openi.postPermissions(permissionsList, new IPostPermissionsResponse() {
            @Override
            public Object doProcess(String authToken) throws ApiException {
                return null;
            }

            @Override
            public void onSuccess(PermissionsResponse obj) {
                Toast.makeText(getBaseContext(), "Permissions: Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getBaseContext(), "Permissions: Error", Toast.LENGTH_SHORT).show();
            }
        });
        //Intent i = null;
        //int i1 = v.getId();
        //if (i1 == R.id.btnSingIn) {
        //i = new Intent(this, SignInActivity.class);

        //} else if (i1 == R.id.btnSignUp) {
        //i = new Intent(this, SignUpActivity.class);

        //}
        //startActivity(i);
    }


}
