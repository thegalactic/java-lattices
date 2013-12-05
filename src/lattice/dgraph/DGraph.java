package lattice.dgraph;

/*
 * DGraph.java
 *
 * last update on March 2010
 *
 */
//package lattice;
//import lattice.lattice.Node;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
/**
 * This class gives a standard representation for a directed graph
 * by sets of successors and predecessors.<p>
 *
 * A directed graph is composed of a treeset of node, defined by class <code>Node</code> ;
 * a treemap of successors that associates to each node a treeset of successors ;
 * and a treemap of predecessors that associates to each node a treeset of predecessors.
 * <p>
 * This class provides methods implementing classical operation on a directed graph:
 * sinks, wells, subgraph, depth first search, reflexive closure and reduction,
 * transitive closure, strongly connected components, ...
 * <p>
 * This class also provides a static method randomly generating a directed graph.
 * <p>
 * <img src="..\..\..\images\lgpl.png" height="20" alt="lgpl"/>
 * Copyright 2010 Karell Bertet<p>
 * This file is part of lattice.
 * lattice is free package: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with lattice.  If not, see <a href="http://www.gnu.org/licenses/" target="_blank">license</a>
 *
 * @author Karell Bertet
 * @version 2010
 */
public class DGraph {

	/* ------------- FIELD ------------------ */	
	
	/** A set of elements **/
	protected TreeSet<Node> nodes;
	/** A map to associate a set of successors to each node **/
	protected TreeMap<Node,TreeSet<Edge>> succ;
	/** A map to associate a set of predecessors to each node **/
	protected TreeMap<Node,TreeSet<Edge>> pred;

	/* ------------- CONSTRUCTORS ------------------ */	
	
  	/** Constructs a new directed graph with an empty set of node.**/
   public DGraph () {
		this.nodes = new TreeSet<Node> ();	
		this.succ = new TreeMap<Node,TreeSet<Edge>>();
		this.pred = new TreeMap<Node,TreeSet<Edge>>();
		}
  	/** Constructs a new directed graph from the specified set of nodes. 
	* Successors and predessors of each nodes are initialised by an empty set. **/
   public DGraph (TreeSet<Node> S) {
		this.nodes = new TreeSet<Node> (S);
		this.succ = new TreeMap<Node,TreeSet<Edge>>();
		for (Node n : this.getNodes())
			this.succ.put(n,new TreeSet<Edge>());
		this.pred = new TreeMap<Node,TreeSet<Edge>>();
		for (Node n : this.getNodes())
			this.pred.put(n,new TreeSet<Edge>());
		}
	/**  Constructs this component as a copy of the specified directed graph.
	* @param G the directed graph to be copied */
	public DGraph (DGraph G) {
		this.nodes = new TreeSet<Node> (G.getNodes());
		this.succ = new TreeMap<Node,TreeSet<Edge>>();
		this.pred = new TreeMap<Node,TreeSet<Edge>>();
		for (Node n : G.getNodes()) {
			this.succ.put(n, new TreeSet<Edge>(G.getEdgesSucc(n)));
			this.pred.put(n, new TreeSet<Edge>(G.getEdgesPred(n)));
		}
	}	
		
	/* ----------- STATIC GENERATION METHODS ------------- */

    /** Generates a random directed graph of nb nodes
	 @param nb the number of nodes of the generated graph 
	 **/
    public static DGraph randomDGraph (int nb) {
		DGraph G = new DGraph();
		// addition of Nodes
		for (int i=1; i<=nb; i++) {
			Node N = new Node(new Integer(i));
			G.addNode (N);
			}
		// addition of edges
		for (Node from : G.getNodes()) 
			for (Node to : G.getNodes()) {
			    int choice = (int) Math.rint (10*Math.random());
			    if (choice > 5) 	G.addEdge(from, to);
				}
		return G;
 	   }

	/* --------------- ACCESSOR AND OVERLAPPING METHODS ------------ */	


