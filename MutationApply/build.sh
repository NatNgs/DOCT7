#!/bin/bash

mvn package --quiet -DskipTests
cd target
jar=$(find -name mutationApply*.jar)

mv $jar ../MutationApply.jar
cd ..

