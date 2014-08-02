#!/bin/sh
cd swagger-codegen

rm -fr generated-code/android-java

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true eu.openict.codegen.AndroidOPENiApiCodegen http://$1/api-spec/v1/api_framework

cd generated-code/android-java/

mvn package

cp target/openi-android-client-1.0.0.jar ../../../openi-graph-api-android-sdk-1.0.0.jar

