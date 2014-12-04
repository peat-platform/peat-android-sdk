package eu.openiict.client.permissions;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.openiict.client.R;
import eu.openiict.client.async.OPENiAsync;
import eu.openiict.client.async.models.ICloudletIdResponse;
import eu.openiict.client.async.models.IPostPermissionsResponse;
import eu.openiict.client.async.models.ISearchOneCloudletResults;
import eu.openiict.client.common.ApiException;
import eu.openiict.client.model.OPENiObject;
import eu.openiict.client.model.OPENiObjectList;
import eu.openiict.client.model.Permissions;
import eu.openiict.client.model.PermissionsResponse;

public class PermissionsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    //private Integer i = -1;
    // Profile
    public static final String KEY_PROFILE_ASSIGNMENT = "pref_profile_assignment";
    // Location
    public static final String KEY_LOCATION_PROVIDER = "pref_location_provider";
    public static final String KEY_LOCATION_ASSIGNMENT = "pref_location_assignment";
    // Media
    public static final String KEY_MEDIA_ASSIGNMENT = "pref_media_assignment";
    private static OPENiAsync openi;
    private static String cloudletID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);*/

        this.openi = OPENiAsync.instance();

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

        for (Integer y = 0; y < 10; y++) {
            prefList.get(y).setEnabled(false);
        }
        //final Preference prefEntry = findPreference(pref_key);
        //ProgressBar spinner = new ProgressBar(this);
        //spinner.setVisibility(View.VISIBLE);

        // get cloudletID
        ICloudletIdResponse cloudletIDresp = new ICloudletIdResponse() {
            @Override
            public void onSuccess(String cloudletID) {
                PermissionsActivity.cloudletID = cloudletID;
                // profile
                filterMenusOnCloudletObjects("t_402d94dd3b59ecd8cce7e037f32932cb-1363", prefList.get(0));
                // wallet
                filterMenusOnCloudletObjects("t_cc9178ba0c9167197f8c897e31bc9949-428", prefList.get(1));
                // device
                filterMenusOnCloudletObjects("", prefList.get(2));
                // contact
                filterMenusOnCloudletObjects("", prefList.get(3));
                // media
                filterMenusOnCloudletObjects("t_682c8f8b85cece5e8fabe4e35999cf83-855", prefList.get(4));
                filterMenusOnCloudletObjects("t_bd3ea322c9b29d0280b4b915098ecb69-743", prefList.get(4));
                filterMenusOnCloudletObjects("t_eb3b2abb860e2028da3794e5161d62d4-743", prefList.get(4));
                // webcam and microphone
                filterMenusOnCloudletObjects("", prefList.get(5));
                // social activity
                filterMenusOnCloudletObjects("", prefList.get(6));
                // product and services
                filterMenusOnCloudletObjects("t_40122ca32ad4d9f2ec28241835264fa6-638", prefList.get(7));
                filterMenusOnCloudletObjects("t_613359e6e063e2e4ae27083cce39d4ea-638", prefList.get(7));
                // health and condition
                filterMenusOnCloudletObjects("t_8954a822010d9fb2a344fbab35328e44-641", prefList.get(8));
                filterMenusOnCloudletObjects("t_cf66ce4d4cdf08636b78742cc005332a-967", prefList.get(8));
                // location
                filterMenusOnCloudletObjects("t_0578381d8207a9302a6b31b9510bb098-399", prefList.get(9));
            }

            @Override
            public void onFailure() {
                System.out.println("Booom error1");
            }
        };
        //this.openi.prefs.getString()


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
        this.openi = OPENiAsync.instance();
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

    public void filterMenusOnCloudletObjects(String typeid, Preference pref_key) {

        final Preference prefEntry = pref_key;

        /*Preference.OnPreferenceClickListener clickListener = new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Toast toast = Toast.makeText(PermissionsActivity.this, preference.getTitle(), Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
        };
        prefEntry.setOnPreferenceClickListener(clickListener);*/

        ISearchOneCloudletResults cloudletSearchResults = new ISearchOneCloudletResults() {
            @Override
            public OPENiObjectList doProcess(String authToken) throws ApiException {
                return null;
            }

            @Override
            public void onSuccess(OPENiObjectList obj) {
                System.out.println("Baaaang search: " + obj.getResult());
                if (obj.getResult().size() == 0) {
                    //prefEntry.setEnabled(false);
                    //prefEntry.setEnabled(true);
                } else {
                    prefEntry.setEnabled(true);
                    populateProfile(obj.getResult());
                }
            }

            @Override
            public void onFailure() {
                //prefEntry.setEnabled(false);
                System.out.println("Boom error pref");
            }
        };
        PermissionsActivity.openi.searchInOneCloudlet(cloudletID, null, null, typeid, false, null, null, null, cloudletSearchResults);

    }

    public void populateProfile(List<OPENiObject> results) {
        findPreference("openi_username").setSummary(results.get(0).getData().get("username").toString());

        findPreference("openi_first_name").setSummary(results.get(0).getData().get("first_name").toString());

        findPreference("openi_last_name").setSummary(results.get(0).getData().get("last_name").toString());

        findPreference("openi_email").setSummary(results.get(0).getData().get("email").toString());

        findPreference("openi_password").setSummary(results.get(0).getData().get("password").toString());

        findPreference("pref_profile").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //Log.i("onPreferenceChange", "NumberPicker Changed");
                //Toast.makeText(getBaseContext(), "CHANGEEEED !!!" + newValue.toString(), Toast.LENGTH_SHORT).show();

                // TODO: do this in asyncTask
                Permissions permissions = new Permissions();
                permissions.setAccessLevel("APP");
                permissions.setAccessType("READ");
                permissions.setRef("t_402d94dd3b59ecd8cce7e037f32932cb-1363");
                permissions.setType("type");
                //PermissionsApi permissionsapi = new PermissionsApi();

                List<Permissions> permissionsList = new ArrayList<Permissions>();

                if (newValue.toString().equals("false")) {
                    openi.postPermissions(permissionsList, new IPostPermissionsResponse() {
                        @Override
                        public Object doProcess(String authToken) throws ApiException {
                            return null;
                        }

                        @Override
                        public void onSuccess(PermissionsResponse obj) {
                            Toast.makeText(getBaseContext(), "Permissions: Denied", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(getBaseContext(), "Permissions: Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

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
                }

                return true;
            }
        });

        //openi_username.setSummary();
    }
}


