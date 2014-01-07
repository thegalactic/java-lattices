package lattice.lattice;

/*
 * Concept.java
 *
 * last update on March 2010
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;
import lattice.dgraph.Node;
/**
 * This class gives a representation for a concept, i.e. a node of a concept lattice.
 * <p>
 * A concept extends class <code>Node</code> by providing two comparable sets defined
 * by <code>ComparableSet</code>, namely setA and setB, aiming at storing set of a concepts.
 * <p>
 * This component can also be used to store a closed set by using only set A.
 * <p>
 * This class implements class <code>Comparable</code> aiming at
 * sorting concepts by providing the <code>compareTo()</code> method.
 * Comparison between this component and those in parameter is realised by comparing set A.
 * <p>
 * Copyright: 2013 University of La Rochelle, France
 * @license: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 * @author Karell Bertet
 * @version 2013
 */
public class Concept extends Node {

	/* ------------- FIELDS ------------------ */		

    /** This first set of comparable elements of the concept **/
    protected ComparableSet setA;
    /** This second set of comparable elements of the concept **/
    protected ComparableSet setB;

	/* ------------- CONSTRUCTORS ------------------ */	
	
  	/** Constructs a new concept containing the specified
	* comparables set as setA and setB **/
	public Concept (TreeSet<Comparable> setA, TreeSet<Comparable> setB) {
		this.setA = new ComparableSet(setA);
		this.setB = new ComparableSet(setB);
		}
    /** Constructs a new concept with an empty set of comparableset as setA
     * and set B if the two boolean are true. False booleans allow to construct
     * a concept with only one of the two sets.  **/
	public Concept(boolean setA, boolean setB) {
        if (setA) this.setA = new ComparableSet();
        else this.setA = null;
        if (setB) this.setB = new ComparableSet();
        else this.setB = null;
		}
    /** Constructs a new concept containing the specified
	* comparables set as setA, and an empty set of comparableset as setB
    * if the boolean is true. A false boolean allows to construct
    * a concept with the only set A.  **/
	public Concept(TreeSet<Comparable> setA, boolean setB) {
		this.setA = new ComparableSet(setA);
        if (setB) this.setB = new ComparableSet();
        else this.setB = null;
		}
    /** Constructs a new concept containing the specified
	* comparables set as setB, and an empty set of comparableset as setA
    * if the boolean is true. A false boolean allows to construct
    * a concept with the only set B.  **/
	public Concept(boolean setA, TreeSet<Comparable> setB) {
		this.setB = new ComparableSet(setB);
        if (setA) this.setA = new ComparableSet();
        else this.setA = null;
		}
	/**  Constructs this component as a copy of the specified ClosedSet 
	* @param C the closed set to be copied */
	public Concept (Concept C) {
        if (C.hasSetA()) this.setA = new ComparableSet(C.getSetA());
        else this.setA= null;
        if (C.hasSetB()) this.setB = new ComparableSet(C.getSetB());
        else this.setB= null;
		}

	/* --------------- ACCESSOR AND OVERLAPPING METHODS ------------ */

    /** Returns a copy of this component **/
    public Concept copy() {       
        if (this.hasSetA() && this.hasSetB()) {
            TreeSet setA = (TreeSet) this.getSetA().clone();
            TreeSet setB = (TreeSet) this.getSetB().clone();            
            return new Concept(setA,setB);
        }
        if (this.hasSetA() && !this.hasSetB()) {
            TreeSet setA = (TreeSet) this.getSetA().clone();
            return new Concept(setA,false);
        }
        else {
            TreeSet setB = (TreeSet) this.getSetB().clone();
            return new Concept(false,setB);
        }
    }

    /** Cheks if the concept has an empty set B */
    public boolean hasSetB () {
        return this.setB!=null; }
    /** Cheks if the concept has an empty set B */
    public boolean hasSetA () {
        return this.setA!=null; }


