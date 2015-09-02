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

echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 642AC823
sudo apt-get update -y
sudo apt-get install -y sbt

wget -P ~/.sbt/launchers/0.13.0/ https://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/0.13.0/sbt-launch.jar

rm -fr scala-2.10.3.deb

wget https://github.com/swagger-api/swagger-codegen/archive/2.0.17.tar.gz

tar -xvf 2.0.17.tar.gz

rm -f 2.0.17.tar.gz
mv swagger-codegen-2.0.17 swagger-codegen

cp -r peat-custom-code/src/main/scala/*     swagger-codegen/src/main/scala/
cp -r peat-custom-code/src/main/resources/* swagger-codegen/src/main/resources/

cd swagger-codegen

rm -f target/scala-2.11/swagger-codegen.jar

./sbt assembly

mv target/scala-2.11/swagger-codegen*.jar target/scala-2.11/swagger-codegen.jar

mkdir -p generated-code/android-java/src/main/java