    /** Returns a copy of this component composed of a copy of each node and each edge **/
    public DGraph copy() {
        System.out.println("clone dans DGraph");
        DGraph G = new DGraph();
        TreeMap<Node,Node> copy = new TreeMap<Node,Node> ();
        for (Node n : this.getNodes()) {
            Node n2 = n.copy();
            copy.put(n,n2);
            G.addNode(n2);
        }
        for (Edge ed : this.getEdges()) {
            Edge ed2 = new Edge (copy.get(ed.from()), copy.get(ed.to()), ed.content());
            G.addEdge (ed);
        }
        return G;
    }

    /** Returns the set of nodes of this component */
    public TreeSet<Node> getNodes () {
		return this.nodes; }
    /** Returns the set of edges of this component */
    public TreeSet<Edge> getEdges () {
        TreeSet X = new TreeSet();
        for (Node n : this.getNodes())
            X.addAll(this.getEdgesSucc(n));
		return X; 
    }
    /** Returns the set of edges successors of the specified node  */
    public TreeSet<Edge> getEdgesSucc (Node N) {
		return (TreeSet<Edge>)this.succ.get(N);}
    /** Returns the set of edges successors of the specified node  */
    public TreeSet<Edge> getEdgesPred (Node N) {
		return (TreeSet<Edge>)this.pred.get(N);}

    /** Returns the set of nodes successors of the specified node  */
    public TreeSet<Node> getNodesSucc (Node N) {
	   TreeSet<Edge> edgesSucc = this.getEdgesSucc(N);
       TreeSet<Node> nodesSucc = new TreeSet<Node> ();
       for (Edge ed : edgesSucc)
           nodesSucc.add(ed.to());
       return nodesSucc;
    }
    /** Returns the set of nodes successors of the specified node  */
    public TreeSet<Node> getNodesPred (Node N) {        
        TreeSet<Edge> edgesPred = this.getEdgesPred(N);
        TreeSet<Node> nodesPred = new TreeSet<Node> ();
        for (Edge ed : edgesPred)
            nodesPred.add(ed.from());
        return nodesPred;
    }
    /** Returns, if it exists, the edge between node from and node to */
    public Edge getEdge (Node from, Node to) {
        if (this.containsEdge(from,to))
            for (Edge ed : this.getEdgesSucc(from))
                if (ed.to().equals(to))
                    return ed;
		return null;
    }
	 /** Returns the node that is equal to the specified one */
	 public Node getNode (Object o) {
	 	for (Node n : this.getNodes()) 
			if (n.equals(o))
				return n;
		return null; }
	 /** Returns the node whose content is equal to the specified one */
	 public Node getNodeByContent (Object o) {
	 	for (Node n : this.getNodes()) 
			if (n.content.equals(o))
				return n;
		return null; }
	 /** Returns the node whose ident is equal to the specified one */
	 public Node getNodeByIdent (int id) {
	 	for (Node n : this.getNodes()) 
			if (n.ident == id)
				return n;
		return null; }
					
    /** Returns the number of nodes of this component **/
    public int nbNodes () {
		return this.getNodes().size(); }
    /** Returns the number of edges of this component **/		
    public int nbEdges () {
	 	int nb=0;
		for (Node n : this.getNodes())
            nb+=this.getEdgesSucc(n).size();
		return nb;	
		}	
		
	/** Returns a String representation of this component */	
    public String toString () {
		StringBuffer nodes  = new StringBuffer();
                nodes.append(this.nbNodes()).append(" Nodes: {");
		StringBuffer edges = new StringBuffer ();
                edges.append(this.nbEdges()).append(" Edges: {");
		for (Node from : this.getNodes()) 
			nodes.append(from.toString()+",");
		for (Edge ed : this.getEdges())
			edges.append(ed.toString()+",");
		nodes.append("}\n").append(edges).append("}\n");
                return nodes.toString();
    	}
	/** Write the dot description of this component in a file  which name is specified 
	* @param filename the name of the file 
	*/
    public void toDot (String filename)  {
	try {
		FileOutputStream fich = new FileOutputStream(filename);
		DataOutputStream out = new DataOutputStream(fich);		
		out.writeBytes("digraph G {\n");
                out.writeBytes("Graph [rankdir=BT]\n");
		StringBuffer nodes  = new StringBuffer();
		StringBuffer edges = new StringBuffer ();
		for (Node from : this.getNodes())
			nodes.append(from.toDot()).append("\n");
                for (Edge ed : this.getEdges())
                        edges.append(ed.toDot()).append("\n");
		out.writeBytes(nodes.toString());
                out.writeBytes(edges.toString());
		out.writeBytes("}");
		out.close();
 	   }
		catch (Exception e) { e.printStackTrace(); }
	}			

