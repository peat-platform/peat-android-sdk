To build the OPENi Android SDK execute the following steps

**Provision your system by installing java, scala, sdt, maven3, and swagger-codegen**

1) sh setup.sh

**Execute the build script (Ensure you have the latest version of the Mongrel2 module, the api_framework, and the cloudlet platform running on the host that the SDK is being built for.
This module will build the SDK for any instance of OPENi once it can access its REST endpoints. )**


Replace IP_ADDRESS/HOST with either the hostname or IP address of the server hosting the OPENi platform ege localhost or dev.openi-ict.eu.

2) sh build-android-sdk.sh IP_ADDRESS/HOST


The SDK (openi-android-sdk-1.0.0.jar) will be created in the root folder.

