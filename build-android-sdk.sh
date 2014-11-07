#!/bin/sh
cd swagger-codegen

rm -fr generated-code/android-java

scala -cp  target/scala-2.10/swagger-codegen.jar -DskipErrors=true eu.openiict.codegen.AndroidOPENiApiCodegen http://$1/api-spec/v1

sed -i 's/import eu.openiict.client.model.Datetime;//' generated-code/android-java/src/main/java/eu/openiict/client/model/*
sed -i 's/import eu.openiict.client.model.Integer;//' generated-code/android-java/src/main/java/eu/openiict/client/model/*
sed -i 's/import eu.openiict.client.model.Object;//' generated-code/android-java/src/main/java/eu/openiict/client/api/*
sed -i 's/import eu.openiict.client.model.Related;//' generated-code/android-java/src/main/java/eu/openiict/client/model/*
sed -i 's/related/Object/' generated-code/android-java/src/main/java/eu/openiict/client/model/*
sed -i 's/Related/Object/' generated-code/android-java/src/main/java/eu/openiict/client/model/*
sed -i 's/datetime/String/' generated-code/android-java/src/main/java/eu/openiict/client/model/*

cd generated-code/android-java/

cp -r ../../../openi-custom-code/src/main/java src/main/cd

mvn package

cp target/openi-android-client-1.0.0.jar ../../../openi-android-sdk-1.0.0.jar