	/* --------------- NODES AND EDGES MODIFICATION METHODS ------------ */	

    /** Checks if the specified node belong to this component **/
    public boolean containsNode (Node N) { 
		return this.getNodes().contains(N);}
    /** Adds the specified node to the set of node of this component **/
    public boolean addNode (Node N) {     
		if (!this.getNodes().contains(N)) {                    
			this.getNodes().add(N);
			this.succ.put(N, new TreeSet<Edge>());
			this.pred.put(N, new TreeSet<Edge>());
			return true;
			}
		return false;}		
    /** Removes the specified node from this component. **/
    public boolean removeNode (Node n) {
        if (this.getNodes().contains(n)) {            
            // Remove the edges (n,to) with key n in succ, and key to in pred             
            for (Edge ed : this.getEdgesSucc(n)) {
                if (ed.to().compareTo(n)!=0) {                        
                    TreeSet<Edge> predN = (TreeSet<Edge>) this.getEdgesPred(ed.to()).clone();                    
                    for (Edge ed2 : predN) 
                        if (ed2.from().compareTo(n)==0)
                            this.pred.get(ed.to()).remove(ed2);
                }
                this.succ.remove(n); }                        
            // Remove the edges (from,n) with key n in pred, and key from in succ              
            for (Edge ed : this.getEdgesPred(n)) {
                if (ed.from().compareTo(n)!=0) {
                    TreeSet<Edge> succN = (TreeSet<Edge>) this.getEdgesSucc(ed.from()).clone();
                    for (Edge ed2 : succN) 
                        if (ed2.to().compareTo(n)==0)
                            this.succ.get(ed.from()).remove(ed2);                
                this.pred.remove(n);  }  
            }
            // Remove node n
            this.getNodes().remove(n);
            return true;
	}
    return false;}		
                    
    /** Removes the specified set of node from this component. **/
    public boolean removeAllNodes (TreeSet<Node> X) {
	 	boolean all = true;
	 	for (Node n : X)
			if (!this.removeNode(n)) 
				all=false;
		return all;}			
    /** Checks if there exists an edge between the two specified nodes.
	 * @param from the node origine of the edge
	 * @param to the node destination of the edge **/
    public boolean containsEdge (Node from, Node to) {
		return this.containsNode(from) && this.containsNode(to) && 
			(this.getNodesSucc(from)).contains(to) &&
			(this.getNodesPred(to)).contains(from); }
    /** Checks if the specified edge belong to this component.
    ** @param ed the dege to be checked **/
    public boolean containsEdge (Edge ed) {
		return this.containsNode(ed.from()) && this.containsNode(ed.to()) &&
			(this.getEdgesSucc(ed.from())).contains(ed) &&
			(this.getEdgesPred(ed.to())).contains(ed); }
    /** Adds an edge between the specified nodes to this component:
	 * <code>to</code> is added as a successor of <code>from</code>.
	 * If the case where specified nodes don't belongs to the node set, 
	 * then the edge will not be added.
	 * @param from the node origine of the edge
	 * @param to the node destination of the edge **/	 
    public boolean addEdge (Node from, Node to) {       
	 	if (this.containsNode(from) && this.containsNode(to)) {
            Edge ed = new Edge(from,to);
			this.getEdgesSucc(from).add(ed);
			this.getEdgesPred(to).add(ed);
			return true;
			}
		return false; }
    /** Adds the specified edge to this component in the successors of ed.from() and in the
     * predecessors of ed.to(). 
	 * If the case where nodes to and from of this edges don't belongs to the node set, 
	 * then the edge will not be added.
	 * @param ed the dege to be added **/	 
    public boolean addEdge (Edge ed) {       
	 	if (this.containsNode(ed.from()) && this.containsNode(ed.to())) {            
			this.getEdgesSucc(ed.from()).add(ed);
			this.getEdgesPred(ed.to()).add(ed);			
			return true;
			}
		return false; }
     /** Removes from this component the edge between the specified node.
	 * <code>to</code> is removed from the successors of <code>from</code>
	 * and <code>to</code> is removed from the predecessors of <code>from</code>.
	 * @param from the node origine of the edge
	 * @param to the node destination of the edge **/
    public boolean removeEdge (Node from, Node to) {
	 	if (this.containsEdge(from,to)) {
            Edge ed = new Edge(from, to);
			this.getEdgesSucc(from).remove(ed);
			this.getEdgesPred(to).remove(ed);
			return true;
			}
		return false; }
     /** Removes from this component the specified edge from the successors od ed.from()
	 * and  from the predecessors of ed.to().
	 * @param ed the edge to be removed. **/
    public boolean removeEdge (Edge ed) {
	 	if (this.containsEdge(ed)) {
			this.getEdgesSucc(ed.from()).remove(ed);
			this.getEdgesPred(ed.to()).remove(ed);
			return true;
			}
		return false; }

