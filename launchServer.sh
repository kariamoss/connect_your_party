#!/usr/bin/env bash

echo "Generate JavaDoc"
mkdir javadoc
javadoc -protected -splitindex -encoding UTF-8 -docencoding utf-8 -charset utf-8 -d javadoc -sourcepath server/serviceInterface/src/main/java -subpackages connectYourParty

cd server/back
mvn install tomee:run