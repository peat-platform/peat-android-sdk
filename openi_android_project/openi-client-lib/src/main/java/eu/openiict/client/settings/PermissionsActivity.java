package eu.peatplatform.client.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

import java.util.ArrayList;

import eu.peatplatform.client.R;
import eu.peatplatform.client.async.PEATAsync;

public class PermissionsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "PermissionsActivity";

    private PEATAsync peat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        this.peat = PEATAsync.instance(this);

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


        final ArrayList<Preference> prefList = new ArrayList<Preference>();
        prefList.add(findPreference("peat_profile"));
        prefList.add(findPreference("peat_wallet"));
        prefList.add(findPreference("peat_device"));
        prefList.add(findPreference("peat_contact"));
        prefList.add(findPreference("peat_media"));
        prefList.add(findPreference("peat_webcam_micro"));
        prefList.add(findPreference("peat_social"));
        prefList.add(findPreference("peat_product_service"));
        prefList.add(findPreference("peat_health"));
        prefList.add(findPreference("peat_location"));

    }

    //Todo: Niko mporeis na to tsekareis ama exeis xrono

    @Override
    protected void onResume() {
        this.peat = PEATAsync.instance(this);
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //sharedPreferences.getBoolean(key, false)

        Log.i(TAG, "Sharef PreferenceChanged: " + key);

    }

}


