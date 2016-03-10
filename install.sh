#!/bin/bash

echo -n "Install mutation generator..."
mkdir Reports 2> /dev/null
cd MutationGenerator
mvn clean install >> "../Reports/Mutagen mvn install.txt"
cd ../MutationApply
bash build.sh
cd ..
echo "Done"
