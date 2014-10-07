#!/bin/bash
# TO BE IMPROVED !
build_dir='build/src'
tutorial_dir='doc/tutorial'
back_dir='../..'

# Moving to directory
cd $tutorial_dir 

# First run of gpp
# *.tmp generation
gpp -x --include macro_code.gpp tutorial_lattice.gpp > tutorial_lattice.gpp1
gpp -x --include macro_code.gpp tutorial_conceptlattice.gpp > tutorial_conceptlattice.gpp1
# Replace \" by " in *.gpp1 files
for file in *.gpp1
do
	sed 's/\\"/"/g' $file > "${file%%.*}".gpp2
done

# Java source file generation
for file in *.tmp
do
	echo -e "import java.io.IOException;\n import fr.kbertet.dgraph.*; \n import fr.kbertet.lattice.*; \n public class" > "${file%%.*}".java
	echo -e "${file%%.*}" >> "${file%%.*}".java
	echo -e "{\n public static void main(String[] args) throws IOException {" >> "${file%%.*}".java
	cat $file >> "${file%%.*}".java
	echo -e "} \n } \n" >> "${file%%.*}".java
done

# Compiling java source files
for file in *.java
do
	javac -classpath $back_dir/$build_dir $file
done

# Run java class files
cp *.class $back_dir/$build_dir
cp tealady.cxt $back_dir/$build_dir
cd $back_dir/$build_dir
for file in *.class
do
	java ${file%%.*}
done

cp *.dot $back_dir/$tutorial_dir/
cp *.txt $back_dir/$tutorial_dir/
rm *.class
rm *.dot
rm *.txt
cd $back_dir/$tutorial_dir

# Generate images with dot
for file in *.dot
do
	dot -Tpng $file -o ${file%%.*}.png
	# And resizing them
	convert ${file%%.*}.png -resize 50% ${file%%.*}.png
done
cp *.png $back_dir/

# second run of gpp
# .md generation
gpp -x --include macro_include.gpp tutorial_lattice.gpp2 > tutorial_lattice.md
gpp -x --include macro_include.gpp tutorial_conceptlattice.gpp2 > tutorial_conceptlattice.md

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
cd $back_dir