	/** Returns the set A of this component */
	public TreeSet<Comparable> getSetA () {
		return this.setA; }
    /** Returns the set B of comparable of this component */
	public TreeSet<Comparable> getSetB () {
		return this.setB; }

	/** Checks if the set A contains the specified comparable */
	public boolean containsInA (Comparable x) {
        if (this.hasSetA())
    		return this.setA.contains(x);
        else return false;}
	/** Checks if the set B contains the specified comparable */
	public boolean containsInB (Comparable x) {
        if (this.hasSetB())
    		return this.setB.contains(x);
        else return false;}
    /** Checks if the set A contains the specified set of comparable */
	public boolean containsAllInA (TreeSet X) {
		if (this.hasSetA())
		return this.setA.containsAll(X);
        else return false;}
	/** Checks if the set B contains the specified set of comparable */
	public boolean containsAllInB (TreeSet X) {
        if (this.hasSetB())
		return this.setB.containsAll(X);
        else return false;}
	/** Replaces the set A of this component by the specified one */
    public void putSetB (ComparableSet X) {
        if (this.hasSetB())
    		this.setB = X;
        else this.setB = new ComparableSet(X);}
	/** Replaces the set A of this component by the specified one */
	public void putSetA (ComparableSet X) {
		if (this.hasSetA())
    		this.setA = X;
        else this.setA = new ComparableSet(X);}

	/** Adds a comparable to the set A */
	public boolean addToA (Comparable x) {
		if (this.hasSetA())
		return this.setA.add(x);
        else return false;}
    /** Adds a comparable to the set B */
	public boolean addToB (Comparable x) {
        if (this.hasSetB())
		return this.setB.add(x);
        else return false;}
	/** Adds the specified set of comparable to the set A */
	public boolean addAllToA (TreeSet X) {
		if (this.hasSetA())
		return this.setA.addAll(X);
        else return false;}
	/** Adds the specified set of comparable to the set B */
	public boolean addAllToB (TreeSet X) {
        if (this.hasSetB())
		return this.setB.addAll(X);
        else return false;}
	/** Remove a comparable from the set A */
	public boolean removeFromA (Comparable x) {
		if (this.hasSetA())
		return this.setA.remove(x);
        else return false;}
	/** Remove a comparable from the set B */
	public boolean removeFromB (Comparable x) {
        if (this.hasSetB())
		return this.setB.remove(x);
        else return false;}
	/** Remove a comparable from the set A */
	public boolean removeAllFromA (TreeSet X) {
		if (this.hasSetA())
		return this.setA.removeAll(X);
        else return false;}
	/** Remove a comparable from the set B */
	public boolean removeAllFromB (TreeSet X) {
        if (this.hasSetB())
		return this.setB.removeAll(X);
        else return false;}

	/* --------------- OVERLAPPING METHODS ------------ */

    /** Returns the description of this component in a String without spaces*/
	public String toString () {
       String s="";
       if (this.hasSetA()) s+=this.setA;
       if (this.hasSetA()&&this.hasSetB()) s+="-";
       if (this.hasSetB()) s+=this.setB;
       StringTokenizer st = new StringTokenizer (s);
       s = "";
		while (st.hasMoreTokens())
				s+=st.nextToken();
       return s;
    }

    /** Returns the dot description of this component in a String */
	public String toDot () {
    	String s = this.ident+" [label=\" "  ;
        String tmp="";
       if (this.hasSetA()) tmp+=this.setA;
       if (this.hasSetA()&&this.hasSetB()) tmp+="\\n";
       if (this.hasSetB()) tmp+=this.setB;
		StringTokenizer st = new StringTokenizer (tmp,"\"");
		while (st.hasMoreTokens())
				s+=st.nextToken();
		s += "\"]";
		return s;
    }

