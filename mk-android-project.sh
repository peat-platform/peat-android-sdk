#!/bin/sh


rm -fr swagger-codegen/generated-code/android-java
rm -fr peat_android_project/openi-client-lib/libs/*
rm -fr peat_android_project/openi-client-lib/src/main/java/org/peatplatform/client/api
rm -fr peat_android_project/openi-client-lib/src/main/java/org/peatplatform/client/common
rm -fr peat_android_project/openi-client-lib/src/main/java/org/peatplatform/client/model
rm -fr peat_android_project/openi-client-lib/src/main/java/org/peatplatform/client/utils

cd swagger-codegen

scala -cp target/scala-2.11/swagger-codegen.jar -DskipErrors=true org.peatplatform.codegen.AndroidOPENiApiCodegen http://$1/api-spec/v1

#scala -cp target/scala-2.11/swagger-codegen.jar -DskipErrors=true org.peatplatform.codegen.AndroidOPENiApiCodegen http://dev.openi-ict.eu/api-spec/v1
#scala -cp target/scala-2.11/swagger-codegen.jar -DskipErrors=true org.peatplatform.codegen.AndroidOPENiApiCodegen http://dev.openi-ict.eu/api-spec/v1/cloudlet

sed -i 's/import org.peatplatform.client.model.Datetime;//' generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/import org.peatplatform.client.model.Integer;//' generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/import org.peatplatform.client.model.Object;//' generated-code/android-java/src/main/java/org/peatplatform/client/api/*
sed -i 's/import org.peatplatform.client.model.Related;/import org.peatplatform.client.model.Context;/' generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/JSON/Map/g'        generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/Integer id, String box, String classification, String background, String format, String color, String text, String font, String background_color, String text_copy, String Authorization, String format/Integer id, String box, String classification, String background, String format, String color, String text, String font, String background_color, String text_copy, String Authorization/'
sed -i 's/related/Context/' generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/Related/Context/' generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/datetime/String/' generated-code/android-java/src/main/java/org/peatplatform/client/model/*


cd generated-code/android-java/

cp -r ../../../peat-custom-code/src/main/java src/main/

mvn package

mvn dependency:copy-dependencies

cp target/openi-android-client-1.0.0.jar ../../../openi-android-sdk-1.0.0.jar

cd ~/repos/openi_android_sdk/peat_android_project

cp -r ../swagger-codegen/generated-code/android-java/src/main/java/org openi-client-lib/src/main/java

mkdir -p openi-client-lib/libs

cp -r ../swagger-codegen/generated-code/android-java/target/dependency/* openi-client-lib/libs


