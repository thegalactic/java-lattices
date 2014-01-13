package lattice;

/*
 * BijectiveComponents.java
 *
 * last update on March 2010
 *
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.TreeSet;
import dgraph.DGraph;
/**
 * This class generates bijective components issued from lattice theory for a specified
 * closure system.
 *
 * Bijective components are:
 * closed set lattice or concept lattice, reduced lattice, reduced context, canonical direct basis, minimal generators
 * and canonical basis, dependance graph.
 *
 * A closure system is described by the abstract class `ClosureSystem`.
 * In this package, a closure system can be instancied by an implicational
 * system described by class `IS`) or a context described by
 * class `Context`).
 *
 * This class provides a constructor, and only two methods: the method `initialize`
 * generates all the bijective components of the specified closure system; and the method
 * `save` saves theses components in files.
 *
 * This class can be used as follows:
 *
 * ~~~Java
 * BijectiveComponents BC = new BijectiveComponents (initialClosureSystem);
 * BC.initialize();
 * BC.save(dirString,nameString);
 * ~~~
 *
 * Copyright: 2013 University of La Rochelle, France
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 *
 * @author Karell Bertet
 * @version 2013
 */
public class BijectiveComponents {

	/* ------------- FIELDS ------------------ */

	/** The initial closure system **/
    public ClosureSystem init;
    /** The closed set lattice of the closure system when closure system is an implicational system **/
    /** The concept lattice of the closure system when closure system is a context **/
    public ConceptLattice lattice = null;
    /** The reduced lattice **/
    public Lattice reducedLattice = null;
    /** The dependance graphe of the reduced lattice **/
    public DGraph dependanceGraph = null;
    /** The minimal generators of the reduced lattice **/
    public TreeSet<ComparableSet> minimalGenerators = null;
    /** The canonical direct basis of the reduced lattice **/
    public IS canonicalDirectBasis = null;
    /** The canonical basis of the reduced lattice **/
    public IS canonicalBasis = null;
    /** The table of the reduced lattice **/
    public Context table = null;

  	/** Constructs this component with the specified Closure System as initial closure system.
     **/
    public BijectiveComponents (ClosureSystem Init) {
        this.init = Init;
    }

    /** Generates all the bijective components included in this component
     * issued from the initial closure system `init`.
     *
     * The closed set lattice is generated when the closure system is an implicational system,
     * and obtained by
     *
     * ~~~Java
     * this.init.closedSetLattice(true);
     * ~~~
     *
     * The concept lattice is generated when the closure system is a context, and obtained by
     * by
     *
     * ~~~Java
     * this.init.conceptLattice(true);
     * ~~~
     *
     * The reduced lattice is obtained by
     *
     * ~~~Java
     * this.lattice.getIrreduciblesReduction();
     * ~~~
     *
     * The reduced table is obtained by
     *
     * ~~~Java
     * this.reducedLattice.getTable();
     * ~~~
     *
     * The dependance graph is obtained by
     *
     * ~~~Java
     * this.reducedLattice.getDependanceGraph();
     * ~~~
     *
     * Minimal generators are obtained by
     *
     * ~~~Java
     * this.reducedLattice.getMinimalGenerators();
     * ~~~
     *
     * The canonical direct basis is obtained by
     *
     * ~~~Java
     * this.reducedLattice.getCanonicalDirectBasis();
     * ~~~
     *
     * The canonical basis is obtained by
     *
     * ~~~Java
     * new IS(this.canonicalDirectBasis).makeCanonicalBasis();
     * ~~~
     *
     * @return time of computation
     */
    public long initialize () {
        long debut = new Date().getTime();        
        //if (this.init instanceof lattice.Context) 
        if (this.init instanceof Context) 
            this.lattice = ((Context)this.init).conceptLattice(true);
        else
            this.lattice = this.init.closedSetLattice(true);
        this.reducedLattice = this.lattice.getIrreduciblesReduction();
        this.table = this.lattice.getTable();
        this.dependanceGraph = this.lattice.getDependanceGraph();
        this.minimalGenerators = this.lattice.getMinimalGenerators();
        this.canonicalDirectBasis = this.lattice.getCanonicalDirectBasis();
        this.canonicalBasis = new IS(this.canonicalDirectBasis);
        this.canonicalBasis.makeCanonicalBasis();
        long fin = new Date().getTime();
        return fin-debut;
    }

   /** Saves all the bijective components included in this component in files
    * saved in the specified directory.
    * A global description is saved in file `name+"Readme.txt"`.
    *
    * The specified name is used to defined a name for each file.
    */
    public void save (String directory, String name) {
        try 	{
            // create the directory
            directory = directory+File.separator+name+"BijectiveComponents"+File.separator;
            File f = new File(directory);
            f.mkdir();
            directory += name;
			BufferedWriter fichier = new BufferedWriter(new FileWriter(directory+"Readme.txt"));            
            // saves the inital closure system
            String nameInit = directory+"InitialClosureSystem.txt";
            this.init.toFile (nameInit);
            fichier.write("-> Initial closure system saved in "+nameInit+": \n");
            fichier.write(this.init.toString()+"\n");
            // saves the closed set lattice
            String nameLattice = directory+"Lattice.dot";            
            this.lattice.toDot(nameLattice);
            fichier.write("-> Closed set or concept lattice saved in "+nameLattice+"\n");
            // saves the reduced lattice
            String nameReducedLattice = directory+"ReducedLattice.dot";
            this.reducedLattice.toDot(nameReducedLattice);
            fichier.write("-> Reduced lattice saved in "+nameReducedLattice+"\n");
            // saves the reduced table
            String nameTable = directory+"Table.txt";
            this.table.toFile(nameTable);
            fichier.write("-> Table of the reduced lattice saved in "+nameTable+"\n");
            fichier.write(this.table.toString()+"\n");
            // saves the canonical basis
            String nameCB = directory+"CanonicalBasis.txt";
            this.canonicalBasis.toFile(nameCB);
            fichier.write("-> Canonical basis saved in "+nameCB+": \n");
            fichier.write(this.canonicalBasis.toString()+"\n");
            // saves the canonical direct basis
            String nameCDB = directory+"CanonicalDirectBasis.txt";
            this.canonicalDirectBasis.toFile(nameCDB);
            fichier.write("-> Canonical direct basis of the reduced lattice saved in "+nameCDB+": \n");
            fichier.write(this.canonicalDirectBasis.toString()+"\n");
            // saves the dependance graph
            String nameODGraph = directory+"DependanceGraph.dot";
            this.dependanceGraph.toDot(nameODGraph);
			fichier.write("-> Dependance Graph  of the reduced lattice saved in "+nameODGraph+" \n");
            // saves the minimal generators
			fichier.write("-> Minimal generators  of the reduced lattice are "+this.minimalGenerators+"\n");
            fichier.close();

    	}
    	catch (Exception e) { e.printStackTrace(); }
    }

	/* Returns the informativ generic basis
	public IS getApproximativBasis () {
		IS IGB = new IS(this.canonicalDirectBasis);
        IS tmp = new IS(this.canonicalDirectBasis);
		for (Rule r : tmp.getRules()) {
			TreeSet premise = new TreeSet(r.getPremise());
            Concept c = new Concept(this.closure(premise),false);
			for (TreeSet conclusion : this.immediateSuccessors(c)) {
                TreeSet concl = new TreeSet(conclusion);
				conclusion.removeAll(premise);
				IGB.addRule(new Rule(premise,concl));
			}
		}
		return IGB;
	}
**/
}// end of BijectiveComponents
