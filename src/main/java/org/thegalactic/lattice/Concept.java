package org.thegalactic.lattice;

/*
 * Concept.java
 *
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.thegalactic.context.Context;
import org.thegalactic.dgraph.DAGraph;
import org.thegalactic.dgraph.DGraph;
import org.thegalactic.dgraph.Edge;
import org.thegalactic.dgraph.Node;
import org.thegalactic.util.ComparableSet;

/**
 * This class gives a representation for a concept, i.e. a node of a concept
 * lattice.
 *
 * A concept extends class {@link Node} by providing two comparable sets defined
 * by {@link ComparableSet}, namely `setA` and `setB`, aiming at storing set of
 * a concepts.
 *
 * This component can also be used to store a closed set by using only set `A`.
 *
 * This class implements class `Comparable` aiming at sorting concepts by
 * providing the {@link #compareTo} method. Comparison between this component
 * and those in parameter is realised by comparing set `A`.
 *
 * @todo Should not inherit from Node since content is not used. Maybe by using
 * interface.
 *
 * ![Concept](Concept.png)
 *
 * @uml Concept.png
 * !include resources/org/thegalactic/dgraph/Node.iuml
 * !include resources/org/thegalactic/lattice/Concept.iuml
 * !include resources/org/thegalactic/util/ComparableSet.iuml
 *
 * hide members
 * show Concept members
 * class Concept #LightCyan
 * title Concept UML graph
 */
public class Concept extends Node {

    /*
     * ------------- FIELDS ------------------
     */

    /**
     * This first set of comparable elements of the concept.
     */
    private ComparableSet setA;

    /**
     * This second set of comparable elements of the concept.
     */
    private ComparableSet setB;

    /*
     * ------------- CONSTRUCTORS ------------------
     */
    /**
     * Constructs a new concept containing the specified comparables set as setA
     * and setB.
     *
     * @param setA set of comparable used to initialise setA.
     * @param setB set of comparable used to initialise setB.
     */
    public Concept(TreeSet<Comparable> setA, TreeSet<Comparable> setB) {
        this.setA = new ComparableSet(setA);
        this.setB = new ComparableSet(setB);
    }

    /**
     * Constructs a new concept with an empty set of comparableset as setA and
     * set B if the two boolean are true. False booleans allow to construct a
     * concept with only one of the two sets.
     *
     * @param setA field setA is empty if true, setA is null if false.
     * @param setB field setB is empty if true, setB is null if false.
     */
    public Concept(boolean setA, boolean setB) {
        if (setA) {
            this.setA = new ComparableSet();
        } else {
            this.setA = null;
        }
        if (setB) {
            this.setB = new ComparableSet();
        } else {
            this.setB = null;
        }
    }

    /**
     * Constructs a new concept containing the specified comparables set as
     * setA, and an empty set of comparableset as setB if the boolean is true. A
     * false boolean allows to construct a concept with the only set A.
     *
     * @param setA set of comparable used to initialise setA.
     * @param setB field setB is empty if true, setB is null if false.
     */
    public Concept(TreeSet<Comparable> setA, boolean setB) {
        this.setA = new ComparableSet(setA);
        if (setB) {
            this.setB = new ComparableSet();
        } else {
            this.setB = null;
        }
    }

    /**
     * Constructs a new concept containing the specified comparables set as
     * setB, and an empty set of comparableset as setA if the boolean is true. A
     * false boolean allows to construct concept with the only set B.
     *
     * @param setA field setA is empty if true, setA is null if false.
     * @param setB set of comparable used to initialise setB.
     */
    public Concept(boolean setA, TreeSet<Comparable> setB) {
        this.setB = new ComparableSet(setB);
        if (setA) {
            this.setA = new ComparableSet();
        } else {
            this.setA = null;
        }
    }

    /**
     * Constructs this component as a copy of the specified ClosedSet.
     *
     * @param c the closed set to be copied
     */
    public Concept(Concept c) {
        if (c.hasSetA()) {
            this.setA = new ComparableSet(c.getSetA());
        } else {
            this.setA = null;
        }
        if (c.hasSetB()) {
            this.setB = new ComparableSet(c.getSetB());
        } else {
            this.setB = null;
        }
    }

