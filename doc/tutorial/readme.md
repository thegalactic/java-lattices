Tutorial writer guide
=====================

Introduction
------------

The .md files are automatically generated with the `generate_tutorial.sh` script which requires the generic preprocessor `gpp` and the two macro files `macro_code.gpp` and `macro_include.gpp`.

The final .pdf tutorial is generated from .md files with pandoc.

The .gpp files
--------------

.gpp files are essentially .md files with two improvments :

+ Code function
+ Include function

## Code

You can insert Java source code that will be ran by the script `generate_tutorial`.

Syntax is the following :

    ~~~Code(filename.tmp,
    Java Source Code
    )~~~

WARNING : if `Java Source Code` contains double quotes `"`, you MUST replace them by `\\\"`

The `Java Source Code` is embedded in a .java file in the following way :

    import java.io.IOException;
    import dgraph.*;
    import lattice.*;
    public class `filename` {
        public static void main(String[] args) {
        `Java Source Code`
	}
    }

This file is compiled and ran.

A .png image is automatically generated from produced .dot files.

All these files are removed at the end of `generate_tutorial` script, after the second run of gpp which uses `Include` function. So that you should include all the content you want to keep in your .gpp file with the next function.

## Include

If the excecution of the previous code produces some .dot or .txt files, they can be included in the final .md file by

    Include(filename.ext)

Each line of `filename.ext` will have four blanc spaces added before inclusion.

Remember that you can include .png image with standard .md format file.
