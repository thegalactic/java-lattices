package lattice;

/*
 * Lattice.java
 *
 * last update on March 2010
 *
 */
import java.util.TreeMap;
import java.util.TreeSet;
import dgraph.DAGraph;
import dgraph.DGraph;
import dgraph.Edge;
import dgraph.Node;
/**
 * This class extends class `DAGraph` to provide specific methods
 * to manipulate a lattice. 
 *
 * A lattice is a directed acyclic graph (DAGraph) containing particular nodes denoted join and meet
 * (a dag is a lattice if and only if each pair of nodes admits a join and a meet.)
 *
 * Since checking the lattice property is very time-expensive, this property is not ensured 
 * for components of this class. However, it can be explicitely ckecked using
 * method `boolean isLattice ()`.
 *
 * This class provides methods implementing classical operation on a lattice
 * issued from join and meet operation and irreducibles elements,
 * and methods that returns a condensed representation of a lattice.
 *
 * A well-known condensed representation of a lattice is its table, obtained by method
 * `Context getTable()`,
 * where join-irreducibles are in column and meet-irreducibles are in rown.
 *
 * Another condensed representation is its dependance graph obtained by method
 * `DGraph getDependanceGraph()`.
 * The dependance graph is a directed graph where nodes are join-irreducibles, 
 * edges corresponds to the dependance relation between two join-irreducibles 
 * and are valuated by a family of subsets of irreducibles.
 *
 * The dependance graph encodes two other condensed representation of a lattice
 * that are its minimal generators and its canonical direct basis
 * that can be obtained from the dependance graph by methods
 * `TreeSet getMinimalGenerators()` and `IS getCanonicalDirectBasis()`.
 *
 * Copyright: 2013 University of La Rochelle, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 *
 * @author Karell Bertet
 * @version 2013
 */
public class Lattice extends DAGraph {

	/* ------------- FIELDS ------------------ */

    /** The dependance graph of a lattice.
     * Nodes are join irreducibles elements,
     * and edges correspond to the dependance relation of the lattice
     * (j -> j' if and only if there exists a node x in the lattice such that
     * x not greather than j and j', and x v j' > j), 
     * and edges are labeled with the smallest subsets X of join-irreducibles such that 
     * the join of elements of X corresponds to the node x of the lattice. 
     */
    protected DGraph dependanceGraph = null;

	/* ------------- CONSTRUCTORS ------------------ */	
	/** Constructs this component with an empty set of nodes.*/     
	public Lattice () {
        super ();
    }
  	/** Constructs this component with the specified set of nodes, 
	* and empty treemap of successors and predecessors 
	* @param S the set of nodes **/	
   public Lattice (TreeSet<Node> S) {
       super (S);
   }
	/**  Constructs this component as a copy of the specified lattice.
	 *
	* Lattice property is checked for the specified lattice.
	* When not verified, this component is construct with an empty set of nodes. 
	* @param G the Lattice to be copied */
	public Lattice (DAGraph G) {	
		super (G);
		if (!this.isAcyclic()) { 
			this.setNodes(new TreeSet<Node>()); 
			this.setSuccessors(new TreeMap<Node,TreeSet<Edge>>());
			this.setPredecessors(new TreeMap<Node,TreeSet<Edge>>());
			}
    }

    /* ------------- LATTICE CHEKING METHODS ------------------ */
 
   /** Check if this component is a lattice.
    *
	* There exists several caracterizations of a lattice. 
	* The caracterization implemented is the following: 
	* A lattice is a DAG if there exists a meet for each pair of node, 
	* and an unique maximal node.
	*
	* This treatment is performed in O(n^3), where n is the number of nodes.
	*/
   public boolean isLattice () {
		if (!this.isAcyclic()) return false;
		for (Node x : this.getNodes())
			for (Node y : this.getNodes()) 
				if (this.meet(x,y)==null) 
					return false;
		return (this.max().size()==1);							
	}

	/* --------------- LATTICE HANDLING METHODS ------------ */	
	