    /*
     * --------------- ACCESSOR AND OVERLAPPING METHODS ------------
     */
    /**
     * Returns a clone of this component.
     *
     * @return a clone of this component
     */
    @Override
    public Concept clone() {
        if (this.hasSetA() && this.hasSetB()) {
            TreeSet setA = (TreeSet) this.getSetA().clone();
            TreeSet setB = (TreeSet) this.getSetB().clone();
            return new Concept(setA, setB);
        }
        if (this.hasSetA() && !this.hasSetB()) {
            TreeSet setA = (TreeSet) this.getSetA().clone();
            return new Concept(setA, false);
        } else if (this.hasSetB()) {
            TreeSet setB = (TreeSet) this.getSetB().clone();
            return new Concept(false, setB);
        } else {
            return new Concept(false, false);
        }
    }

    /**
     * Checks if the concept has an empty set B.
     *
     * @return true if and only if setB is not null
     */
    public boolean hasSetB() {
        return this.setB != null;
    }

    /**
     * Checks if the concept has an empty set A.
     *
     * @return true if and only if setA is not null
     */
    public boolean hasSetA() {
        return this.setA != null;
    }

    /**
     * Returns the set A of this component.
     *
     * @return the set A of this component
     */
    public TreeSet<Comparable> getSetA() {
        return this.setA;
    }

    /**
     * Returns the set B of comparable of this component.
     *
     * @return the set B of this component.
     */
    public TreeSet<Comparable> getSetB() {
        return this.setB;
    }

    /**
     * Checks if the set A contains the specified comparable.
     *
     * @param x comparable to find in setA.
     *
     * @return true if and only if setA contains x.
     */
    public boolean containsInA(Comparable x) {
        if (this.hasSetA()) {
            return this.setA.contains(x);
        } else {
            return false;
        }
    }

    /**
     * Checks if the set B contains the specified comparable.
     *
     * @param x comparable to find in setB.
     *
     * @return true if and only if setB contains x.
     */
    public boolean containsInB(Comparable x) {
        if (this.hasSetB()) {
            return this.setB.contains(x);
        } else {
            return false;
        }
    }

    /**
     * Checks if the set A contains the specified set of comparable.
     *
     * @param x set of comparable to find in setA.
     *
     * @return true if and only if setA contains all elemens of x.
     */
    public boolean containsAllInA(TreeSet x) {
        if (this.hasSetA()) {
            return this.setA.containsAll(x);
        } else {
            return false;
        }
    }

    /**
     * Checks if the set B contains the specified set of comparable.
     *
     * @param x set of comparable to find in setB.
     *
     * @return true if and only if setB contains all elemens of x.
     */
    public boolean containsAllInB(TreeSet x) {
        if (this.hasSetB()) {
            return this.setB.containsAll(x);
        } else {
            return false;
        }
    }

    /**
     * Replaces the set A of this component by the specified one.
     *
     * @param x set of comparable used to replace setB
     */
    public void putSetB(ComparableSet x) {
        if (this.hasSetB()) {
            this.setB = x;
        } else {
            this.setB = new ComparableSet(x);
        }
    }

    /**
     * Replaces the set A of this component by the specified one.
     *
     * @param x set of comparable used to replace setA
     */
    public void putSetA(ComparableSet x) {
        if (this.hasSetA()) {
            this.setA = x;
        } else {
            this.setA = new ComparableSet(x);
        }
    }

    /**
     * Adds a comparable to the set A.
     *
     * @param x comparable to add to setA
     *
     * @return true if and only if addition is successful.
     */
    public boolean addToA(Comparable x) {
        if (this.hasSetA()) {
            return this.setA.add(x);
        } else {
            return false;
        }
    }

    /**
     * Adds a comparable to the set B.
     *
     * @param x comparable to add to setB
     *
     * @return true if and only if addition is successful.
     */
    public boolean addToB(Comparable x) {
        if (this.hasSetB()) {
            return this.setB.add(x);
        } else {
            return false;
        }
    }

