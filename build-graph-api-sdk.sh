#!/bin/sh
cd swagger-codegen

rm -fr generated-code/android-java

scala -cp Target/scala-2.10/swagger-codegen.jar -DskipErrors=true com.wordnik.swagger.codegen.BasicAndroidJavaClient http://$1/api-spec/v1/api_framework

cd generated-code/android-java/

mv src/main/java/pom.xml .

#Process the generated code so that it is Java Compliant
cd src/main/java/com/wordnik/client/model
ls | awk '{system("mv " $0 " " toupper(substr($0,1,1)) substr($0,2))}'
cd ../../../../../../../
perl -pi -e 's/-//g' src/main/java/com/wordnik/client/api/VApi.java

mvn package

cp target/android-client-1.0.0.jar ../../../openi-graph-api-android-sdk-1.0.0.jar