	/** Returns the top of the lattice **/
	public Node top () {
		TreeSet<Node> max = this.max();
		if (max.size()==1)
			return max.first();
		return null;	
		}
	/** Returns the top of the lattice **/
	public Node botom () {
		TreeSet<Node> min = this.min();
		if (min.size()==1)
			return min.first();
		return null;	
		}

	/** Returns the meet of the two specified nodes if it exists **/
	public Node meet (Node x, Node y) {
		TreeSet<Node> Mx = this.minorants(x);
		Mx.add(x);
		TreeSet<Node> My = this.minorants(y);
		My.add(y);
		Mx.retainAll(My);
		DAGraph G = this.getSubgraphByNodes(Mx);
		TreeSet<Node> meet = G.max();
		if (meet.size() == 1)
			return meet.first();
		return null;
		}		 
	/** Returns the join of the two specified nodes if it exists **/
	public Node join (Node x, Node y) {		
		TreeSet<Node> Mx = this.majorants(x);
		Mx.add(x);
		TreeSet<Node> My = this.majorants(y);
		My.add(y);
		Mx.retainAll(My);
		DAGraph G = this.getSubgraphByNodes(Mx);
		TreeSet<Node> join = G.min();
		if (join.size() == 1)
			return join.first();
		return null;
		}

	/* ------------- IRREDUCIBLES RELATIVE METHODS ------------------ */				
	
	/** Returns the set of join irreducibles of this component. 
	* Join irreducibles are nodes with an unique immediate predecessor
	* in the transitiv and reflexiv reduction. 
	* This component is first reduced reflexively and transitively. 
	**/
   public TreeSet<Node> joinIrreducibles () {
		DAGraph G = new DAGraph (this);
		G.reflexiveReduction();
		G.transitiveReduction();
		TreeSet<Node> J = new TreeSet();
		for (Node n : G.getNodes()) 
			if (G.getPredecessorNodes(n).size()==1) J.add(n);
		return J;	
 	 }

	/** Returns the set of meet irreducibles of this component. 
	* Mett irreducibles are nodes with an unique immediate successor
	* in the transitiv and reflexiv reduction.
	* This component is first reduced reflexively and transitively. 
	**/
   public TreeSet<Node> meetIrreducibles () {
		DAGraph G = new DAGraph (this);
		G.reflexiveReduction();
		G.transitiveReduction();
		TreeSet<Node> M = new TreeSet();
		for (Node n : G.getNodes()) 
			if (G.getSuccessorNodes(n).size()==1) M.add(n);
		return M;	
 	 }

    /** Returns the set of join-irreducibles thar are minorants of the specified node.**/
    public TreeSet<Comparable> joinIrreducibles (Node n) {
		TreeSet<Comparable> join = new TreeSet<Comparable> (this.joinIrreducibles());
        TreeSet<Comparable> min = new TreeSet<Comparable> (this.minorants(n));
        min.add(n);              
        min.retainAll(join);        
        return min;
 	 }

    /** Returns the set of meet-irreducibles thar are majorants of the specified node.**/
    public TreeSet<Comparable> meetIrreducibles (Node n) {
		TreeSet<Comparable> meet = new TreeSet<Comparable> (this.meetIrreducibles());
        TreeSet<Comparable> maj = new TreeSet<Comparable> (this.majorants(n));
        maj.retainAll(meet);
        return maj;
 	 }


	/** Returns the subgraph induced by the join irreducibles nodes of this component. **/
	public DAGraph joinIrreduciblesSubgraph () {
		TreeSet<Node> Irr = this.joinIrreducibles();
		return this.getSubgraphByNodes(Irr);
	}	
	/** Returns the subgraph induced by the meet irreducibles nodes of this component. **/
	public DAGraph meetIrreduciblesSubgraph () {
		TreeSet<Node> Irr = this.meetIrreducibles();
		return this.getSubgraphByNodes(Irr);
	}	


	/** Returns the subgraph induced by the irreducibles nodes of this component. **/
	public DAGraph irreduciblesSubgraph () {
		TreeSet<Node> Irr = this.meetIrreducibles();
		Irr.addAll (this.joinIrreducibles());
		return this.getSubgraphByNodes(Irr);
	}	