    /**
     * Adds the specified set of comparable to the set A.
     *
     * @param x set of comparable to add to setA
     *
     * @return true if and only if addition is successful.
     */
    public boolean addAllToA(TreeSet x) {
        if (this.hasSetA()) {
            return this.setA.addAll(x);
        } else {
            return false;
        }
    }

    /**
     * Adds the specified set of comparable to the set B.
     *
     * @param x set of comparable to add to setB
     *
     * @return true if and only if addition is successful.
     */
    public boolean addAllToB(TreeSet x) {
        if (this.hasSetB()) {
            return this.setB.addAll(x);
        } else {
            return false;
        }
    }

    /**
     * Remove a comparable from the set A.
     *
     * @param x comparable to remove from setA
     *
     * @return true if and only if removal is successful.
     */
    public boolean removeFromA(Comparable x) {
        if (this.hasSetA()) {
            return this.setA.remove(x);
        } else {
            return false;
        }
    }

    /**
     * Remove a comparable from the set B.
     *
     * @param x comparable to remove from setB
     *
     * @return true if and only if removal is successful.
     */
    public boolean removeFromB(Comparable x) {
        if (this.hasSetB()) {
            return this.setB.remove(x);
        } else {
            return false;
        }
    }

    /**
     * Remove a set of comparable from the set A.
     *
     * @param x set to remove from setA
     *
     * @return true if and only if removal is successful.
     */
    public boolean removeAllFromA(TreeSet x) {
        if (this.hasSetA()) {
            return this.setA.removeAll(x);
        } else {
            return false;
        }
    }

    /**
     * Remove a set of comparable from the set B.
     *
     * @param x set to remove from setB
     *
     * @return true if and only if removal is successful.
     */
    public boolean removeAllFromB(TreeSet x) {
        if (this.hasSetB()) {
            return this.setB.removeAll(x);
        } else {
            return false;
        }
    }

    /*
     * --------------- OVERLAPPING METHODS ------------
     */
    /**
     * Returns the description of this component in a String without spaces.
     *
     * @return the description of this component in a String without spaces.
     */
    @Override
    public String toString() {
        String s = "";
        if (this.hasSetA()) {
            s += this.setA;
        }
        if (this.hasSetA() && this.hasSetB()) {
            s += "-";
        }
        if (this.hasSetB()) {
            s += this.setB;
        }
        StringTokenizer st = new StringTokenizer(s);
        s = "";
        while (st.hasMoreTokens()) {
            s += st.nextToken();
        }
        return s;
    }