	/* --------------- ACYCLIC CHECKING METHODS ------------ */	
		
   /** Check if this component is acyclic **/
   public boolean isAcyclic () {
		ArrayList T = this.topologicalSort();
		return (T.size() == this.nbNodes());
	}
 
   /** Returns a topological sort of the node of this component. <p>
	* This topological sort is a sort on all the nodes according to their successors. 
	* If the graph is not acyclic, some nodes don't belong to the sort. 
	* This treatment is performed in O(n+m), where n corresponds to the number of nodes, 
	* and m corresponds to the number of edges. 
	**/
   public ArrayList<Node> topologicalSort () {
		TreeSet<Node> sources = this.sinks();
		// initialize a map with the number of predecessors (value) for each node (key);		
		TreeMap nbpred = new TreeMap ();
		for (Node n : this.getNodes()) 
			nbpred.put (n, new Integer(this.getNodesPred(n).size()));
		ArrayList sort = new ArrayList();
		while (sources.size()!=0)	{
			// random choice of a node in the set min 
			int choice = (int)(Math.random()*(sources.size()-1));
			int nb=0;
			Node Nx = null;
			for (Node N : sources) {
				if (nb==choice) Nx = N;
				nb++;			
				} 
			sources.remove(Nx);
			sort.add(Nx);
			// updating of the set min by considering the successors of Nx 
			for (Node Ny : this.getNodesSucc(Nx)) {
				int nbp = ((Integer)nbpred.remove(Ny)).intValue()-1;
				nbpred.put (Ny, new Integer(nbp));
				if (nbp==0)
					sources.add(Ny);
		   	}
			}	
			return sort;		
 		}  

	/* --------------- GRAPH HANDLING METHODS ------------ */	

   /** Returns the sinks of this component **/
   public TreeSet<Node> sinks () {
		TreeSet<Node> min = new TreeSet<Node> ();
		for (Node N : this.getNodes()) 
			if (this.getEdgesPred(N).isEmpty())
				min.add(N);
		return min;
    	}
		
   /** Returns the wells of this component **/
   public TreeSet<Node> wells () {
		TreeSet<Node> max = new TreeSet<Node> ();
		for (Node N : this.getNodes()) 
			if (this.getEdgesSucc(N).isEmpty())
				max.add(N);
		return max;
    	}

