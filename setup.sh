#!/bin/sh
sudo apt-get install -Y openjdk-7-jdk
sudo apt-get install -Y maven=3.0.4-2

sudo apt-get remove scala-library scala
wget www.scala-lang.org/files/archive/scala-2.10.3.deb
sudo dpkg -i scala-2.10.3.deb
sudo apt-get update
sudo apt-get install scala
sudo apt-get -f install
sudo apt-get install scala

wget http://scalasbt.artifactoryonline.com/scalasbt/sbt-native-packages/org/scala-sbt/sbt//0.12.3/sbt.deb
sudo dpkg -i sbt.deb
sudo apt-get update
sudo apt-get install sbt

rm -fr sbt.deb  scala-2.10.3.deb

git clone https://github.com/wordnik/swagger-codegen.git

cd swagger-codegen

./sbt assembly

mkdir -p generated-code/android-java/src/main/java