    /**
     * Returns the hash code of this component.
     *
     * @return hash code of this component
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Compares this component with the specified one.
     *
     * @param o object compared to this component.
     *
     * @return true if and only if o is equals to this component.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Concept)) {
            return false;
        }
        if (!this.hasSetB()) {
            return this.setA.equals(((Concept) o).setA);
        }
        if (!this.hasSetA()) {
            return this.setB.equals(((Concept) o).setB);
        }
        return this.setA.equals(((Concept) o).setA) && this.setB.equals(((Concept) o).setB);
    }

    /**
     * Compares this component with the specified one sorted by the lectic
     * order.
     *
     * @return a negative integer, zero, or a positive integer as this component
     * is less than, equal to, or greater than the specified object.
     */
    /*
     * public int compareTo(Object o){
     * if (!(o instanceof lattice.Concept)) return -1;
     * Concept c = (Concept) o;
     * //System.out.println("compareTo : "+this+" "+o);
     * if (!this.hasSetB()) {
     * return this.setA.compareTo(c.setA);
     * }
     * if (!this.hasSetA()) {
     * return this.setB.compareTo(c.setB);
     * }
     * if (this.setA.compareTo(c.setA)!=0) {
     * return this.setB.compareTo(c.setB);
     * } else {
     * return this.setA.compareTo(c.setA);
     * }
     * }
     */
    /**
     * Computes the immediate successors of this component with the LOA
     * algorithm.
     *
     * @param init context from which successor of this component are computed.
     *
     * @return immediate successors of this component.
     */
    public ArrayList<TreeSet<Comparable>> immediateSuccessorsLOA(Context init) {
        ArrayList<TreeSet<Comparable>> succB = new ArrayList();
        TreeSet<Comparable> attributes = (TreeSet<Comparable>) init.getSet().clone();
        attributes.removeAll(this.getSetA());

        boolean add;
        for (Comparable x : attributes) {
            add = true;
            Iterator it = succB.iterator();
            while (it.hasNext()) {
                TreeSet tX = (TreeSet) it.next();
                TreeSet<Comparable> bx = (TreeSet<Comparable>) this.getSetA().clone();
                bx.add(x);
                TreeSet<Comparable> bX = (TreeSet<Comparable>) this.getSetA().clone();
                bX.addAll(tX);
                TreeSet<Comparable> bXx = (TreeSet<Comparable>) bX.clone();
                bXx.add(x);
                int cBx = count(init, bx);
                int cBX = count(init, bX);
                int cBXx = count(init, bXx);
                if (cBx == cBX) { // Try to group tests by pairs.
                    if (cBXx == cBx) {
                        it.remove(); // Update present potential successor.
                        TreeSet<Comparable> xX = new TreeSet();
                        xX.addAll(tX);
                        xX.add(x);
                        succB.add(xX);
                        add = false;
                        break;
                    }
                }
                if (cBx < cBX) {
                    if (cBXx == cBx) {
                        add = false;
                        break;
                    }
                }
                if (cBx > cBX) {
                    if (cBXx == cBX) {
                        it.remove();
                    }
                }
            }
            if (add) {
                TreeSet<Comparable> t = new TreeSet();
                t.add(x);
                succB.add(new TreeSet(t));
            }
        }
        for (TreeSet t : succB) {
            t.addAll(this.getSetA());
        }
        return succB;
    }

    /**
     * Returns the number of observations corresponding to the set of attributes
     * in the init context.
     *
     * @param init initial context from which attributes are count.
     * @param attributes attributes from which observations are counted.
     *
     * @return number of observations corresponding to the set of attributes in
     * init context.
     */
    private int count(Context init, TreeSet<Comparable> attributes) {
        return init.getExtentNb(attributes);
    }