	/** Generates and returns the isomorphic closed set lattice defined on the join irreducibles set.
	 *
	* Each node of this component is replaced  by a node containing its join irreducibles 
	* predecessors stored in a closed set. */
	public ConceptLattice joinClosure () {
		ConceptLattice CSL = new ConceptLattice();
		// associates each node to a new closed set of join irreducibles
		TreeSet<Node> Join = this.joinIrreducibles();		
		TreeMap<Node,Concept> closure = new TreeMap<Node,Concept>();
		Lattice L = new Lattice (this);
		L.transitiveClosure();
		L.reflexiveClosure();
		for (Node to : L.getNodes()) {
			ComparableSet Jx = new ComparableSet();
			for (Node from : L.getPredecessorNodes(to))
				if (Join.contains(from))
					Jx.add(from.getContent());
			closure.put(to,new Concept(Jx,false));
		}
		// addition of nodes
		for (Node n : this.getNodes()) 
			CSL.addNode(closure.get(n));
		// addition of edges
		for (Edge ed : this.getEdges())
                    CSL.addEdge(closure.get(ed.getFrom()),closure.get(ed.getTo()));
		return CSL;
	}	

	/** Generates and returns the isomorphic closed set lattice defined on the meet irreducibles set.
	*
	* Each node of this component is replaced  by a node containing its meet irreducibles 
	* successors stored in a closed set. */
	public ConceptLattice meetClosure () {
		ConceptLattice CSL = new ConceptLattice();
		// associates each node to a new closed set of join irreducibles
		TreeSet<Node> Meet = this.meetIrreducibles();		
		TreeMap<Node,Concept> closure = new TreeMap<Node,Concept>();
		Lattice L = new Lattice (this);
		L.transitiveClosure();
		L.reflexiveClosure();
		for (Node to : L.getNodes()) {
			ComparableSet Mx = new ComparableSet();
			for (Node from : L.getSuccessorNodes(to))
				if (Meet.contains(from))
					Mx.add(from);
        			closure.put(to,new Concept(false,Mx));
		}
		// addition of nodes
		for (Node n : this.getNodes()) 
			CSL.addNode(closure.get(n));
		// addition of edges
		for (Edge ed : this.getEdges())
            CSL.addEdge(closure.get(ed.getFrom()),closure.get(ed.getTo()));
		return CSL;
	}	

	/** Generates and returns the isomorphic concept lattice defined on the join and meet 
	* irreducibles sets.
	*
	* Each node of this component is replaced  by a node containing its meet irreducibles 
	* successors and its join irreducibles predecessors stored in a concept. */
	public ConceptLattice irreducibleClosure () {
		ConceptLattice CL = new ConceptLattice();
		// associates each node to a new closed set of join irreducibles
		TreeSet<Node> Meet = this.meetIrreducibles();		
		TreeSet<Node> Join = this.joinIrreducibles();				
		TreeMap<Node,Concept> closure = new TreeMap<Node,Concept>();
		Lattice L = new Lattice (this);
		L.transitiveClosure();
		L.reflexiveClosure();
		for (Node to : L.getNodes()) {
			ComparableSet Jx = new ComparableSet();
			for (Node from : L.getPredecessorNodes(to))
				if (Join.contains(from))
					Jx.add(from);
			ComparableSet Mx = new ComparableSet();
			for (Node from : L.getSuccessorNodes(to))
				if (Meet.contains(from))
					Mx.add(from);		
			closure.put(to,new Concept(Jx,Mx));
		}
		// addition of nodes
		for (Node n : this.getNodes()) 
			CL.addNode(closure.get(n));
		// addition of edges
		for (Edge ed : this.getEdges())
            CL.addEdge(closure.get(ed.getFrom()),closure.get(ed.getTo()));
		return CL;
	}	

