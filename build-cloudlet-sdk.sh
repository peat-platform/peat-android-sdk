#!/bin/sh
cd swagger-codegen

rm -fr generated-code/android-java

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true eu.openiict.codegen.AndroidOPENiApiCodegen http://$1/api-spec/v1/cloudlet

cd generated-code/android-java/

cp -r ../../../openi-custom-code/src/main/java src/main/

mvn package

cp target/openi-android-client-1.0.0.jar ../../../openi-cloudlet-android-sdk-1.0.0.jar


