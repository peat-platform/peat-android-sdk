#!/bin/sh
cd swagger-codegen

rm -fr generated-code/*

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true com.wordnik.swagger.codegen.BasicAndroidJavaClient http://dev.openi-ict.eu/api-spec/v1/

cd generated-code/android-java/

mv src/main/java/pom.xml .

mvn package

cp target/android-client-1.0.0.jar ../../../openi-android-sdk-1.0.0.jar