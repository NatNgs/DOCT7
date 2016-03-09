#!/bin/bash

projet=$2
racine=$1

if [ ! -d "$projet" ]; then
	continue
fi  

echo "Apply spoon on $projet..."
cd $projet
mvn clean --quiet
cd ..
rm -rf ../MutatedSrc/$projet
cp -Rf $projet ../MutatedSrc/
# TODO :
# Add processor
echo "Done spoon on $projet"

cd ../MutatedSrc/$projet

	
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
