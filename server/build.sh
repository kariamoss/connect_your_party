#!/usr/bin/env bash

files="parent serviceInterface spotifyService dropboxService cotyPhotoService back"

for i in ${files}
do
    cd ${i}
    mvn clean install
    cd ..
done