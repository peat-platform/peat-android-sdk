#!/bin/sh
cd swagger-codegen

rm -fr generated-code/android-java
rm -fr openi_android_project/openi-client-lib/libs/*
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/api
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/common
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/model
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/utils

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true eu.openiict.codegen.AndroidOPENiApiCodegen http://$1/api-spec/v1

cd generated-code/android-java/

cp -r ../../../openi-custom-code/src/main/java src/main/

mvn package

mvn dependency:copy-dependencies

cp target/openi-android-client-1.0.0.jar ../../../openi-android-sdk-1.0.0.jar

cd ~/repos/openi_android_sdk/openi_android_project

cp -r ../swagger-codegen/generated-code/android-java/src/main/java/eu openi-client-lib/src/main/java

mkdir openi-client-lib/libs

cp -r ../swagger-codegen/generated-code/android-java/target/dependency/* openi-client-lib/libs

bash gradlew assemble

cp openi-client-lib/build/outputs/aar/openi-client-lib.aar ..
