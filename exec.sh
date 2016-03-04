#!/bin/bash

clear
racine=$(pwd)

echo "#############"
echo -e "Run mutation testing framework\n"


find . -name "Result.html" -type f -delete
rm -rf TempResult
rm -rf MutatedSrc
mkdir TempResult
mkdir MutatedSrc

echo -n "Install mutation generator..."
cd MutationGenerator
mvn clean --quiet
mvn install --quiet 
cd ..
echo "Done"


cd ./OriginalSrc/
projets=$(ls)

i=1;
for projet in $projets
do
	if [ ! -d "$projet" ]; then
		continue
	fi  
	
	echo -n "Apply spoon on $projet..."
	cd $projet
	mvn clean --quiet
	cd ..
	rm -rf ../MutatedSrc/$projet
	cp -Rf $projet ../MutatedSrc/
	# TODO :
	# Add processor
	echo "Done"
	
	cd ../MutatedSrc/$projet

		
	echo -n "Build project $projet..."
	mvn package  --quiet
	echo "Done"
	echo -n "Run tests for $projet..."
	mvn test  --quiet	
	tests=$(find -name surefire-reports)
	
	for t in $tests
	do
		target="../../TempResult/surefire-reports-$i/"
		mkdir $target
		cp -Rf $t ../surefire-reports/ $target
		i=$((i + 1))
	done
	echo "Done"
	cd $racine/OriginalSrc/
done

cd $racine


echo -n "Parse XML and generate report..."

find ./TempResult -name "*.txt" -type f -delete

dossier="$racine/TempResult/"

cd "./XmlsCompiler/out/artifacts/XmlsCompiler_jar/"
java -jar XmlsCompiler.jar "$dossier"

mv Result.html ../../../../

cd $racine
rm -rf TempResult

echo "Done"
echo "Report : Result.html"
echo "#############"