	/** Returns the table of the lattice, composed of the join and meet irreducibles nodes.
	*
	* Each attribute of the table is a copy of a join irreducibles node.
	* Each observation of the table is a copy of a meet irreducibles node.
	* An attribute is extent of an observation when its join irreducible node
	* is greather than the meet irreducible node in the lattice.
	*/	
	public Context getTable () { 	
		// generation of attributes 
		TreeSet<Node> join = this.joinIrreducibles();
        //TreeMap<Node,Node> JoinContent = new TreeMap();
		Context T = new Context ();
		for (Node j : join) {
          //  Node nj = new Node (j);
			//JoinContent.put(j,nj);
			T.addToAttributes(j);
        }
		// generation of observations
		TreeSet<Node> meet = this.meetIrreducibles();						
		//TreeMap<Node,Node> MeetContent = new TreeMap();
		for (Node m : meet) {
		//	Node nm = new Node (m);
			T.addToObservations(m);
		//	MeetContent.put(m,nm);
		}
		// generation of extent-intent
		Lattice tmp = new Lattice (this);
		tmp.transitiveClosure();
		for (Node j : join) 
			for (Node m : meet) 
				if (j.equals(m) || tmp.getSuccessorNodes(j).contains(m))
                    T.addExtentIntent (m,j);
					//T.addExtentIntent (MeetContent.get(m),JoinContent.get(j));
		return T;
		}

	/** Returns an IS of the lattice defined on the join irreducibles nodes.
	*
	* Each element of the IS is a copy of a a join irreducibles node.	
	*/	
	public IS getIS () { 	
		// initialization of IS
		TreeSet<Node> join = this.joinIrreducibles();        
		IS Sigma = new IS ();
		for (Node j : join)           
			Sigma.addElement((Comparable)j.getContent());
		// generation of the family of closures
                TreeSet<ComparableSet> F = new TreeSet<ComparableSet> ();
		Lattice L = new Lattice (this);
		ConceptLattice CL = L.joinClosure();
                for (Node n : CL.getNodes()) {
                    Concept c = (Concept) n;                                    
                    F.add(c.setA);
                }
                // rules generation
                for (ComparableSet Jx : F) {
                    for (Node j : join) {
                        ComparableSet P = new ComparableSet();
                        P.add(j.getContent());
                        P.addAll(Jx);                               
                        if (!F.contains(P)) {
                            ComparableSet min = new ComparableSet();
                            min.addAll(F.last());                                                        
                            for (ComparableSet C : F) {                                
                                //System.out.println("min: "+min.getClass()+" -C:"+C.getClass());
                                if (C.containsAll(P) && !P.containsAll(C) && min.containsAll(C))                                   
                                    min = C.copy();
                            }
                                    Rule r = new Rule();
                                    r.addAllToPremise(P);
                                    min.removeAll(P);
                                    r.addAllToConclusion(min);
                                    Sigma.addRule(r);                                                            
                            }                                               
                        }
                    }
                
                
/**		for (Node j : join) 
			for (Node m : meet) 
				if (j.equals(m) || tmp.getSuccessorNodes(j).contains(m))
                    T.addExtentIntent (m,j);
					//T.addExtentIntent (MeetContent.get(m),JoinContent.get(j));
		return T;**/
                Sigma.makeRightMaximal();
                return Sigma;
		}
   /* ------------- DEPENDANCE GRAPH RELATIVE METHODS ------------------ */

