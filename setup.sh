#!/bin/sh
sudo apt-get install -y openjdk-7-jdk
sudo apt-get install -y maven=3.0.5-1

sudo apt-get remove -y scala-library scala
wget www.scala-lang.org/files/archive/scala-2.11.6.deb
sudo dpkg -i scala-2.11.6.deb
sudo apt-get update -y
sudo apt-get install -y scala
sudo apt-get -f install
sudo apt-get install scala

wget http://scalasbt.artifactoryonline.com/scalasbt/sbt-native-packages/org/scala-sbt/sbt//0.13.1/sbt.deb
sudo dpkg -i sbt.deb
sudo apt-get update -y
sudo apt-get install sbt -y

rm -fr sbt.deb  scala-2.10.3.deb

wget https://github.com/swagger-api/swagger-codegen/archive/2.0.17.tar.gz

tar -xvf 2.0.17.tar.gz

rm -f 2.0.17.tar.gz
mv swagger-codegen-2.0.17 swagger-codegen

cp -r openi-custom-code/src/main/scala/*     swagger-codegen/src/main/scala/
cp -r openi-custom-code/src/main/resources/* swagger-codegen/src/main/resources/

cd swagger-codegen

./sbt assembly

mv target/scala-2.11/swagger-codegen*.jar target/scala-2.11/swagger-codegen.jar

mkdir -p generated-code/android-java/src/main/java
