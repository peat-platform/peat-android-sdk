#!/bin/sh
cd swagger-codegen

rm -fr generated-code/python3

scala -cp target/scala-2.10/swagger-codegen.jar -DskipErrors=true com.wordnik.swagger.codegen.BasicPython3Generator http://dev.openi-ict.eu/api-spec/v1/