	/** Returns the subgraph of this component induced by the specified set of nodes <p>
	* The subgraph only contains nodes of the specified set that also are in this component. 
	* **/
	public DGraph subgraphByNodes (TreeSet<Node> S) {
		DGraph G = new DGraph ();
		// addition of nodes in the subgraph
		for (Node n : S)
			if (this.containsNode(n)) G.addNode(n);	
		// addition of edges in the subgraph		
		for (Edge ed : this.getEdges())
			if (G.containsNode(ed.to()))
				G.addEdge(ed);
        return G;
		}

    /** Returns the subgraph of this component induced by the specified set of edges <p>
	 *  The subgraph contains all nodes of this components, and
     * only edges of the specified set that also are in this component.
	 **/
	public DGraph subgraphByEdges (TreeSet<Edge> S) {
		DGraph G = new DGraph ();
		// addition of all nodes in the subgraph
		for (Node n : this.getNodes())
			G.addNode(n);	
		// addition of specified edges in the subgraph		
		for (Edge ed : S)
			if (this.containsEdge(ed))
				G.addEdge(ed);
        return G;
		}
	/** Replaces this component by its complementary graph.
	* There is an edge between to nodes in the complementary graph when 
	* there is no edges between the nodes in this component. 
	* **/
	public void complementary () {		
		for (Node from : this.getNodes()) {
			TreeSet<Node> newSucc = new TreeSet(this.getNodes());
			newSucc.removeAll (this.getNodesSucc(from));
			TreeSet<Node>oldSucc = new TreeSet(this.getNodesSucc(from));
			for (Node to : oldSucc)
				this.removeEdge(from,to);
			for (Node to : newSucc)
				this.addEdge(from,to);
		}	
	}	
	
	/* --------------- GRAPH TREATMENT METHODS ------------ */	

   /** Computes the reflexive reduction of this component  
	* @return the number of removed edges
	**/
   public int reflexiveReduction () {
		int nb = 0;
		for (Node N : this.getNodes()) 
			if (this.containsEdge(N,N)) {
				nb++;
				this.removeEdge(N,N);
			}	
		return nb;		
   }
	
   /** Computes the reflexive closure of this component  
	* @return the number of added edges
	**/
   public int reflexiveClosure () {
		int nb = 0;
		for (Node N : this.getNodes()) 
			if (!this.containsEdge(N,N)) {
				nb++;
				this.addEdge(N,N);
	    	}
	return nb;
   }
		 
   /** Computes the transitive closure of this component.<p>
	* This treatment is performed in O(nm+m_c), where n corresponds to the number of nodes,
	* m to the number of edges, and m_c to the number of edges in the closure. 
	* This treatment improves the Roy-Warshall algorithm that directly implements 
	* the definition in O(logm n^3). 
	* This treatment is overlapped in class <code>DAGrapg</code> 
	* with a more efficient algorithm dedicated to directed acyclic graph.
	* @return the number of removed edges
	**/
   public int transitiveClosure () {
		int nb=0;
		// mark each node to false
		TreeMap mark = new TreeMap ();
		for (Node n : this.getNodes()) 
			mark.put (n, new Boolean(false));					
		// treatment of nodes 
		for (Node x : this.getNodes()) {
			ArrayList<Node> F = new ArrayList();
			F.add(x);
			while (!F.isEmpty()) {
				Node y = F.get(0);
				F.remove(y);
				for (Node z : this.getNodesSucc(y)) {
					// treatment of y when not marked
					if (!((Boolean)mark.get(z)).booleanValue()) {
						mark.remove(z);
						mark.put(z,new Boolean(true));
						this.addEdge(x,z);
						nb++;
						F.add(z);
					}
				}
			}
			for (Node y : this.getNodesSucc(x)) {
				mark.remove(y);
				mark.put(y,new Boolean(false));								
			}
		}					
		return nb;
    }
	 
