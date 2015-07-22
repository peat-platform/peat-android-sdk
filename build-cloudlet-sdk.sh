#!/bin/sh

rm -fr swagger-codegen/generated-code/android-java
rm -fr peat_android_project/peat-client-lib/libs/*
rm -fr peat_android_project/peat-client-lib/src/main/java/org/peatplatform/client/api
rm -fr peat_android_project/peat-client-lib/src/main/java/org/peatplatform/client/common
rm -fr peat_android_project/peat-client-lib/src/main/java/org/peatplatform/client/model
rm -fr peat_android_project/peat-client-lib/src/main/java/org/peatplatform/client/utils

cd swagger-codegen

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true org.peatplatform.codegen.AndroidPEATApiCodegen http://$1/api-spec/v1/cloudlet

cd generated-code/android-java/

cp -r ../../../peat-custom-code/src/main/java src/main/

mvn package

mvn dependency:copy-dependencies

cp target/peat-android-client-1.0.0.jar ../../../peat-cloudlet-android-sdk-1.0.0.jar



