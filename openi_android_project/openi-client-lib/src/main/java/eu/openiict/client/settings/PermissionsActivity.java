package eu.openiict.client.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

import java.util.ArrayList;

import eu.openiict.client.R;
import eu.openiict.client.async.OPENiAsync;

public class PermissionsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "PermissionsActivity";

    private OPENiAsync openi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        this.openi = OPENiAsync.instance(this);

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


        final ArrayList<Preference> prefList = new ArrayList<Preference>();
        prefList.add(findPreference("openi_profile"));
        prefList.add(findPreference("openi_wallet"));
        prefList.add(findPreference("openi_device"));
        prefList.add(findPreference("openi_contact"));
        prefList.add(findPreference("openi_media"));
        prefList.add(findPreference("openi_webcam_micro"));
        prefList.add(findPreference("openi_social"));
        prefList.add(findPreference("openi_product_service"));
        prefList.add(findPreference("openi_health"));
        prefList.add(findPreference("openi_location"));

    }

    //Todo: Niko mporeis na to tsekareis ama exeis xrono

    @Override
    protected void onResume() {
        this.openi = OPENiAsync.instance(this);
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


