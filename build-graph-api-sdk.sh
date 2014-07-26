#!/bin/sh
cd swagger-codegen

rm -fr generated-code/android-java

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true com.wordnik.swagger.codegen.BasicAndroidJavaClient http://$1/api-spec/v1/api_framework

cd generated-code/android-java/

mv src/main/java/pom.xml .

mvn package

cp target/android-client-1.0.0.jar ../../../openi-framework-android-sdk-1.0.0.jar

