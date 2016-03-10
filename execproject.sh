#!/bin/bash

projet=$2
racine=$1

	
echo -e "Build project $projet...\n"
mvn package > "$racine/Reports/$projet mvn package.txt"
cp -Rf ./target/generated-sources/spoon/* ./src/main/java/
mvn package >> "$racine/Reports/$projet mvn package.txt"
echo "Done build project $projet"

echo -e "Run tests for $projet...\n"
mvn test > "$racine/Reports/$projet mvn test.txt"
tests=$(find -name surefire-reports)

for t in $tests
do
	target="../../TempResult/surefire-reports-$projet"
	mkdir $target
	pwd
	ls
	cp -Rf $t/* $target
	cp mutation $target
done
echo -e "Done tests $projet \n"
cd $racine/OriginalSrc/
