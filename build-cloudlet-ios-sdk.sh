#!/bin/sh

#rm -fr swagger-codegen/generated-code/android-java

cd swagger-codegen

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true com.wordnik.swagger.codegen.BasicObjcGenerator http://$1/api-spec/v1

#cd generated-code/android-java/



