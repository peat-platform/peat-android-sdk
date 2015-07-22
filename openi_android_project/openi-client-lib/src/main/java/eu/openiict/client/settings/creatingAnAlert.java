package eu.peatplatform.client.settings;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import eu.peatplatform.client.R;
import eu.peatplatform.client.api.SubscriptionApi;
import eu.peatplatform.client.async.PEATAsync;
import eu.peatplatform.client.async.models.IPEATAPiCall;
import eu.peatplatform.client.model.PEATType;
import eu.peatplatform.client.model.Subscription;

public class creatingAnAlert extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    //private Integer i = -1;
    // Profile
    public static final String KEY_PROFILE_ASSIGNMENT = "pref_profile_assignment";
    // Location
    public static final String KEY_LOCATION_PROVIDER = "pref_location_provider";
    public static final String KEY_LOCATION_ASSIGNMENT = "pref_location_assignment";
    // Media
    public static final String KEY_MEDIA_ASSIGNMENT = "pref_media_assignment";
    private static PEATAsync peat;
    private static String cloudletID;
    private static ArrayList<PEATType> typeList = new ArrayList<PEATType>();
    private static CheckBoxPreference curPreference;
    PermissionsActivity yo = new PermissionsActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.peat = PEATAsync.instance(this);

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.alerts);
        //addPreferencesFromResource(R.xml.preferences);



        final ArrayList<Preference> prefList = new ArrayList<Preference>();

        prefList.add(findPreference("peat_wallet_alerts"));
        prefList.add(findPreference("peat_device_alerts"));
        prefList.add(findPreference("peat_contact_alerts"));
        prefList.add(findPreference("peat_media_alerts"));
        prefList.add(findPreference("peat_webcam_micro_alerts"));
        prefList.add(findPreference("peat_social_alerts"));
        prefList.add(findPreference("peat_product_service_alerts"));
        prefList.add(findPreference("peat_health_alerts"));
        prefList.add(findPreference("peat_location_alerts"));

        //Setting up the Checkboxes
        final CheckBoxPreference checkboxPref = (CheckBoxPreference)getPreferenceManager().findPreference("pref_profile_cat_alerts");
        final CheckBoxPreference checkboxPref1 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Account_alerts");
        final CheckBoxPreference checkboxPref2 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_User_alerts");
        final CheckBoxPreference checkboxPref3 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_online_payment_cat_alerts");
        final CheckBoxPreference checkboxPref4 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Card_alerts");
        final CheckBoxPreference checkboxPref5 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Wallet_alerts");
        final CheckBoxPreference checkboxPref6 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_device_cat_alerts");
        final CheckBoxPreference checkboxPref7 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Application_alerts");
        final CheckBoxPreference checkboxPref8 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Device_alerts");
        final CheckBoxPreference checkboxPref9 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Contact_alerts");
        final CheckBoxPreference checkboxPref10 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_media_alerts");
        final CheckBoxPreference checkboxPref11 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Photo_alerts");
        final CheckBoxPreference checkboxPref12 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Audio_alerts");
        final CheckBoxPreference checkboxPref13 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Video_alerts");
        final CheckBoxPreference checkboxPref14 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Article_alerts");
        final CheckBoxPreference checkboxPref15 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_File_alerts");
        final CheckBoxPreference checkboxPref16 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Playlist_alerts");
        final CheckBoxPreference checkboxPref17 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Folder_alerts");
        final CheckBoxPreference checkboxPref18 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_social_cat_alerts");
        final CheckBoxPreference checkboxPref19 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Like_alerts");
        final CheckBoxPreference checkboxPref20 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Comment_alerts");
        final CheckBoxPreference checkboxPref21 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Dislike_alerts");
        final CheckBoxPreference checkboxPref22 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Favorite_alerts");
        final CheckBoxPreference checkboxPref23 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Friendship_alerts");
        final CheckBoxPreference checkboxPref24 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Tag_alerts");
        final CheckBoxPreference checkboxPref25 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Badge_alerts");
        final CheckBoxPreference checkboxPref26 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Game_alerts");
        final CheckBoxPreference checkboxPref27 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Note_alerts");
        final CheckBoxPreference checkboxPref28 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_product_cat_alerts");
        final CheckBoxPreference checkboxPref29 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Service_alerts");
        final CheckBoxPreference checkboxPref30 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Products_alerts");
        final CheckBoxPreference checkboxPref31 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_health_cat_alerts");
        final CheckBoxPreference checkboxPref32 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Nutrition_alerts");
        final CheckBoxPreference checkboxPref33 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Measurement_alerts");
        final CheckBoxPreference checkboxPref34 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Workout_alerts");
        final CheckBoxPreference checkboxPref35 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_location_cat_alerts");
        final CheckBoxPreference checkboxPref36 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Event_alerts");
        final CheckBoxPreference checkboxPref37 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Places_alerts");
        final CheckBoxPreference checkboxPref38 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Route_alerts");
        final CheckBoxPreference checkboxPref39 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Review_alerts");
        final CheckBoxPreference checkboxPref40 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Checkin_alerts");
        final CheckBoxPreference checkboxPref41 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Status_alerts");
        final CheckBoxPreference checkboxPref42 = (CheckBoxPreference)getPreferenceManager().findPreference("pref_Shop_alerts");



        // 1. Instantiate an AlertDialog.Builder with its constructor
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics

        builder.setTitle("Editing alert confirmation");
        builder.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

