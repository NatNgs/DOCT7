#!/bin/bash


find . -name "Result.html" -type f -delete
rm -rf TempResult
rm -rf MutatedSrc
mkdir TempResult
mkdir MutatedSrc



cd MutationGenerator
echo  -n "Build mutation generator.."
mvn clean compile assembly:single > /dev/null 2> /dev/null
echo ".Done"

cd target

jar=$(find -name "mutationGenerator*.jar")

java -jar $jar ../../OriginalSrc ../../MutatedSrc

cd ../..

fichiers=`ls ./MutatedSrc/`
i=1;
for fichier in $fichiers
do
	echo -n "Build all $fichier .."
	cd "./MutatedSrc/$fichier/"
	# bash build_all.sh &> /dev/null 
	cd ../..
	cp -Rf "./MutatedSrc/$fichier/j2e/target/surefire-reports/" ./TempResult/
	mkdir ./TempResult/surefire-reports-$i/
	mv -f ./TempResult/surefire-reports/* ./TempResult/surefire-reports-$i/
	i=$((i + 1))
	echo ".Done"
done


rm -rf ./TempResult/surefire-reports
find ./TempResult -name "*.txt" -type f -delete

dossier="$(pwd)/TempResult/"

cd "./XmlsCompiler/out/artifacts/XmlsCompiler_jar/"
java -jar XmlsCompiler.jar "$dossier"

mv Result.html ../../../../

