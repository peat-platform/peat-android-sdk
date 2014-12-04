#!/bin/sh

rm -fr swagger-codegen/generated-code/android-java
rm -fr openi_android_project/openi-client-lib/libs/*
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/api
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/common
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/model
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/utils

cd swagger-codegen

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true eu.openiict.codegen.AndroidOPENiApiCodegen http://$1/api-spec/v1/cloudlet

cd generated-code/android-java/

cp -r ../../../openi-custom-code/src/main/java src/main/

mvn package

mvn dependency:copy-dependencies

cp target/openi-android-client-1.0.0.jar ../../../openi-cloudlet-android-sdk-1.0.0.jar



