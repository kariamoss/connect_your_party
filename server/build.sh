#!/usr/bin/env bash

cd serviceInterface
mvn clean install
cd ..

cd dropboxService
mvn clean install
cd ..


cd back
mvn clean install
cd ..