   /** Returns the dependance graph of this component.
    *
    * The dependance graph is a condensed representation of a lattice that encodes
    * its minimal generators, and its canonical direct basis.
    *
    * In the dependance graph, nodes are join irreducibles,
    * egdes correspond to the dependance relation between join-irreducibles
     * (j -> j' if and only if there exists a node x in the lattice such that
     * x not greather than j and j', and x v j' > j),
     * and edges are labeled with the smallest subsets X of join-irreducibles such that
     * the join of elements of X corresponds to the node x of the lattice.
     *
    * The dependance graph could has been already computed in the case where this component 
    * has been instancied as the diagramm of the closed set lattice of a closure system 
    * using the static method
    * `static ConceptLattice diagramLattice (ClosureSystem init)`
    * This method implements an adaptation adaptation of Bordat's
     * where the dependance graph is computed while the lattice is generated.
    *
    * However, it is generated in O(nj^3) where n is the number of nodes of the lattice, and
    * j is the number of join-irreducibles of the lattice.
    */
    public DGraph getDependanceGraph () {
        if (this.dependanceGraph!=null) 
            return this.dependanceGraph;        
        this.dependanceGraph = new DGraph();
        // nodes of the dependance graph are join-irreducibles
        TreeSet<Node> J = this.joinIrreducibles();
        for (Node j : J)
            this.dependanceGraph.addNode(j);
        // computes the transitive closure of the join-irreducibles subgraph of this compnent
        DAGraph joinG = this.irreduciblesSubgraph();
        joinG.transitiveClosure();
        // edges of the dependance graph are dependance relation between join-irreducibles
        // they are first valuated by nodes of the lattice        
        for (Node j1 : J) {
            TreeSet<Node> majj1 = this.majorants(j1);            
            for (Node j2 : J)
              if (!j1.equals(j2)) {
                // computes the set S of nodes not greather than j1 and j2
                TreeSet<Node> S = new TreeSet<Node>(this.getNodes());
                S.removeAll(majj1);
                S.removeAll(this.majorants(j2));
                S.remove(j1);
                S.remove(j2);
                for (Node x: S) {
                    // when j2 V x greather than j1 then add a new edge from j1 to J2
                    // or only a new valuation when the edge already exists
                    if (majj1.contains(this.join(j2,x))) {                        
                        Edge ed = this.dependanceGraph.getEdge(j1,j2);
                        if (ed==null) {
                            ed = new Edge (j1,j2,new TreeSet<ComparableSet>());
                            this.dependanceGraph.addEdge(ed);
                            }
                        // add {Jx minus predecessors in joinG of j in Jx} as valuation of edge
                        // from j1 to j2
                        TreeSet<ComparableSet> ValEd = (TreeSet<ComparableSet>)ed.getContent();
                        ComparableSet newValByNode = new ComparableSet (this.joinIrreducibles(x));
                        for (Comparable j : this.joinIrreducibles(x))
                            newValByNode.removeAll(joinG.getPredecessorNodes((Node)j));
                        ComparableSet newVal = new ComparableSet ();
                        for (Object j : newValByNode) {
                            Node n = (Node) j;
                            newVal.add(n.getContent());
                        }
                        ((TreeSet<ComparableSet>)ed.getContent()).add(newVal);
                        // Minimalisation by inclusion of valuations on edge j1->j2
                        ValEd = new TreeSet<ComparableSet>((TreeSet<ComparableSet>)ed.getContent());
                        for (ComparableSet X1 : ValEd) {
                            if (X1.containsAll(newVal) && !newVal.containsAll(X1))
                                ((TreeSet<ComparableSet>)ed.getContent()).remove(X1);
                            if (!X1.containsAll(newVal) && newVal.containsAll(X1))
                                ((TreeSet<ComparableSet>)ed.getContent()).remove(newVal);
                        }
                    }
                }
             }
         }
        // minimalisation of edge's content to get only inclusion-minimal valuation for each edge
        /**for (Edge ed : this.dependanceGraph.getEdges()) {
             TreeSet<ComparableSet> valEd = new TreeSet<ComparableSet>(((TreeSet<ComparableSet>)ed.getContent()));
             for (ComparableSet X1 : valEd)
                 for (ComparableSet X2 : valEd)
                     if (X1.containsAll(X2) && !X2.containsAll(X1))
                         ((TreeSet<ComparableSet>)ed.getContent()).remove(X1);
        }**/
        return this.dependanceGraph;
    }