/*                openi.execOpeniApiCall(new IOPENiAPiCall() {
                    @Override
                    public Object doProcess(String authToken) {
                        SubscriptionApi sub = new SubscriptionApi();
                        Subscription subBody = new Subscription();

                        subBody.setNotificationType("gcm");

                        //its wrong UI
                        if (curPreference==checkboxPref){
                            //needs to be changed in the openisync the way you get the id
                            subBody.setCloudletid(openi.getPref("cloudletid"));
                        }

                        return null;
                    }

                    @Override
                    public void onSuccess(Object object) {
                        Log.d("OPENi", object.toString());
                    }

                    @Override
                    public void onFailure() {
                        Log.d("OPENi", "Failed");
                    }
                });*/

                // User clicked OK button

                if(curPreference.isChecked())
                {
                    curPreference.setChecked(false);
                }else if (!curPreference.isChecked())
                {
                    curPreference.setChecked(true);
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // User cancelled the dialog
                //final CheckBoxPreference pref = (CheckBoxPreference) preference;

                //final CheckBoxPreference pref = (CheckBoxPreference)preference;
                //SharedPreferences prefe = getPreferenceManager().getSharedPreferences();
                //final CheckBoxPreference pref = (CheckBoxPreference) findPreference();

                if(curPreference.isChecked())
                {
                    curPreference.setChecked(true);
                }else if (!curPreference.isChecked())
                {
                    curPreference.setChecked(false);
                }
            }
        });

        //making one onclicklistener for all the checkboxes
         final Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
             @Override
            public boolean onPreferenceClick(Preference preference) {
                final CheckBoxPreference pref = (CheckBoxPreference) preference;//findPreference("pref_profile_cat_alerts");
                 curPreference=pref;

                if (pref.isChecked()) {
                    pref.setChecked(false);
                    // 3. Get the AlertDialog from create()
                    builder.setMessage("Are you sure that you want to create an alert?");
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (!pref.isChecked()) {
                    pref.setChecked(true);
                    // 3. Get the AlertDialog from create()
                    builder.setMessage("Are you sure that you want to delete the alert?");
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }

                return false;
            }
        };

        //all the checkboxes are assignes to the click listener
        checkboxPref.setOnPreferenceClickListener(listener);
        checkboxPref1.setOnPreferenceClickListener(listener);
        checkboxPref2.setOnPreferenceClickListener(listener);
        checkboxPref3.setOnPreferenceClickListener(listener);
        checkboxPref4.setOnPreferenceClickListener(listener);
        checkboxPref5.setOnPreferenceClickListener(listener);
        checkboxPref6.setOnPreferenceClickListener(listener);
        checkboxPref7.setOnPreferenceClickListener(listener);
        checkboxPref8.setOnPreferenceClickListener(listener);
        checkboxPref9.setOnPreferenceClickListener(listener);
        checkboxPref10.setOnPreferenceClickListener(listener);
        checkboxPref11.setOnPreferenceClickListener(listener);
        checkboxPref12.setOnPreferenceClickListener(listener);
        checkboxPref13.setOnPreferenceClickListener(listener);
        checkboxPref14.setOnPreferenceClickListener(listener);
        checkboxPref15.setOnPreferenceClickListener(listener);
        checkboxPref16.setOnPreferenceClickListener(listener);
        checkboxPref17.setOnPreferenceClickListener(listener);
        checkboxPref18.setOnPreferenceClickListener(listener);
        checkboxPref19.setOnPreferenceClickListener(listener);
        checkboxPref20.setOnPreferenceClickListener(listener);
        checkboxPref21.setOnPreferenceClickListener(listener);
        checkboxPref22.setOnPreferenceClickListener(listener);
        checkboxPref23.setOnPreferenceClickListener(listener);
        checkboxPref24.setOnPreferenceClickListener(listener);
        checkboxPref25.setOnPreferenceClickListener(listener);
        checkboxPref26.setOnPreferenceClickListener(listener);
        checkboxPref27.setOnPreferenceClickListener(listener);
        checkboxPref28.setOnPreferenceClickListener(listener);
        checkboxPref29.setOnPreferenceClickListener(listener);
        checkboxPref30.setOnPreferenceClickListener(listener);
        checkboxPref31.setOnPreferenceClickListener(listener);
        checkboxPref32.setOnPreferenceClickListener(listener);
        checkboxPref33.setOnPreferenceClickListener(listener);
        checkboxPref34.setOnPreferenceClickListener(listener);
        checkboxPref35.setOnPreferenceClickListener(listener);
        checkboxPref36.setOnPreferenceClickListener(listener);
        checkboxPref37.setOnPreferenceClickListener(listener);
        checkboxPref38.setOnPreferenceClickListener(listener);
        checkboxPref39.setOnPreferenceClickListener(listener);
        checkboxPref40.setOnPreferenceClickListener(listener);
        checkboxPref41.setOnPreferenceClickListener(listener);
        checkboxPref42.setOnPreferenceClickListener(listener);
    }

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

        if (sharedPreferences.getBoolean(key, true)){
            // set Permissions
            //showNotificationAlert();
            //Code to get if my checkbox is pressed or not

            //openi.postPermissions();
        } else{
            //showNotificationAlert();
            // remove permisisons
        }

        /*if (key.equals(KEY_LOCATION_PROVIDER)) {
            Preference locationProviderPreference = findPreference(KEY_LOCATION_PROVIDER);
            locationProviderPreference.setSummary(sharedPreferences.getString(key, ""));
        }*/
    }
}
