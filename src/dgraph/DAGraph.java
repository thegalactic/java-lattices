package dgraph;

/*
 * DAGraph.java
 *
 * last update on March 2010
 *
 */

//import lattice.lattice.Node;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import lattice.Concept;
import lattice.ConceptLattice;
/**
 * This class extends the representation of a directed graph given by class 
 * `DGraph` for directed acyclic graph (DAG). 
 *
 * The main property of a directed acyclic graph is to be a partially ordered set (poset) when
 * transitively closed, and a Hasse diagram when transitively reduced.
 *
 * This property is not ensured for components of this class because it would require a
 * checking treatment over the graph whenever a new edge or node is added.
 * However, this property can be explicitely ckecked using method
 * `boolean isAcyclic ()`.
 *
 * This class provides methods implementing classical operation on a directed acyclic graph:
 * minorants and majorants, filter and ideal, transitive reduction, ideal lattice, ...
 *
 * This class also provides a static method randomly generating a directed acyclic graph,
 * and a static method generating the graph of divisors.
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
public class DAGraph extends DGraph {

  	/** Constructs a new DAG with an empty set of node. **/
   public DAGraph () { super (); }
	/** Constructs this component with the specified set of nodes, 
	* and empty treemap of successors and predecessors 	
	* @param S the set of nodes **/
   public DAGraph (TreeSet<Node> S) { super (S); }
  	/** Constructs this component as a copy of the specified directed graph
	* when it is acyclic. An empty ;
	*
	* Acyclic property is checked for the specified DAG.
	* When not verified, this component is construct with an empty set of nodes. 	
	* @param G the DAG to be copied */
	public DAGraph (DGraph G) {		
		super (G);
		this.reflexiveReduction();
		if (!this.isAcyclic()) { 
			this.nodes=new TreeSet(); 
			this.succ=new TreeMap<Node,TreeSet<Edge>>();
			this.pred=new TreeMap<Node,TreeSet<Edge>>();
			}
		}
			
	/* --------------- DAG HANDLING METHODS ------------ */	

   /** Returns the minimal element of this component **/
   public TreeSet<Node> min () {
		return this.sinks(); }
   /** Returns the maximal element of this component **/
   public TreeSet<Node> max () {
		return this.wells(); }

	/**
	 * Returns the set of majorants of the specified node.
	 *
	 * Majorants of a node are its successors in the transitive closure **/
	public TreeSet<Node> majorants (Node N) {
		DAGraph G = new DAGraph (this);
		G.transitiveClosure();		
		return G.getNodesSucc(N);
	}
	/** Returns the set of minorants of the specified node.
	 *
	 * Minorants of a node are its predecessors in the transitive closure **/
	public TreeSet<Node> minorants (Node N) {
		DAGraph G = new DAGraph (this);
		G.transitiveClosure();
		return G.getNodesPred(N);
	}
	/** Returns the subgraph induced by the specified node and its successors
	* in the transitive closure */
	public DAGraph filter (Node N) {
		TreeSet<Node> S = this.majorants(N);
		S.add(N);
		return this.subgraphByNodes(S);
	}
	/** Returns the subgraph induced by the specified node and its predecessors
	* in the transitive closure */
	public DAGraph ideal (Node N) {
		TreeSet<Node> S = this.minorants(N);
		S.add(N);
		return this.subgraphByNodes(S);
	}

	/** Returns the subgraph of this component induced by the specified set of nodes
	 *
	 * The subgraph only contains nodes of the specified set that also are in this component. 
	 */
	public DAGraph subgraphByNodes (TreeSet<Node> S) {
		DGraph tmp = new DGraph (this);
		tmp.transitiveClosure();
		DGraph sub = tmp.subgraphByNodes(S);
		DAGraph sub2 = new DAGraph(sub);
		sub2.transitiveReduction();
		return sub2;
		}


	/* --------------- DAG TREATMENT METHODS ------------ */	
	
   /** Computes the transitive reduction of this component.
    *
	* The transitive reduction is not uniquely defined only when the acyclic property 
	* is verified. In this case, it corresponds to the Hasse diagram of the DAG.
	*
	* This method is an implementation of the Goralcikova-Koubeck 
	* algorithm that can also compute the transitive closure. 
	* This tratment is performed in O(n+nm_r+nm_c), 
	* where n corresponds to the number of nodes, 
	* m_r to the numer of edges in the transitive closure, 
	* and m_r the number of edges in the transitive reduction.
	* @return the number of added edges
	**/
   public int transitiveReduction () {

		// copy this component in a new DAG G

		DAGraph G = new DAGraph(this);
		G.reflexiveReduction();
		// initalize this component with no edges
		this.succ = new TreeMap();
		for (Node n : this.getNodes())
			this.succ.put(n,new TreeSet());
		this.pred = new TreeMap();
		for (Node n : this.getNodes())
			this.pred.put(n,new TreeSet());		
		int nb=0;
		// mark each node to false
		TreeMap mark = new TreeMap ();
		for (Node n : G.getNodes()) 
			mark.put (n, new Boolean(false));					
		// treatment of nodes according to a topological sort
		ArrayList<Node> sort = G.topologicalSort();
		for (Node x : sort) {
			TreeSet<Node> S = new TreeSet(G.getNodesSucc(x));
			while (!S.isEmpty()) {	
				// compute the smallest successor y of x according to the topological sort
				int i=0;
				while (!S.contains(sort.get(i))) i++;
				Node y = sort.get(i);	
				// when y is not not marked, x->y is a reduced edge	
				if ((y!=null) && !((Boolean)mark.get(y)).booleanValue()) {
					this.addEdge(x,y);
					G.addEdge(x,y);
				}
				for (Node z : G.getNodesSucc(y)) {
					// treatment of z when not marked
					if (!((Boolean)mark.get(z)).booleanValue()) {
						mark.remove(z);					
						mark.put(z,new Boolean(true));
						G.addEdge(x,z);															
						nb++;														
						S.add(z); 
					}
				}					
				S.remove(y);	
			}			
			for (Node y : G.getNodesSucc(x)) {
				mark.remove(y);
				mark.put(y,new Boolean(false));								
			}
		}					
		return nb;
    }

   /** Computes the transitive closure of this component.
    *
	* This method overlaps the computation of the transitive closure for directed graph 
	* in class `DGraph` with an implementation of the Goralcikova-Koubeck 
	* algorithm dedicated to acyclic directed graph. This algorithm can also compute the 
	* transitive reduction of a directed acyclic graph.
	*
	* This treatment is performed in O(n+nm_r+nm_c), where n corresponds to the number of nodes, 
	* m_r to the numer of edges in the transitive closure, 
	* and m_r the number of edges in the transitive reduction.
	* @return the number of added edges
	**/
   public int transitiveClosure () {
		int nb=0;
		// mark each node to false
		TreeMap mark = new TreeMap ();
		for (Node n : this.getNodes()) 
			mark.put (n, new Boolean(false));					
		// treatment of nodes according to a topological sort
		ArrayList<Node> sort = this.topologicalSort();
		for (Node x : sort) {
			TreeSet<Node> S = new TreeSet(this.getNodesSucc(x));
			while (!S.isEmpty()) {	
				// compute the smallest successor y of x according to the topological sort
				int i=0; 
                do i++; while (!S.contains(sort.get(i)));				
				Node y = sort.get(i);	
				for (Node z : this.getNodesSucc(y)) {
					// treatment of z when not marked
					if (!((Boolean)mark.get(z)).booleanValue()) {
						mark.remove(z);					
						mark.put(z,new Boolean(true));															
						this.addEdge(x,z);
						nb++;														
						S.add(z); 
					}
				}					
				S.remove(y);	
			}			
			for (Node y : this.getNodesSucc(x)) {
				mark.remove(y);
				mark.put(y,new Boolean(false));								
			}
		}					
		return nb;
    }
			 
	/** generate the lattice composed of all the antichains of thic omponent 
	* ordered with the inclusion relation. 
	* This treatment is performed in O(??) by implementation of Nourine algorithm 
	* that consists in a sequence of doubling intervals of nodes. 
	**/
   public ConceptLattice idealsLattice () {
		if (!this.isAcyclic()) return null;
		// initialize the poset of ideals with the emptyset 
		ConceptLattice GI = new ConceptLattice();
		int id = 1;
		Concept E = new Concept(true,false);
		GI.addNode(E);
		// travel on G according to a topological sort
		DAGraph G = new DAGraph(this);
		G.transitiveClosure();
		// treatment of nodes according to a topological sort
		ArrayList<Node> sort = G.topologicalSort();
		for (Node x : sort) {
		    // computation of Jx
	    	TreeSet Jxmoins = new TreeSet(G.getNodesPred(x));
	    	// storage of new ideals in a set 
	    	TreeSet<Concept> toAdd = new TreeSet();
	    	for (Node J1 : GI.getNodes()) 
				if (((Concept)J1).containsAllInA(Jxmoins)) {
			  		Concept newJ = new Concept(true,false);
			  		newJ.addAllToA(((TreeSet)((Concept)J1).getSetA()));
		 	  		newJ.addToA(x);
		  	  		toAdd.add(newJ);
				}
	    // addition of the new ideals in GI
	    for (Node newJ : toAdd) 
			GI.addNode(newJ);
		}
	// computation of the inclusion relaton
	for (Node N1 : GI.getNodes())
	    for (Node N2 : GI.getNodes()) {
			if (((Concept)N1).containsAllInA(((Concept)N2).getSetA()))
		    	GI.addEdge(N2,N1);
	    }
	GI.transitiveReduction();	 
	return GI;
   } 

	/* ----------- STATIC GENERATION METHODS ------------- */

	/** Generates the directed asyclic graph (DAG) of divisors for integers 
	* included betwwen 2 and the specified value. 
	* In this DAG, nodes corresponds to the intergers, 
	* and there is an edge between two integers if and only if the second one
	* is divisible by the first one.
	* @param nb the maximal integer 
	*/	
    public static DAGraph divDAG(int nb) {
		DAGraph G = new DAGraph();		
		// addition of nodes
		for (int i=2; i<=nb; i++) {
		    Node N = new Node(new Integer(i));
		    G.addNode (N);
			}				
		// addition of edges
		for (Node from : G.getNodes()) 
			for (Node to : G.getNodes()) {
				int v1 = ((Integer) from.content).intValue();
				int v2 = ((Integer) to.content).intValue();
			   if ((v1<v2) && (v2%v1==0)) 
					G.addEdge (from, to);
			}
		return G;
    	}
		
    /** Generates a random directed and acyclic graph (DAG) of nb nodes
	 @param nb the number of nodes of the generated graph 	 
	  **/
    public static DAGraph randomDAGraph(int nb) {
		DAGraph G = new DAGraph();
		// addition of Nodes
		for (int i=1; i<nb; i++) {
			Node N = new Node(new Integer(i));
			G.addNode (N);
			}
		// addition of edges
		for (Node from : G.getNodes()) 
			for (Node to : G.getNodes()) 
				if (from.compareTo(to)==1) {// test to avoid cycles		
			    int choice = (int) Math.rint (10*Math.random());
			    if (choice > 5) 	G.addEdge(from, to);
				}
		return G;
 	   }
}// end of DAGraph
