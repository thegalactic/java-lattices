#!/bin/bash
# TO BE IMPROVED !
cd doc/tutorial
# first run of gpp
# *.tmp generation
gpp -x --include macro_code.gpp tutorial_lattice.gpp > tutorial_lattice.gpp1
# replace \" by " in *.gpp1 files
for file in *.gpp1
do
	sed 's/\\"/"/g' $file > "${file%%.*}".gpp2
done
# Java source file generation
for file in *.tmp
do
	echo -e "import java.io.IOException;\n import dgraph.*; \n import lattice.*; \n public class" > "${file%%.*}".java
	echo -e "${file%%.*}" >> "${file%%.*}".java
	echo -e "{\n public static void main(String[] args) {" >> "${file%%.*}".java
	cat $file >> "${file%%.*}".java
	echo -e "} \n } \n" >> "${file%%.*}".java
done
# Compiling java source files
for file in *.java
do
	javac -classpath ../../build/classes $file
done
# Run java class files
cp *.class ../../build/classes
cd ../../build/classes
for file in *.class
do
	java ${file%%.*}
done
cp *.dot ../../doc/tutorial
cp *.txt ../../doc/tutorial
rm *.class
rm *.dot
rm *.txt
cd ../../doc/tutorial

# Generate images with dot
for file in *.dot
do
	dot -Tpng $file -o ${file%%.*}.png
	# And resizing them
	convert ${file%%.*}.png -resize 50% ${file%%.*}.png
done
cp *.png ../..

# second run of gpp
# .md generation
gpp -x --include macro_include.gpp tutorial_lattice.gpp2 > tutorial_lattice.md

# MrProper is cleanning
rm *.tmp
rm *.dot
rm *.png
rm *.txt
rm *.java
rm *.class
rm *.gpp1
rm *.gpp2

# TO BE IMPROVED !
cd ../..

