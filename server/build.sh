#!/usr/bin/env bash

files="parent serviceInterface spotifyService dropboxService cotyPhotoService "

for i in ${files}
do
    cd ${i}
    mvn clean install
    cd ..
done

cp dropboxService/target/classes/connectYourParty/DropboxService.class back/src/test/resources/DropboxService.class

cd back
mvn clean install
cd ..