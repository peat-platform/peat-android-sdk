#!/bin/sh
cd swagger-codegen

rm -fr generated-code/android-java

scala -cp Target/scala-2.10/swagger-codegen.jar -DskipErrors=true com.wordnik.swagger.codegen.BasicAndroidJavaClient http://$1/api-spec/v1/api_framework

cd generated-code/android-java/

mv src/main/java/com/ src/main/java/eu
mv src/main/java/eu/wordnik/ src/main/java/eu/openi

mv src/main/java/pom.xml .

#Process the generated code so that it is Java Compliant

mvn package

cp target/android-client-1.0.0.jar ../../../openi-graph-api-android-sdk-1.0.0.jar

