#!/bin/sh
cd swagger-codegen

rm -fr generated-code/python3

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true com.wordnik.swagger.codegen.BasicPython3Generator https://$1/api-spec/v1/cloudlet

