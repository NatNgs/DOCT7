#!/bin/bash

echo -n "Install mutation generator..."
cd MutationGenerator
mvn clean install >> "../Reports/Mutagen mvn install.txt"
cd ../MutationApply
bash build.sh
cd ..
echo "Done"
