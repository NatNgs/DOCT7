#!/bin/bash

clear
racine=$(pwd)

echo "################################"
echo -e "Run mutation testing framework\n"


find . -name "Result.html" -type f -delete
rm -rf MutatedSrc/*
rm -rf Reports/*
rm -rf MutatedSrc/*
mkdir TempResult 2> /dev/null
mkdir MutatedSrc 2> /dev/null
mkdir Reports	 2> /dev/null

echo -n "Install mutation generator..."
cd MutationGenerator
mvn clean install > "$racine/Reports/Mutagen mvn install.txt"
cd ../MutationApply
bash build.sh
cd ..
echo "Done"

processors=$(cat processors)

cd ./OriginalSrc/
projets=$(ls)


for projet in $projets
do
	if [ ! -d "$projet" ]; then
		continue
	fi  

	echo "Apply spoon on $projet..."
	cd $projet
	mvn clean --quiet
	cd ..
	i=1;
	for processor in $processors
	do
		uname="$projet-$i"
		cp -Rf "$projet" "$racine/MutatedSrc/$uname"
		cd $racine/MutationApply	
		java -jar MutationApply.jar $racine/MutatedSrc/$uname $processor
		echo "Done spoon on $projet"
		cd  $racine/MutatedSrc/$uname
		bash $racine/execproject.sh $racine $uname &
		cd $racine/OriginalSrc/
		i=$((i + 1))
	done
	
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
#rm -rf TempResult &

echo "Done"
echo "Report : Result.html"
echo "################################"
