#!/bin/sh
cd swagger-codegen

rm -fr generated-code/android-java

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true org.peatplatform.codegen.AndroidOPENiApiCodegen http://$1/api-spec/v1/api_framework

sed -i 's/import org.peatplatform.client.model.Datetime;//' generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/import org.peatplatform.client.model.Integer;//' generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/import org.peatplatform.client.model.Object;//' generated-code/android-java/src/main/java/org/peatplatform/client/api/*
sed -i 's/import org.peatplatform.client.model.Related;/import org.peatplatform.client.model.Context;/' generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/related/Context/' generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/Related/Context/' generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/datetime/String/' generated-code/android-java/src/main/java/org/peatplatform/client/model/*
sed -i 's/JSON/Map/g'       generated-code/android-java/src/main/java/org/peatplatform/client/model/*

cd generated-code/android-java/

mvn package

cp target/openi-android-client-1.0.0.jar ../../../openi-graph-api-android-sdk-1.0.0.jar

