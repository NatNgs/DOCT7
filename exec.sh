#!/bin/bash

clear
racine=$(pwd)

echo "################################"
echo "Run mutation testing framework\n"


find . -name "Result.html" -type f -delete
#rm -rf MutatedSrc/
#rm -rf Reports/
mkdir TempResult 2> /dev/null
mkdir MutatedSrc 2> /dev/null
mkdir Reports	 2> /dev/null

echo -n "Install mutation generator..."
cd MutationGenerator
mvn clean install > "$racine/Reports/Mutagen mvn install.txt"
cd ..
echo "Done"


cd ./OriginalSrc/
projets=$(ls)

i=1;
for projet in $projets
do
	sh $racine/execproject.sh $racine $projet &
done

wait

cd $racine


echo -n "Parse XML and generate report..."

find ./TempResult -name "*.txt" -type f -delete &

dossier="$racine/TempResult/"

cd "./XmlsCompiler/out/artifacts/XmlsCompiler_jar/"
java -jar XmlsCompiler.jar "$dossier"

mv Result.html ../../../../ &

cd $racine
rm -rf TempResult &

echo "Done"
echo "Report : Result.html"
echo "################################"