	 /**Returns a two cells array containing the first visited sort on the nodes, 
	 * and the last visited sort on the nodes, by a depth first traverse issued from 
	 * the specified node.
	 */
	 public ArrayList<Node>[] depthFirstSearch (Node source, 
	 								TreeSet<Node> visited, ArrayList<Node> sort) {
		ArrayList<Node> first = new ArrayList();							
		ArrayList<Node> last = new ArrayList();											
		first.add(source);
		visited.add(source);
		ArrayList<Node>[] Tvisited = new ArrayList[2];
		if (sort!=null) {
			// search according to a sort		
			for (Node n : sort) 
				if (this.getNodesSucc(source).contains(n) && !visited.contains(n)) {
					Tvisited = this.depthFirstSearch(n, visited, sort);
					visited.addAll(Tvisited[0]);
					first.addAll(Tvisited[0]);
					last.addAll(Tvisited[1]);
				}
			}	
			// classical search	
		else {
			for (Node n2 : this.getNodesSucc(source))
				if (!visited.contains(n2)) {
					Tvisited = this.depthFirstSearch(n2, visited, sort);
					visited.addAll(Tvisited[0]);					
					first.addAll(Tvisited[0]);
					last.addAll(Tvisited[1]);
				}	
			}	
		last.add(source);
		Tvisited[0] = first;
		Tvisited[1] = last;
		return Tvisited;
	}	
	 /**Returns a two cells array containing the first visited sort on the nodes, 
	 * and the last visited sort on the nodes, by a depth first search
	 */
	 public ArrayList<Node>[] depthFirstSearch () {
		TreeSet<Node> visited = new TreeSet();
		ArrayList<Node>[] Tvisited = new ArrayList[2];		
	 	ArrayList first = new ArrayList();
	 	ArrayList last = new ArrayList();
		for (Node n : this.getNodes()) 
			if (!visited.contains(n)) {
				Tvisited = this.depthFirstSearch (n, visited, null);
				visited.addAll(Tvisited[0]);				
				first.addAll(Tvisited[0]);
				last.addAll(Tvisited[1]);
				}
		Tvisited[0] = first;
		Tvisited[1] = last;
		return Tvisited;
	}	
	
	/** Transposes this component by replacing for each node
	* its successor set by its predecessor set, and its predecessor set 
	* by its successor set.
	*/ 
	public void transpose () {
        DGraph tmp = new DGraph (this);		
        for (Edge ed : tmp.getEdges()) {
             this.removeEdge(ed);
             this.addEdge(new Edge(ed.to(), ed.from(), ed.content()));
         }
	}
	
	/** Returns the directed acyclic graph where each node corresponds to a 
	* strongly connected component (SCC) of this component stored in a TreeSet of nodes.
	* When two nodes in two different SCC are in relation, the same is for the SCC
    * they belongs to.
     */
	public DAGraph stronglyConnectedComponent () {
		DAGraph CC = new DAGraph();		
		// first depth first search
        DGraph tmp = new DGraph(this);
        tmp.transitiveClosure();
        ArrayList<Node> last = tmp.depthFirstSearch()[1];
        // transposition of the graph
		DGraph Gt = new DGraph (this);
		Gt.transpose();		
		// sort nodes according to the reverse last sort
		ArrayList<Node> sort = new ArrayList();
		Object [] T = last.toArray();
		for (int i=T.length-1 ; i>=0 ; i--) 
			sort.add((Node)T[i]);
		// second depth first search according to sort
		TreeSet<Node> visited = new TreeSet();		
		for (Node n : sort) 
			if (!visited.contains(n)) {
				last = Gt.depthFirstSearch(n, visited, sort)[1];
				visited.addAll(last);				
				TreeSet sCC = new TreeSet(last);
				// a strongly connected component is generated
				CC.addNode(new Node(sCC));	// addition of			
			}
		// edges between strongly connected component	
		for (Node CCfrom : CC.getNodes()) 
			for (Node CCto : CC.getNodes()) 
				for (Node from : (TreeSet<Node>)CCfrom.content)
					for (Node to : ((TreeSet<Node>)CCto.content))	
						if (tmp.getNodesSucc(from).contains(to))
							CC.addEdge(CCfrom,CCto);
		CC.reflexiveReduction();															 							
		return CC;
	}	
}// end of DGraph