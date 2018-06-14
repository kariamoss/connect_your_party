#!/usr/bin/env bash
# Artifactory location
server=http://207.154.199.162:8081/artifactory
repo=ConnectRelease

# Maven artifact location
name=serviceInterface
artifact=ConnectYourParty/${name}
path=$server/$repo/$artifact
version=1.0-SNAPSHOT
build=`curl -s $path/$version/maven-metadata.xml | grep '<value>' | head -1 | sed "s/.*<value>\([^<]*\)<\/value>.*/\1/"`
jar=$name-$build.jar
url=$path/$version/$jar

# Download
echo $url