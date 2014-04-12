package lattice;

/*
 * BijectiveComponents.java
 *
 * last update on February 2014
 *
 * Copyright: 2013 University of La Rochelle, France
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 *
 * @author Karell Bertet
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
 * and canonical basis, dependency graph.
 *
 * A closure system is described by the abstract class {@link ClosureSystem}.
 * In this package, a closure system can be instancied by an implicational
 * system described by class {@link ImplicationalSystem}) or a context described by
 * class {@link Context}).
 *
 * This class provides a constructor, and only two methods: the method {@link #initialize}
 * generates all the bijective components of the specified closure system; and the method
 * {@link #save} saves theses components in files.
 *
 * This class can be used as follows:
 *
 * ~~~Java
 * BijectiveComponents BC = new BijectiveComponents(initialClosureSystem);
 * BC.initialize();
 * BC.save(dirString,nameString);
 * ~~~
 *
 * ![BijectiveComponents](BijectiveComponents.png)
 *
 * @uml BijectiveComponents.png
 * !include src/lattice/BijectiveComponents.iuml
 * !include src/lattice/ClosureSystem.iuml
 *
 * hide members
 * show BijectiveComponents members
 * class BijectiveComponents #LightCyan
 * title BijectiveComponents UML graph
 *
 * @todo  comment the abstract class ClosureSystem
 * @todo  compute information at demand
 */
public class BijectiveComponents {
    /* ------------- FIELDS ------------------ */

    /**
     * The initial closure system.
     */

    private ClosureSystem init;

    /**
     * The closed set lattice of the closure system when closure system is an implicational system.
     *
     * The concept lattice of the closure system when closure system is a context
     */
    private ConceptLattice lattice = null;

    /**
     * The reduced lattice.
     */
    private Lattice reducedLattice = null;

    /**
     * The dependency graph of the reduced lattice.
     */
    private DGraph dependencyGraph = null;

    /**
     * The minimal generators of the reduced lattice.
     */
    private TreeSet<ComparableSet> minimalGenerators = null;

    /**
     * The canonical direct basis of the reduced lattice.
     */
    private ImplicationalSystem canonicalDirectBasis = null;

    /**
     * The canonical basis of the reduced lattice.
     */
    private ImplicationalSystem canonicalBasis = null;

    /**
     * The table of the reduced lattice.
     */
    private Context table = null;

    /**
     * Constructs this component with the specified Closure System as initial closure system.
     *
     * @param   init  initial closure system
     */
    public BijectiveComponents(ClosureSystem init) {
        this.init = init;
    }

    /**
     * Generates all the bijective components included in this component
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
     * The dependency graph is obtained by
     *
     * ~~~Java
     * this.reducedLattice.getDependencyGraph();
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
     * new ImplicationalSystem(this.canonicalDirectBasis).makeCanonicalBasis();
     * ~~~
     *
     * @return  time of computation
     */
    public long initialize() {
        long debut = new Date().getTime();
        //if (this.init instanceof lattice.Context)
        if (this.init instanceof Context) {
            this.lattice = ((Context) this.init).conceptLattice(true);
        } else {
            this.lattice = this.init.closedSetLattice(true);
        }
        this.reducedLattice = this.lattice.getIrreduciblesReduction();
        this.table = this.lattice.getTable();
        this.dependencyGraph = this.lattice.getDependencyGraph();
        this.minimalGenerators = this.lattice.getMinimalGenerators();
        this.canonicalDirectBasis = this.lattice.getCanonicalDirectBasis();
        this.canonicalBasis = new ImplicationalSystem(this.canonicalDirectBasis);
        this.canonicalBasis.makeCanonicalBasis();
        long fin = new Date().getTime();
        return fin - debut;
    }