	/** Compares this component with the specified one */
	public boolean equals (Object o) {
        if (!(o instanceof Concept)) return false;
        if (!this.hasSetB())
			return this.setA.equals(((Concept)o).setA);
        if (!this.hasSetA())
			return this.setB.equals(((Concept)o).setB);
		return this.setA.equals(((Concept)o).setA)
                && this.setB.equals(((Concept)o).setB);
    }
	/** Compares this component with the specified one sorted by the lectic order.
	* @return a negative integer, zero, or a positive integer as this component is less than,
	* equal to, or greater than the specified object.
	*/
	/**public int compareTo(Object o){
        if (!(o instanceof lattice.Concept)) return -1;
        Concept c = (Concept) o;
        //System.out.println("compareTo : "+this+" "+o);
        if (!this.hasSetB()) 
			return this.setA.compareTo(c.setA); 
        if (!this.hasSetA()) 		
			return this.setB.compareTo(c.setB); 
		if (this.setA.compareTo(c.setA)!=0) 
            return this.setB.compareTo(c.setB); 
        else return this.setA.compareTo(c.setA);
		}**/
        public ArrayList<TreeSet<Comparable>> immediateSuccessorsLOA(Context init){
            ArrayList<TreeSet<Comparable>> succB = new ArrayList();
            TreeSet<Comparable> attributes = (TreeSet<Comparable>) init.getSet().clone();
            attributes.removeAll(this.getSetA());
            
            boolean add;
            for (Comparable x : attributes){
                add = true;
                Iterator it = succB.iterator();
                while(it.hasNext()){
                    TreeSet X = (TreeSet) it.next();
                    TreeSet<Comparable> Bx = (TreeSet<Comparable>) this.getSetA().clone();
                    Bx.add(x);                    
                    TreeSet<Comparable> BX = (TreeSet<Comparable>) this.getSetA().clone();
                    BX.addAll(X);
                    TreeSet<Comparable> BXx = (TreeSet<Comparable>) BX.clone();
                    BXx.add(x);
                    int cBx = count(init, Bx);
                    int cBX = count(init, BX);
                    int cBXx = count(init, BXx);
                    if(cBx == cBX){
                        if(cBXx == cBx){
                            it.remove();
                            TreeSet<Comparable> Xx = new TreeSet();
                            Xx.addAll(X);
                            Xx.add(x);
                            succB.add(Xx);   
                            add=false;
                            break;
                        }
                    }
                    if(cBx < cBX){
                        if(cBXx == cBx){
                            add=false;
                            break;
                        }
                    }
                    if(cBx > cBX){
                        if(cBXx == cBX){
                            it.remove();
                        }
                    }
                }
                if(add){
                    TreeSet<Comparable> t = new TreeSet();
                    t.add(x);
                    succB.add(new TreeSet(t));
                }
            }
            for (TreeSet t : succB){
                t.addAll(this.getSetA());
            }
            return succB;
        }
        
        private int count(Context init, TreeSet<Comparable> attributes){
            return init.getExtentNb(attributes);
        }
        