    /**
     * Returns the list of immediate successors of a given node of the lattice.
     *
     * This treatment is an adaptation of Bordat's theorem stating that there is
     * a bijection between minimal strongly connected component of the
     * precedence subgraph issued from the specified node, and its immediate
     * successors.
     *
     * This treatment is performed in O(Cl|S|^3log g) where S is the initial set
     * of elements, Cl is the closure computation complexity and g is the number
     * of minimal generators of the lattice.
     *
     * This treatment is recursively invoked by method recursiveDiagramlattice.
     * In this case, the dependance graph is initialised by method
     * recursiveDiagramMethod, and updated by this method, with addition some
     * news edges and/or new valuations on existing edges. When this treatment
     * is not invoked by method recursiveDiagramLattice, then the dependance
     * graph is initialised, but it may be not complete. It is the case for
     * example for on-line generation of the concept lattice.
     *
     * cguerin - 2013-04-12 - transfer immedateSuccessors method from
     * ConceptLattice to Concept
     *
     * @param init closure system used to compute immediate successors of this
     * component.
     *
     * @return the list of immediate successors of this component.
     */
    public ArrayList<TreeSet<Comparable>> immediateSuccessors(ClosureSystem init) {
        // Initialisation of the dependance graph when not initialised by method recursiveDiagramLattice
        DGraph dependanceGraph = new DGraph();
        for (Comparable c : init.getSet()) {
            dependanceGraph.addNode(new Node(c));
        }
        // computes newVal, the subset to be used to valuate every new dependance relation
        // newVal = F\predecessors of F in the precedence graph of the closure system
        // For a non reduced closure system, the precedence graph is not acyclic,
        // and therefore strongly connected components have to be used.
        ComparableSet f = new ComparableSet(this.getSetA());
        long start = System.currentTimeMillis();
        System.out.print("Precedence graph... ");
        DGraph prec = init.precedenceGraph();
        System.out.println(System.currentTimeMillis() - start + "ms");
        start = System.currentTimeMillis();
        System.out.print("Srongly connected component... ");
        DAGraph acyclPrec = prec.getStronglyConnectedComponent();
        System.out.println(System.currentTimeMillis() - start + "ms");
        ComparableSet newVal = new ComparableSet();
        newVal.addAll(f);
        for (Object x : f) {
            // computes nx, the strongly connected component containing x
            Node nx = null;
            for (Node cc : acyclPrec.getNodes()) {
                TreeSet<Node> cC = (TreeSet<Node>) cc.getContent();
                for (Node y : cC) {
                    if (x.equals(y.getContent())) {
                        nx = cc;
                    }
                }
            }
            // computes the minorants of nx in the acyclic graph
            SortedSet<Node> ccMinNx = acyclPrec.minorants(nx);
            // removes from newVal every minorants of nx
            for (Node cc : ccMinNx) {
                TreeSet<Node> cC = (TreeSet<Node>) cc.getContent();
                for (Node y : cC) {
                    newVal.remove(y.getContent());
                }
            }
        }
        // computes the node belonging in S\F
        TreeSet<Node> n = new TreeSet<Node>();
        for (Node in : dependanceGraph.getNodes()) {
            if (!f.contains(in.getContent())) {
                n.add(in);
            }
        }
        System.out.print("Dependance... ");
        start = System.currentTimeMillis();
        // computes the dependance relation between nodes in S\F
        // and valuated this relation by the subset of S\F
        TreeSet<Edge> e = new TreeSet<Edge>();
        for (Node source : n) {
            for (Node target : n) {
                if (!source.equals(target)) {
                    // check if source is in dependance relation with target
                    // i.e. "source" belongs to the closure of "F+target"
                    ComparableSet fPlusTo = new ComparableSet(f);
                    fPlusTo.add(target.getContent());
                    fPlusTo = new ComparableSet(init.closure(fPlusTo));
                    if (fPlusTo.contains(source.getContent())) {
                        // there is a dependance relation between source and target
                        // search for an existing edge between source and target
                        Edge ed = dependanceGraph.getEdge(source, target);
                        if (ed == null) {
                            ed = new Edge(source, target, new TreeSet<ComparableSet>());
                            dependanceGraph.addEdge(ed);
                        }
                        e.add(ed);
                        // check if F is a minimal set closed for dependance relation between source and target
                        ((TreeSet<ComparableSet>) ed.getContent()).add(newVal);
                        TreeSet<ComparableSet> valEd = new TreeSet<ComparableSet>((TreeSet<ComparableSet>) ed.getContent());
                        for (ComparableSet x1 : valEd) {
                            if (x1.containsAll(newVal) && !newVal.containsAll(x1)) {
                                ((TreeSet<ComparableSet>) ed.getContent()).remove(x1);
                            }
                            if (!x1.containsAll(newVal) && newVal.containsAll(x1)) {
                                ((TreeSet<ComparableSet>) ed.getContent()).remove(newVal);
                            }
                        }
                    }
                }
            }
        }
        System.out.println(System.currentTimeMillis() - start + "ms");
        System.out.print("Subgraph... ");
        start = System.currentTimeMillis();
        // computes the dependance subgraph of the closed set F as the reduction
        // of the dependance graph composed of nodes in S\A and edges of the dependance relation
        DGraph sub = dependanceGraph.getSubgraphByNodes(n);
        DGraph delta = sub.getSubgraphByEdges(e);
        // computes the sources of the CFC of the dependance subgraph
        // that corresponds to successors of the closed set F
        DAGraph cfc = delta.getStronglyConnectedComponent();
        SortedSet<Node> sccmin = cfc.getSinks();
        System.out.println(System.currentTimeMillis() - start + "ms");
        ArrayList<TreeSet<Comparable>> immSucc = new ArrayList<TreeSet<Comparable>>();
        for (Node n1 : sccmin) {
            TreeSet s = new TreeSet(f);
            TreeSet<Node> toadd = (TreeSet<Node>) n1.getContent();
            for (Node n2 : toadd) {
                s.add(n2.getContent());
            }
            immSucc.add(s);
        }
        return immSucc;
    }
}
