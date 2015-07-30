#!/bin/sh
cd swagger-codegen

rm -fr generated-code/android-java

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true org.peatplatform.codegen.AndroidPEATApiCodegen http://$1/api-spec/v1/simple_auth

cd generated-code/android-java/

mvn package

cp target/peat-android-client-1.0.0.jar ../../../peat-auth-android-sdk-1.0.0.jar


