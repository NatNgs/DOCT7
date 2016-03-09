#!/bin/bash

projet=$2
racine=$1

	
echo "Build project $projet... "
mvn package > "$racine/Reports/$projet mvn package.txt"
echo "Done build project $projet"

echo "Run tests for $projet... "
mvn test > "$racine/Reports/$projet mvn test.txt"
tests=$(find -name surefire-reports)

for t in $tests
do
	target="../../TempResult/surefire-reports-$i/"
	mkdir $target
	cp -Rf $t ../surefire-reports/ $target
	i=$((i + 1))
done
echo "Done tests $projet"
cd $racine/OriginalSrc/
