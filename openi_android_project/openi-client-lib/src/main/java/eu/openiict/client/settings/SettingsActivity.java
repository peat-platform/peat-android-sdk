package eu.peatplatform.client.settings;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import eu.peatplatform.client.R;
import eu.peatplatform.client.async.PEATAsync;


public class SettingsActivity extends ActionBarActivity {
    PEATAsync peat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PEATAsync.init("c0e73b305fda9fa7510bfb08c49870da","d14dc301f086ba6615303f56458fec564a72cfe7a82054b908b13b228405cad7",this, "permissions.json");
        peat = PEATAsync.instance(this);
        setContentView(R.layout.activity_settings);


        final Button buttonPermissions = (Button) findViewById(R.id.permissions);
        buttonPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PermissionsActivity.class);
                startActivity(intent);
            }
        });

        final Button buttonAlerts = (Button) findViewById(R.id.creatingAnAlert);

        buttonAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), creatingAnAlert.class);
                startActivity(intent);
            }
        });

        //final Button buttonSettings = (Button) findViewById(R.id.permissions);
    }

    @Override
    protected void onResume() {
        //PEATAsync.init("c0e73b305fda9fa7510bfb08c49870da","d14dc301f086ba6615303f56458fec564a72cfe7a82054b908b13b228405cad7",this, "permissions.json");
        peat = PEATAsync.instance(this);
        super.onResume();

        //getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
