# Android SDK Usage Instructions

###-Please include the peat aar file in your android project.

###-Initiate the SDK in the onCreate and onResume with:


    PEATAsync.init("api_key", "secret_key", context, "permissions_json_file_name");
    PEATAsync peat = PEATAsync.instance();


* permissions file should be in the assets folder (src/main/assets)

###-Connect/check peat application user with:


    IAuthTokenResponse authToken = new IAuthTokenResponse() {
                    @Override
                    public void onSuccess(String authToken) {
                        // user's auth token is available here
                        Log.d("PEAT", "User logged in");
                    }
    
                    @Override
                    public void onAppPermsDenied(String error){
                        Log.d("PEAT", "Permissions Denied");
                    }
    
                    @Override
                    public void onFailure(String error) {
                        Log.d("PEAT", "Failed");
                    }
                };
                peat.peatConnect(authToken);


* After a successful login all PEAT API calls have the JWT available in order to be used in the call.

###Execute an API call 


    peat.getCloudletID(new ICloudletIdResponse() {
                    @Override
                    public void onSuccess(String cloudletID) {
                        Log.d("PEAT", "Got cloudletid: "+cloudletID);
                    }
    
                    @Override
                    public void onFailure() {
                        Log.d("PEAT", "Failed");
                    }
                });


###Execute a custom API call


    peat.execPeatApiCall(new IPEATAPiCall() {
                    @Override
                    public org.peatplatform.client.model.ListView doProcess(String authToken) {
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
                         Log.d("PEAT", object.toString());
                    }
    
                    @Override
                    public void onFailure() {
                         Log.d("PEAT", "Failed");
                    }
                });

            
* You can execute any PEAT API call in "doProccess". JWT is also available there.

* In "onSuccess" the result of the API call is available (any object you returned - you may need to cast it to the appropriate object type)

###Logout a user


    peat.logout();


###Start User Permissions Activity


    startActivity(new Intent(this, PermissionsActivity.class));


* Add permissions activity to Manifest:


    <activity android:name="org.peatplatform.client.permissions.PermissionsActivity" android:label="Permissions" />
