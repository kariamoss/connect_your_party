#!/usr/bin/env bash

files="serviceInterface dropboxService cotyPhotoService back"

for i in ${files}
do
    cd ${i}
    mvn clean install deploy
    cd ..
done