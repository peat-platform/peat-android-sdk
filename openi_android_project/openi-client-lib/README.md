# Android SDK Usage Instructions

###-Please include the openi aar file in your android project.

###-Initiate the SDK in the onCreate and onResume with:


    OPENiAsync.init("api_key", "secret_key", context, "permissions_json_file_name");
    OPENiAsync openi = OPENiAsync.instance();


* permissions file should be in the assets folder (src/main/assets)

###-Connect/check openi application user with:


    IAuthTokenResponse authToken = new IAuthTokenResponse() {
                    @Override
                    public void onSuccess(String authToken) {
                        // user's auth token is available here
                        Log.d("OPENi", "User logged in");
                    }
    
                    @Override
                    public void onAppPermsDenied(String error){
                        Log.d("OPENi", "Permissions Denied");
                    }
    
                    @Override
                    public void onFailure(String error) {
                        Log.d("OPENi", "Failed");
                    }
                };
                openi.openiConnect(authToken);


* After a successful login all OPENi API calls have the JWT available in order to be used in the call.

###Execute an API call 


    openi.getCloudletID(new ICloudletIdResponse() {
                    @Override
                    public void onSuccess(String cloudletID) {
                        Log.d("OPENi", "Got cloudletid: "+cloudletID);
                    }
    
                    @Override
                    public void onFailure() {
                        Log.d("OPENi", "Failed");
                    }
                });


###Execute a custom API call


    openi.execOpeniApiCall(new IOPENiAPiCall() {
                    @Override
                    public eu.openiict.client.model.ListView doProcess(String authToken) {
                       ApplicationApi app = new ApplicationApi();
                        try {
                            return app.applicationList(0, 0, authToken);
                        } catch (ApiException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
    
                    @Override
                    public void onSuccess(Object object) {
                         Log.d("OPENi", object.toString());
                    }
    
                    @Override
                    public void onFailure() {
                         Log.d("OPENi", "Failed");
                    }
                });

            
* You can execute any OPENi API call in "doProccess". JWT is also available there.

* In "onSuccess" the result of the API call is available (any object you returned - you may need to cast it to the appropriate object type)

###Logout a user


    openi.logout();


###Start User Permissions Activity


    startActivity(new Intent(this, PermissionsActivity.class));


* Add permissions activity to Manifest:


    <activity android:name="eu.openiict.client.permissions.PermissionsActivity" android:label="Permissions" />
