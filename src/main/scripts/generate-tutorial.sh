#!/bin/bash
classes_dir=$1
build_dir=$2
scripts_dir=$3
tutorial_dir=$4
project_dir=$5
filename=$6
version=$7

rm -Rf $build_dir
mkdir -p $build_dir
cp $tutorial_dir/* $build_dir

# First run of gpp
# *.tmp generation

for file in $build_dir/*.gpp.md
do
	gpp -x -I$scripts_dir --include macro_code.gpp $file > $build_dir/`basename "${file%%.*}".gpp1`
done

# Replace \" by " in *.gpp1 files
for file in $build_dir/*.gpp1
do
	sed 's/\\"/"/g' $file > "${file%%.*}".gpp2
done

# Java source file generation
for file in $build_dir/*.tmp
do
	echo -e "import java.io.IOException;\n import org.thegalactic.dgraph.*;\n import org.thegalactic.lattice.*;\n import org.thegalactic.context.*;\n import org.thegalactic.util.*;\n public class" > "${file%%.*}".java
	echo -e `basename "${file%%.*}"` >> "${file%%.*}".java
	echo -e "{\n public static void main(String[] args) throws IOException {" >> "${file%%.*}".java
	cat $file >> "${file%%.*}".java
	echo -e "} \n } \n" >> "${file%%.*}".java
done

# Compiling java source files
for file in $build_dir/*.java
do
	javac -classpath $classes_dir $file
done

# Moving to directory
cd $build_dir

for file in *.class
do
	java -classpath "$classes_dir:." `basename ${file%%.*}`
done

# second run of gpp
# .md generation
for file in *.gpp2
do
	gpp -x -I$scripts_dir --include macro_include.gpp $file > `basename "${file%%.*}".md`
done

# Generate images with dot
for file in *.dot
do
	dot -Tpng $file -o ${file%%.*}.png
	# And resizing them
	convert ${file%%.*}.png -resize 50% ${file%%.*}.png
done

# Generate title
echo "% Java-lattices "$version > title
echo "% Tutorial" >> title
echo "% "`LC_TIME=en_US date -u` >> title

# Generate pdf
pandoc --variable=geometry:paper=a4paper,margin=1in `cat list` -o $filename

