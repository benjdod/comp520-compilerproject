#!/bin/bash

for filepath in ./src/miniJava/AbstractSyntaxTrees/*; 
do
	# echo "${fname%.*}"
	FNAME="${filepath##*/}"
	SEARCH="${FNAME%.*}" 
	# echo "${FNAME%.*}"
	printf "%s\t" $SEARCH
	grep -c $SEARCH ./src/miniJava/SyntacticAnalyzer/Parser.java 
done