   /**
    * Saves all the bijective components included in this component in files
    * saved in the specified directory.
    * A global description is saved in file `name+"Readme.txt"`.
    *
    * The specified name is used to defined a name for each file.
    *
    * @param   directory  location to save file
    * @param   name       name of the files
    */
    public void save(String directory, String name) {
        try {
            // create the directory
            directory = directory + File.separator + name + "BijectiveComponents" + File.separator;
            File f = new File(directory);
            f.mkdir();
            directory += name;
            BufferedWriter file = new BufferedWriter(new FileWriter(directory + "Readme.txt"));
            // saves the inital closure system
            String nameInit = directory + "InitialClosureSystem.txt";
            this.init.toFile(nameInit);
            file.write("-> Initial closure system saved in " + nameInit + ": \n");
            file.write(this.init.toString() + "\n");
            // saves the closed set lattice
            String nameLattice = directory + "Lattice.dot";
            this.lattice.writeDot(nameLattice);
            file.write("-> Closed set or concept lattice saved in " + nameLattice + "\n");
            // saves the reduced lattice
            String nameReducedLattice = directory + "ReducedLattice.dot";
            this.reducedLattice.writeDot(nameReducedLattice);
            file.write("-> Reduced lattice saved in " + nameReducedLattice + "\n");
            // saves the reduced table
            String nameTable = directory + "Table.txt";
            this.table.toFile(nameTable);
            file.write("-> Table of the reduced lattice saved in " + nameTable + "\n");
            file.write(this.table.toString() + "\n");
            // saves the canonical basis
            String nameCB = directory + "CanonicalBasis.txt";
            this.canonicalBasis.toFile(nameCB);
            file.write("-> Canonical basis saved in " + nameCB + ": \n");
            file.write(this.canonicalBasis.toString() + "\n");
            // saves the canonical direct basis
            String nameCDB = directory + "CanonicalDirectBasis.txt";
            this.canonicalDirectBasis.toFile(nameCDB);
            file.write("-> Canonical direct basis of the reduced lattice saved in " + nameCDB + ": \n");
            file.write(this.canonicalDirectBasis.toString() + "\n");
            // saves the dependency graph
            String nameODGraph = directory + "DependencyGraph.dot";
            this.dependencyGraph.writeDot(nameODGraph);
            file.write("-> Dependency Graph  of the reduced lattice saved in " + nameODGraph + " \n");
            // saves the minimal generators
            file.write("-> Minimal generators  of the reduced lattice are " + this.minimalGenerators + "\n");
            file.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * Returns the Init of this component.
     *
     * @return  init of this component
     */
    public ClosureSystem getInit() {
        return init;
    }

    /**
     * Set the Init of this component.
     *
     * @param   init  used to define field of this component
     */
    protected void setInit(ClosureSystem init) {
        this.init = init;
    }

    /**
     * Returns the lattice of this component.
     *
     * @return  lattice of this component
     */
    public ConceptLattice getLattice() {
        return lattice;
    }

    /**
     * Set the lattice of this component.
     *
     * @param   lattice  used to define field of this component
     */
    protected void setLattice(ConceptLattice lattice) {
        this.lattice = lattice;
    }

    /**
     * Returns the reduced lattice of this component.
     *
     * @return  reduced lattice of this component
     */
    public Lattice getReducedLattice() {
        return reducedLattice;
    }

    /**
     * Set the reduced lattice of this component.
     *
     * @param  reducedLattice  used to define field of this component
     */
    protected void setReducedLattice(Lattice reducedLattice) {
        this.reducedLattice = reducedLattice;
    }

    /**
     * Returns the dependency graph of this component.
     *
     * @return  dependancyGraph  dependency graph of this component
     */
    public DGraph getDependencyGraph() {
        return dependencyGraph;
    }

    /**
     * Set the dependency graph of this component.
     *
     * @param   dependencyGraph  used to define field of this component
     */
    protected void setDependencyGraph(DGraph dependencyGraph) {
        this.dependencyGraph = dependencyGraph;
    }

    /**
     * Returns the minimal generators of this component.
     *
     * @return  minimal generators of this component
     */
    public TreeSet<ComparableSet> getMinimalGenerators() {
        return minimalGenerators;
    }

    /**
     * Set the minimal generators of this component.
     *
     * @param   minimalGenerators  used to define field of this component
     */
    protected void setMinimalGenerators(TreeSet<ComparableSet> minimalGenerators) {
        this.minimalGenerators = minimalGenerators;
    }

    /**
     * Returns the canonical direct basis of this component.
     *
     * @return  the canonical direct basis of this component
     */
    public ImplicationalSystem getCanonicalDirectBasis() {
        return canonicalDirectBasis;
    }

    /**
     * Set the canonical direct basis of this component.
     *
     * @param   canonicalDirectBasis  used to define field of this component
     */
    protected void setCanonicalDirectBasis(ImplicationalSystem canonicalDirectBasis) {
        this.canonicalDirectBasis = canonicalDirectBasis;
    }

    /**
     * Returns the canonical basis of this component.
     *
     * @return  the canonical basis of this component
     */
    public ImplicationalSystem getCanonicalBasis() {
        return canonicalBasis;
    }

    /**
     * Set the canonical basis of this component.
     *
     * @param   canonicalBasis  used to define field of this component
     */
    protected void setCanonicalBasis(ImplicationalSystem canonicalBasis) {
        this.canonicalBasis = canonicalBasis;
    }

    /**
     * Returns the Table of this component.
     *
     * @return  table of this component
     */
    public Context getTable() {
        return table;
    }
    /**
     * Set the Table of this component.
     *
     * @param   table  used to define field of this component
     */
    protected void setTable(Context table) {
        this.table = table;
    }

/** Returns the informativ generic basis
 * public IS getApproximativBasis () {
 * IS IGB = new IS(this.canonicalDirectBasis);
 * IS tmp = new IS(this.canonicalDirectBasis);
 * for (Rule r : tmp.getRules()) {
 *  TreeSet premise = new TreeSet(r.getPremise());
 *  Concept c = new Concept(this.closure(premise),false);
 *  for (TreeSet conclusion : this.immediateSuccessors(c)) {
 *      TreeSet concl = new TreeSet(conclusion);
 *      conclusion.removeAll(premise);
 *      IGB.addRule(new Rule(premise,concl));
 *  }
 * }
 * return IGB;
 * }
 **/
} // end of BijectiveComponents
