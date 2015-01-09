#!/bin/sh


rm -fr swagger-codegen/generated-code/android-java
rm -fr openi_android_project/openi-client-lib/libs/*
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/api
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/common
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/model
rm -fr openi_android_project/openi-client-lib/src/main/java/eu/openiict/client/utils

cd swagger-codegen

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true eu.openiict.codegen.AndroidOPENiApiCodegen http://$1/api-spec/v1

sed -i 's/import eu.openiict.client.model.Datetime;//' generated-code/android-java/src/main/java/eu/openiict/client/model/*
sed -i 's/import eu.openiict.client.model.Integer;//' generated-code/android-java/src/main/java/eu/openiict/client/model/*
sed -i 's/import eu.openiict.client.model.Object;//' generated-code/android-java/src/main/java/eu/openiict/client/api/*
sed -i 's/import eu.openiict.client.model.Related;/import eu.openiict.client.model.Context;/' generated-code/android-java/src/main/java/eu/openiict/client/model/*
sed -i 's/JSON/Map/g'        generated-code/android-java/src/main/java/eu/openiict/client/model/*
sed -i 's/Integer id, String box, String classification, String background, String format, String color, String text, String font, String background_color, String text_copy, String Authorization, String format/Integer id, String box, String classification, String background, String format, String color, String text, String font, String background_color, String text_copy, String Authorization/'
sed -i 's/related/Context/' generated-code/android-java/src/main/java/eu/openiict/client/model/*
sed -i 's/Related/Context/' generated-code/android-java/src/main/java/eu/openiict/client/model/*
sed -i 's/datetime/String/' generated-code/android-java/src/main/java/eu/openiict/client/model/*


cd generated-code/android-java/

cp -r ../../../openi-custom-code/src/main/java src/main/

mvn package

mvn dependency:copy-dependencies

cp target/openi-android-client-1.0.0.jar ../../../openi-android-sdk-1.0.0.jar

cd ~/repos/openi_android_sdk/openi_android_project

cp -r ../swagger-codegen/generated-code/android-java/src/main/java/eu openi-client-lib/src/main/java

mkdir -p openi-client-lib/libs

cp -r ../swagger-codegen/generated-code/android-java/target/dependency/* openi-client-lib/libs

bash gradlew assemble

cp openi-client-lib/build/outputs/aar/openi-client-lib.aar ..