        //cguerin - 2013-04-12 - transfer immedateSuccessors method from ConceptLattice to Concept
        public ArrayList<TreeSet<Comparable>> immediateSuccessors (ClosureSystem init) {
            // Initialization of the dependance graph when not initialized by method recursiveDiagramLattice
            DGraph dependanceGraph = null;
            dependanceGraph = new DGraph();
            for (Comparable c : init.getSet()){
                dependanceGraph.addNode(new Node(c));
            }
            // computes newVal, the subset to be used to valuate every new dependance relation
            // newVal = F\predecessors of F in the precedence graph of the closure system
            // For a non reduced closure system, the precedence graph is not acyclic,
            // and therefore strongly connected components have to be used.            
            ComparableSet F = new ComparableSet (this.getSetA());
            long start = System.currentTimeMillis();
            System.out.print("Precedence graph... ");
            DGraph prec = init.precedenceGraph();
            System.out.println(System.currentTimeMillis()-start+"ms");
            start = System.currentTimeMillis();
            System.out.print("Srongly connected component... ");
            DAGraph acyclPrec = prec.stronglyConnectedComponent();
            System.out.println(System.currentTimeMillis()-start+"ms");
            ComparableSet newVal = new ComparableSet ();
            newVal.addAll(F);
            for (Object x : F)  {
                // computes nx, the strongly connected component containing x
                Node nx = null;
                for (Node cc : acyclPrec.getNodes()) {
                    TreeSet<Node> CC = (TreeSet<Node>) cc.content;
                    for (Node y : CC){
                        if (x.equals(y.content)){
                            nx=cc;
                        }
                    }
                }
                // computes the minorants of nx in the acyclic graph
                TreeSet<Node> ccMinNx = acyclPrec.minorants(nx);
                // removes from newVal every minorants of nx
                for (Node cc : ccMinNx) {
                    TreeSet<Node> CC = (TreeSet<Node>) cc.content;
                    for (Node y : CC){
                        newVal.remove(y.content);
                    }
                }
            }
            // computes the node belonging in S\F
            TreeSet<Node> N = new TreeSet<Node> ();
            for (Node in : dependanceGraph.getNodes()) {
                if (!F.contains(in.content)){
                    N.add(in);
                }
            }
            
            System.out.print("Dependance... ");
            start = System.currentTimeMillis();
            // computes the dependance relation between nodes in S\F
            // and valuated this relation by the subset of S\F
            TreeSet<Edge> E = new TreeSet<Edge>();
            for (Node from : N) {
                for (Node to : N) {
                   if (!from.equals(to)) {
                    // check if from is in dependance relation with to
                    // i.e. "from" belongs to the closure of "F+to"
                    ComparableSet FPlusTo = new ComparableSet(F);
                    FPlusTo.add(to.content);
                    FPlusTo = new ComparableSet(init.closure(FPlusTo));
                    if (FPlusTo.contains(from.content)) {
                        // there is a dependance relation between from and to
                        // search for an existing edge between from and to
                        Edge ed = dependanceGraph.getEdge(from, to);
                        if (ed==null) {
                            ed = new Edge (from,to,new TreeSet<ComparableSet>());
                            dependanceGraph.addEdge(ed);
                        }
                        E.add(ed);
                        // check if F is a minimal set closed for dependance relation between from and to
                        ((TreeSet<ComparableSet>)ed.content()).add(newVal);
                        TreeSet<ComparableSet> ValEd = new TreeSet<ComparableSet>((TreeSet<ComparableSet>)ed.content());
                            for (ComparableSet X1 : ValEd) {
                                if (X1.containsAll(newVal) && !newVal.containsAll(X1)){
                                    ((TreeSet<ComparableSet>)ed.content()).remove(X1);
                                }                                    
                                if (!X1.containsAll(newVal) && newVal.containsAll(X1)){
                                    ((TreeSet<ComparableSet>)ed.content()).remove(newVal);
                                }
                            }
                        }
                    }
                }
            }            
            System.out.println(System.currentTimeMillis()-start+"ms");
            System.out.print("Subgraph... ");
            start = System.currentTimeMillis();
            // computes the dependance subgraph of the closed set F as the reduction
            // of the dependance graph composed of nodes in S\A and edges of the dependance relation
            DGraph sub = dependanceGraph.subgraphByNodes(N);            
            DGraph delta = sub.subgraphByEdges(E);            
            // computes the sources of the CFC of the dependance subgraph
            // that corresponds to successors of the closed set F
            DAGraph CFC = delta.stronglyConnectedComponent();            
            TreeSet<Node> SCCmin = CFC.sinks();
            System.out.println(System.currentTimeMillis()-start+"ms");
            ArrayList<TreeSet<Comparable>> immSucc = new ArrayList<TreeSet<Comparable>>();            
            for (Node n1 : SCCmin) {
                TreeSet s = new TreeSet(F);
                TreeSet<Node> toadd = (TreeSet<Node>)n1.content;
                for (Node n2 : toadd){
                    s.add(n2.content);
                }                        
                immSucc.add(s);
            }
            return immSucc;
        }
}// end of Concept