	/** Returns the canonical direct basis of the lattice.
	*
    * The canonical direct basis is a condensed representation of a lattice encoding
     * by the dependance graph.
     *
     * This canonical direct basis is deduced from the dependance graph of the lattice:
     * for each edge b -> a valuated by a subset X, the rule a+X->b is a rule of the
     * canonical direct basis.
     *
     * If not yet exists, the dependance graph of this component has to be generated
     * by method `getDependanceGraph`.
	*/    
public IS getCanonicalDirectBasis () {
        DGraph ODGraph = this.getDependanceGraph();
        // initialize elements of the IS with nodes of the ODGraph
        IS BCD = new IS();
        for (Node n : ODGraph.getNodes())
            BCD.addElement((Comparable)n.getContent());
        // computes rules of the BCD from edges of the ODGraph		
		for (Edge ed : ODGraph.getEdges()) {
            Node from = ed.getFrom();
            Node to = ed.getTo();
            TreeSet<ComparableSet> L = (TreeSet<ComparableSet>) ed.getContent();                
            for (ComparableSet X : L) {                
                ComparableSet premise = new ComparableSet(X);
                premise.add((Comparable)to.getContent());
                ComparableSet conclusion = new ComparableSet();
                conclusion.add((Comparable)from.getContent());
                BCD.addRule(new Rule(premise,conclusion));
			}
        }
		//BCD.makeLeftMinimal();
        BCD.makeCompact();
        return BCD;
    }

    /** Returns the minimal generators of the lattice.
    *
    * Minimal generators a condensed representation of a lattice encoding
     * by the dependance graph.
     *
     * Minimal generators are premises of the canonical direct basis.
     * that  is deduced from the dependance graph of the lattice.
     *
     * If not yet exists, the dependance graph of this component has to be generated
     * by method `getDependanceGraph`.
	*/
	public TreeSet getMinimalGenerators () {
        IS BCD = this.getCanonicalDirectBasis();
    	TreeSet GenMin = new TreeSet();
        for (Rule r : BCD.getRules())
           	GenMin.add(r.getPremise());
        return GenMin;
    }
        
 /**
 * The ArrowRelation method encodes arrow relations between meet & join-irreductibles of this component.
 * 
 * Let m and b be respectively meet and join irreductibles of a lattice.
 * Recall that m has a unique successor say m+ and j has a unique predecessor say j-, then :
 * 
 * - j "Up Arrow" m (stored has "Up") iff j is not less or equal than m and j is less than m+
 * - j "Down Arrow" m (stored has "Down") iff j is not less or equal than m and j- is less than m
 * - j "Up Down Arrow" m (stored has "UpDown") iff j "Up" m and j "Down" m
 * - j "Cross" m (stored has "Cross") iff j is less or equal than m
 * - j "Circ" m (stored has "Circ") iff neither j "Up" m nor j "Down" m nor j "Cross" m
 *
 * @return DGraph whose :
 * 
 * - Nodes are join or meet irreductibles of the lattice.
 * - Edges content encodes arrows as String "Up", "Down", "UpDown", "Cross", "Circ".
 * 
 * @author Jean-Francois
 */
     public DGraph ArrowRelation () {
        
        /* Nodes are join or meet irreductibles of the lattice. */
        TreeSet<Node> J = new TreeSet<Node>(this.joinIrreducibles());
        TreeSet<Node> M = new TreeSet<Node>(this.meetIrreducibles());
        TreeSet<Node> nodes = new TreeSet<Node>(M);
        nodes.addAll(J);
        
        DGraph DG = new DGraph(nodes);
        
        Lattice LTC=new Lattice(this);
        LTC.transitiveClosure();
        Lattice LTR=new Lattice(this);
        LTR.transitiveReduction();
        Node jminus = new Node();
        Node mplus = new Node();
        String Arrow = "";
        
        /* Content of edges are arrows */
        for (Node j : J){
            for (Node m : M){
                mplus=LTR.getSuccessorNodes(m).first();
                jminus=LTR.getPredecessorNodes(j).first();
                if (LTC.getSuccessorNodes(j).contains(m) || j.equals(m)){
                    Arrow="Cross";
                }else{
                    if (LTC.getSuccessorNodes(jminus).contains(m)){
                        Arrow="Down";
                        if (LTC.getPredecessorNodes(mplus).contains(j)){
                            Arrow="UpDown";
                        }
                    }else{
                        if (LTC.getPredecessorNodes(mplus).contains(j)){
                            Arrow="Up";
                        }else{
                            Arrow="Circ";
                        }
                    }
                }
                DG.addEdge(m, j, Arrow);
            } 
        }
        return DG;
    }
}// end of Lattice
