package eu.openiict.client.permissions;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import eu.openiict.client.R;

public class PermissionsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    // Profile
    public static final String KEY_PROFILE_ASSIGNMENT = "pref_profile_assignment";

    // Location
    public static final String KEY_LOCATION_PROVIDER = "pref_location_provider";
    public static final String KEY_LOCATION_ASSIGNMENT = "pref_location_assignment";

    // Media
    public static final String KEY_MEDIA_ASSIGNMENT = "pref_media_assignment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Profile
        Preference profileAssignmentPreference = findPreference(KEY_PROFILE_ASSIGNMENT);
        profileAssignmentPreference.setSummary(sharedPreferences.getString(KEY_PROFILE_ASSIGNMENT, "Current App"));

        // Location
        Preference locationProviderPreference = findPreference(KEY_LOCATION_PROVIDER);
        locationProviderPreference.setSummary(sharedPreferences.getString(KEY_LOCATION_PROVIDER, ""));
        Preference locationAssignmentPreference = findPreference(KEY_LOCATION_ASSIGNMENT);
        locationAssignmentPreference.setSummary(sharedPreferences.getString(KEY_LOCATION_ASSIGNMENT, "Current App"));

        // Media
        Preference mediaAssignmentPreference = findPreference(KEY_MEDIA_ASSIGNMENT);
        mediaAssignmentPreference.setSummary(sharedPreferences.getString(KEY_MEDIA_ASSIGNMENT, "Current App"));
    }

    @Override
    protected void onResume() {
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
        if (key.equals(KEY_LOCATION_PROVIDER)) {
            Preference locationProviderPreference = findPreference(KEY_LOCATION_PROVIDER);
            locationProviderPreference.setSummary(sharedPreferences.getString(key, ""));
        }
    }
}
