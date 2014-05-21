package lattice;

/*
 * Lattice.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.TreeMap;
import java.util.TreeSet;
import dgraph.DAGraph;
import dgraph.DGraph;
import dgraph.Edge;
import dgraph.Node;
import java.util.ArrayList;

/**
 * This class extends class {@link dgraph.DAGraph} to provide specific methods to manipulate a lattice.
 *
 * A lattice is a directed acyclic graph ({@link dgraph.DAGraph}) containing particular nodes denoted join and meet\
 * (a dag is a lattice if and only if each pair of nodes admits a join and a meet).
 *
 * Since checking the lattice property is very time-expensive, this property is not ensured
 * for components of this class. However, it can be explicitely ckecked using
 * method {@link #isLattice}.
 *
 * This class provides methods implementing classical operation on a lattice
 * issued from join and meet operation and irreducibles elements,
 * and methods that returns a condensed representation of a lattice.
 *
 * A well-known condensed representation of a lattice is its table, obtained by method
 * {@link #getTable},
 * where join-irreducibles are in column and meet-irreducibles are in rown.
 *
 * Another condensed representation is its dependency graph obtained by method
 * {@link #getDependencyGraph}.
 *
 * The dependency graph is a directed graph where nodes are join-irreducibles,
 * edges corresponds to the dependency relation between two join-irreducibles
 * and are valuated by a family of subsets of irreducibles.
 *
 * The dependency graph encodes two other condensed representation of a lattice
 * that are its minimal generators and its canonical direct basis
 * that can be obtained from the dependency graph by methods
 * {@link #getMinimalGenerators} and {@link #getCanonicalDirectBasis}.
 *
 * ![Lattice](Lattice.png)
 *
 * @uml Lattice.png
 * !include src/dgraph/DAGraph.iuml
 * !include src/dgraph/DGraph.iuml
 * !include src/dgraph/Edge.iuml
 * !include src/dgraph/Node.iuml
 * !include src/lattice/Lattice.iuml
 *
 * hide members
 * show Lattice members
 * class Lattice #LightCyan
 * title Lattice UML graph
 *
 * @todo  remove useless comments
 */
public class Lattice extends DAGraph {

    /* ------------- FIELDS ------------------ */

    /**
     * The dependency graph of a lattice.
     *
     * Nodes are join irreducibles elements,
     * and edges correspond to the dependency relation of the lattice
     * (j -> j' if and only if there exists a node x in the lattice such that
     * x not greather than j and j', and x v j' > j),
     * and edges are labeled with the smallest subsets X of join-irreducibles such that
     * the join of elements of X corresponds to the node x of the lattice.
     */
    private DGraph dependencyGraph = null;

    /* ------------- CONSTRUCTORS ------------------ */

    /**
     * Constructs this component with an empty set of nodes.
     */
    public Lattice() {
        super();
    }

    /**
     * Constructs this component with the specified set of nodes, and empty treemap of successors and predecessors.
     *
     * @param   set  the set of nodes
     */
    public Lattice(TreeSet<Node> set) {
        super(set);
    }

    /**
     * Constructs this component as a copy of the specified lattice.
     *
     * Lattice property is checked for the specified lattice.
     *
     * When not verified, this component is construct with an empty set of nodes.
     *
     * @param   graph  the Lattice to be copied
     */
    public Lattice(DAGraph graph) {
        super(graph);
        if (!this.isAcyclic()) {
            this.setNodes(new TreeSet<Node>());
            this.setSuccessors(new TreeMap<Node, TreeSet<Edge>>());
            this.setPredecessors(new TreeMap<Node, TreeSet<Edge>>());
        }
    }

    /* ------------- LATTICE CHEKING METHODS ------------------ */

   /**
    * Check if this component is a lattice.
    *
    * There exists several caracterizations of a lattice.
    * The characterization implemented is the following:
    * A lattice is a DAG if there exists a meet for each pair of node,
    * and a unique maximal node.
    *
    * This treatment is performed in O(n^3), where n is the number of nodes.
    *
    * @return  the truth value for this property
    */
   public boolean isLattice() {
        if (!this.isAcyclic()) {
            return false;
        }
        for (Node x : this.getNodes()) {
            for (Node y : this.getNodes()) {
                if (this.meet(x, y) == null) {
                    return false;
                }
            }
        }
        return this.max().size() == 1;
    }

   /**
    * Return true if this component is congruence normal.
    *
    * A lattice $L$ is in class CN (Congruence Normal) is there exists a sequence $L_1,\ldots,L_p$ of lattices with $L_p=L$,
    * together with a sequence $C_1,\ldots,C_{p-1}$ such that $C_i$ is a convex of $L_i$ and $L_{i+1}$ is obtained
    * by doubling the convex $C_i$ in $L_i$.
    *
    * See {@link lattice.LatticeFactory} for the doubling convex method which is not used here.
    *
    * This computation is done in O((|J|+|M|)^2|X|) from the transitive reduction of L.
    *
    * This recognition algorithm was first written in :
    * "Doubling convex serts in lattices : characterizations and recognition algorithm", Bertet K., Caspard N. 2002.
    *
    * @return true if this component is congruence normal.
    */
   public boolean isCN() {
       TreeSet<Node> joins = this.joinIrreducibles();
       TreeSet<Node> meats = this.meetIrreducibles();
       DGraph arrows = new ArrowRelation(this);
       Context dbl = this.getDoubleArrowTable();
       // steps are connected component of the double arrow table.
       ArrayList<Concept> steps = new ArrayList<Concept>();
       while (!joins.isEmpty()) {
           TreeSet<Comparable> setA = new TreeSet<Comparable>();
           TreeSet<Comparable> setB = new TreeSet<Comparable>();
           int cardA = setA.size();
           setA.add(joins.first());
           while (cardA != setA.size()) { // something added
               cardA = setA.size(); // update card
               for (Comparable c : setA) {
                   setB.addAll(dbl.getIntent(c));
               }
               for (Comparable c : setB) {
                   setA.addAll(dbl.getExtent(c));
               }
           }
           steps.add(new Concept(setA, setB));
           joins.removeAll(setA);
       }
       for (Concept c : steps) {
           if (c.getSetB().isEmpty()) { // to be verified :-)
               return false;
           }
           for (Comparable j : c.getSetA()) {
               for (Comparable m : c.getSetB()) {
                   if ((arrows.getEdge((Node) m, (Node) j).getContent() != "UpDown")
                           && (arrows.getEdge((Node) m, (Node) j).getContent() != "Circ")) {
                       return false;
                   }
               }
           }
       }
       joins = this.joinIrreducibles();
       meats = this.meetIrreducibles();
       DGraph phi = new DGraph();
       for (Node j : joins) {
           for (Node m : meats) {
               int indJ = 0; // Search for the step containning j
               while ((indJ < steps.size()) && (!steps.get(indJ).containsInA(j))) {
                   indJ++;
               }
               if ((phi.getNodeByContent(indJ) == null) && (indJ != steps.size())) {
                   phi.addNode(new Node(indJ));
               }
               int indM = 0; // Search for the step containning m
               while ((indM < steps.size()) && (!steps.get(indM).containsInB(m))) {
                   indM++;
               }
               if ((phi.getNodeByContent(indM) == null) && (indM != steps.size())) {
                   phi.addNode(new Node(indM));
               }
               if (indM != steps.size() && indJ != steps.size()) {
                   if (arrows.getEdge((Node) m, (Node) j).getContent() == "Up") {
                       phi.addEdge(phi.getNodeByContent(indM), phi.getNodeByContent(indJ));
                   }
                   if (arrows.getEdge((Node) m, (Node) j).getContent() == "Down") {
                       phi.addEdge(phi.getNodeByContent(indJ), phi.getNodeByContent(indM));
                   }
               }
           }
       }
       return (phi.isAcyclic());
   }
   /**
    * Returns true if this component is an atomistic lattice.
    *
    * A lattice is atomistic if its join irreductibles are atoms (e.g. successors of bottom)
    *
    * @return true if this component is atomistic.
    */
   public boolean isAtomistic() {
       TreeSet<Node> join = this.joinIrreducibles();
       TreeSet<Node> atoms = this.getSuccessorNodes(this.bottom());
       return join.containsAll(atoms) && atoms.containsAll(join);
   }
   /**
    * Returns true if this component is an coatomistic lattice.
    *
    * A lattice is coatomistic if its mett irreductibles are coatoms (e.g. predecessors of top)
    *
    * @return true if this component is coatomistic.
    */
   public boolean isCoAtomistic() {
       TreeSet<Node> meet = this.meetIrreducibles();
       TreeSet<Node> coatoms = this.getPredecessorNodes(this.top());
       return meet.containsAll(coatoms) && coatoms.containsAll(meet);
   }
    /* --------------- LATTICE HANDLING METHODS ------------ */

    /**
     * Returns the top of the lattice.
     *
     * @return  the node which is at the top of the lattice or null if it is not unique
     */
    public Node top() {
        TreeSet<Node> max = this.max();
        if (max.size() == 1) {
            return max.first();
        }
        return null;
    }

    /**
     * Returns the bottom of the lattice.
     *
     * @return  the node which is at the bottom of the lattice or null if it is not unique
     */
    public Node bottom() {
        TreeSet<Node> min = this.min();
        if (min.size() == 1) {
            return min.first();
        }
        return null;
    }

    /**
     * Returns the meet of the two specified nodes if it exists.
     *
     * @param   x  the first node
     * @param   y  the second node
     *
     * @return  the node which is at the meet of the nodes or null if it does not exist
     */
    public Node meet(Node x, Node y) {
        TreeSet<Node> xMinorants = this.minorants(x);
        xMinorants.add(x);

        TreeSet<Node> yMinorants = this.minorants(y);
        yMinorants.add(y);

        xMinorants.retainAll(yMinorants);
        DAGraph graph = this.getSubgraphByNodes(xMinorants);
        TreeSet<Node> meet = graph.max();
        if (meet.size() == 1) {
            return meet.first();
        }
        return null;
    }

    /**
     * Returns the join of the two specified nodes if it exists.
     *
     * @param   x  the first node
     * @param   y  the second node
     *
     * @return  the node which is at the join of the nodes or null if it does not exist
     */
    public Node join(Node x, Node y) {
        TreeSet<Node> xMajorants = this.majorants(x);
        xMajorants.add(x);

        TreeSet<Node> yMajorants = this.majorants(y);
        yMajorants.add(y);

        xMajorants.retainAll(yMajorants);
        DAGraph graph = this.getSubgraphByNodes(xMajorants);
        TreeSet<Node> join = graph.min();
        if (join.size() == 1) {
            return join.first();
        }
        return null;
    }

    /* ------------- IRREDUCIBLES RELATIVE METHODS ------------------ */

    /**
     * Returns the set of join irreducibles of this component.
     *
     * Join irreducibles are nodes with an unique immediate predecessor
     * in the transitive and reflexive reduction.
     * This component is first reduced reflexively and transitively.
     *
     * @return  the set of join irreducibles of this component
     */
    public TreeSet<Node> joinIrreducibles() {
        DAGraph graph = new DAGraph(this);
        graph.reflexiveReduction();
        graph.transitiveReduction();
        TreeSet<Node> set = new TreeSet();
        for (Node n : graph.getNodes()) {
            if (graph.getPredecessorNodes(n).size() == 1) {
                set.add(n);
            }
        }
        return set;
    }

    /**
     * Returns the set of meet irreducibles of this component.
     *
     * Meet irreducibles are nodes with an unique immediate successor
     * in the transitiv and reflexiv reduction.
     * This component is first reduced reflexively and transitively.
     *
     * @return  the set of meet irreducibles of this component.
     */
    public TreeSet<Node> meetIrreducibles() {
        DAGraph graph = new DAGraph(this);
        graph.reflexiveReduction();
        graph.transitiveReduction();
        TreeSet<Node> set = new TreeSet();
        for (Node n : graph.getNodes()) {
            if (graph.getSuccessorNodes(n).size() == 1) {
                set.add(n);
            }
        }
        return set;
    }

    /**
     * Returns the set of join-irreducibles that are minorants of the specified node.
     *
     * @param   n  a specified node
     *
     * @return  the set of join-irreducibles thar are minorants of the specified node
     */
    public TreeSet<Comparable> joinIrreducibles(Node n) {
        TreeSet<Comparable> join = new TreeSet<Comparable>(this.joinIrreducibles());
        TreeSet<Comparable> min = new TreeSet<Comparable>(this.minorants(n));
        min.add(n);
        min.retainAll(join);
        return min;
    }

    /**
     * Returns the set of meet-irreducibles thar are majorants of the specified node.
     *
     * @param   n  a specified node
     *
     * @return  the set of meet-irreducibles thar are majorants of the specified node
     */
    public TreeSet<Comparable> meetIrreducibles(Node n) {
        TreeSet<Comparable> meet = new TreeSet<Comparable>(this.meetIrreducibles());
        TreeSet<Comparable> maj = new TreeSet<Comparable>(this.majorants(n));
        maj.retainAll(meet);
        return maj;
    }

    /**
     * Returns the subgraph induced by the join irreducibles nodes of this component.
     *
     * @return  the subgraph induced by the join irreducibles nodes of this component
     */
    public DAGraph joinIrreduciblesSubgraph() {
        TreeSet<Node> irr = this.joinIrreducibles();
        return this.getSubgraphByNodes(irr);
    }

    /**
     * Returns the subgraph induced by the meet irreducibles nodes of this component.
     *
     * @return  the subgraph induced by the meet irreducibles nodes of this component
     */
    public DAGraph meetIrreduciblesSubgraph() {
        TreeSet<Node> irr = this.meetIrreducibles();
        return this.getSubgraphByNodes(irr);
    }

    /**
     * Returns the subgraph induced by the irreducibles nodes of this component.
     *
     * @return  the subgraph induced by the irreducibles nodes of this component
     */
    public DAGraph irreduciblesSubgraph() {
        TreeSet<Node> irr = this.meetIrreducibles();
        irr.addAll(this.joinIrreducibles());
        return this.getSubgraphByNodes(irr);
    }

    /**
     * Generates and returns the isomorphic closed set lattice defined on the join irreducibles set.
     *
     * Each node of this component is replaced  by a node containing its join irreducibles
     * predecessors stored in a closed set.
     *
     * @return  the isomorphic closed set lattice defined on the join irreducibles set
     */
    public ConceptLattice joinClosure() {
        ConceptLattice csl = new ConceptLattice();
        // associates each node to a new closed set of join irreducibles
        TreeSet<Node> join = this.joinIrreducibles();
        TreeMap<Node, Concept> closure = new TreeMap<Node, Concept>();
        Lattice lattice = new Lattice(this);
        lattice.transitiveClosure();
        lattice.reflexiveClosure();
        for (Node to : lattice.getNodes()) {
            ComparableSet jx = new ComparableSet();
            for (Node from : lattice.getPredecessorNodes(to)) {
                if (join.contains(from)) {
                    jx.add(from.getContent());
                }
            }
            closure.put(to, new Concept(jx, false));
        }
        // addition of nodes
        for (Node n : this.getNodes()) {
            csl.addNode(closure.get(n));
        }
        // addition of edges
        for (Edge ed : this.getEdges()) {
            csl.addEdge(closure.get(ed.getFrom()), closure.get(ed.getTo()));
        }
        return csl;
    }

    /**
     * Generates and returns the isomorphic closed set lattice defined on the meet irreducibles set.
     *
     * Each node of this component is replaced  by a node containing its meet irreducibles
     * successors stored in a closed set.
     *
     * @return  the isomorphic closed set lattice defined on the meet irreducibles set
     */
    public ConceptLattice meetClosure() {
        ConceptLattice csl = new ConceptLattice();
        // associates each node to a new closed set of join irreducibles
        TreeSet<Node> meet = this.meetIrreducibles();
        TreeMap<Node, Concept> closure = new TreeMap<Node, Concept>();
        Lattice lattice = new Lattice(this);
        lattice.transitiveClosure();
        lattice.reflexiveClosure();
        for (Node to : lattice.getNodes()) {
            ComparableSet mx = new ComparableSet();
            for (Node from : lattice.getSuccessorNodes(to)) {
                if (meet.contains(from)) {
                    mx.add(from);
                }
            }
            closure.put(to, new Concept(false, mx));
        }
        // addition of nodes
        for (Node n : this.getNodes()) {
            csl.addNode(closure.get(n));
        }
        // addition of edges
        for (Edge ed : this.getEdges()) {
            csl.addEdge(closure.get(ed.getFrom()), closure.get(ed.getTo()));
        }
        return csl;
    }

    /**
     * Generates and returns the isomorphic concept lattice defined on the join and meet
     * irreducibles sets.
     *
     * Each node of this component is replaced  by a node containing its meet irreducibles
     * successors and its join irreducibles predecessors stored in a concept.
     *
     * @return  the irreducible closure
     */
    public ConceptLattice irreducibleClosure() {
        ConceptLattice conceptLatice = new ConceptLattice();
        // associates each node to a new closed set of join irreducibles
        TreeSet<Node> meet = this.meetIrreducibles();
        TreeSet<Node> join = this.joinIrreducibles();
        TreeMap<Node, Concept> closure = new TreeMap<Node, Concept>();
        Lattice lattice = new Lattice(this);
        lattice.transitiveClosure();
        lattice.reflexiveClosure();
        for (Node to : lattice.getNodes()) {
            ComparableSet jx = new ComparableSet();
            for (Node from : lattice.getPredecessorNodes(to)) {
                if (join.contains(from)) {
                    jx.add(from);
                }
            }
            ComparableSet mx = new ComparableSet();
            for (Node from : lattice.getSuccessorNodes(to)) {
                if (meet.contains(from)) {
                    mx.add(from);
                }
            }
            closure.put(to, new Concept(jx, mx));
        }
        // addition of nodes
        for (Node n : this.getNodes()) {
            conceptLatice.addNode(closure.get(n));
        }
        // addition of edges
        for (Edge ed : this.getEdges()) {
            conceptLatice.addEdge(closure.get(ed.getFrom()), closure.get(ed.getTo()));
        }
        return conceptLatice;
    }

    /**
     * Returns the table of the lattice, composed of the join and meet irreducibles nodes.
     *
     * Each attribute of the table is a copy of a join irreducibles node.
     * Each observation of the table is a copy of a meet irreducibles node.
     * An attribute is extent of an observation when its join irreducible node
     * is greater than the meet irreducible node in the lattice.
     *
     * @return  the table of the lattice
     */
    public Context getTable() {
        // generation of attributes
        TreeSet<Node> join = this.joinIrreducibles();
        //TreeMap<Node,Node> JoinContent = new TreeMap();
        Context context = new Context();
        for (Node j : join) {
          //  Node nj = new Node(j);
            //JoinContent.put(j,nj);
            context.addToAttributes(j);
        }
        // generation of observations
        TreeSet<Node> meet = this.meetIrreducibles();
        //TreeMap<Node,Node> MeetContent = new TreeMap();
        for (Node m : meet) {
        //    Node nm = new Node(m);
            context.addToObservations(m);
        //    MeetContent.put(m,nm);
        }
        // generation of extent-intent
        Lattice tmp = new Lattice(this);
        tmp.transitiveClosure();
        for (Node j : join) {
            for (Node m : meet) {
                if (j.equals(m) || tmp.getSuccessorNodes(j).contains(m)) {
                    context.addExtentIntent(m, j);
                    //T.addExtentIntent(MeetContent.get(m),JoinContent.get(j));
                }
            }
        }
        return context;
    }

    /**
     * Returns an ImplicationalSystem of the lattice defined on the join irreducibles nodes.
     *
     * Each element of the ImplicationalSystem is a copy of a join irreducible node.
     *
     * @return  an implicational system
     */
    public ImplicationalSystem getImplicationalSystem() {
        // initialization of ImplicationalSystem
        TreeSet<Node> join = this.joinIrreducibles();
        ImplicationalSystem sigma = new ImplicationalSystem();
        for (Node j : join) {
            sigma.addElement((Comparable) j.getContent());
        }
        // generation of the family of closures
        TreeSet<ComparableSet> family = new TreeSet<ComparableSet>();
        Lattice lattice = new Lattice(this);
        ConceptLattice conceptLattice = lattice.joinClosure();
        for (Node n : conceptLattice.getNodes()) {
            Concept c = (Concept) n;
            ComparableSet setA = new ComparableSet(c.getSetA());
            family.add(setA);
        }
        // rules generation
        for (ComparableSet jx : family) {
            for (Node j : join) {
                ComparableSet p = new ComparableSet();
                p.add(j.getContent());
                p.addAll(jx);
                if (!family.contains(p)) {
                    ComparableSet min = new ComparableSet();
                    min.addAll(family.last());
                    for (ComparableSet c : family) {
                        //System.out.println("min: "+min.getClass()+" -C:"+C.getClass());
                        if (c.containsAll(p) && !p.containsAll(c) && min.containsAll(c)) {
                            min = c.copy();
                        }
                    }
                    Rule r = new Rule();
                    r.addAllToPremise(p);
                    min.removeAll(p);
                    r.addAllToConclusion(min);
                    sigma.addRule(r);
                }
            }
        }


/**        for (Node j : join)
            for (Node m : meet)
                if (j.equals(m) || tmp.getSuccessorNodes(j).contains(m))
                    T.addExtentIntent (m,j);
                    //T.addExtentIntent (MeetContent.get(m),JoinContent.get(j));
        return T;**/
        sigma.makeRightMaximal();
        return sigma;
    }
   /* ------------- dependency GRAPH RELATIVE METHODS ------------------ */

   /** Returns the dependency graph of this component.
    *
    * The dependency graph is a condensed representation of a lattice that encodes
    * its minimal generators, and its canonical direct basis.
    *
    * In the dependency graph, nodes are join irreducibles,
    * egdes correspond to the dependency relation between join-irreducibles
    * (j -> j' if and only if there exists a node x in the lattice such that
    * x not greather than j and j', and x v j' > j),
    * and edges are labeled with the smallest subsets X of join-irreducibles such that
    * the join of elements of X corresponds to the node x of the lattice.
    *
    * The dependency graph could has been already computed in the case where this component
    * has been instancied as the diagramm of the closed set lattice of a closure system
    * using the static method
    * {@link ConceptLattice#diagramLattice}
    * This method implements an adaptation adaptation of Bordat's
    * where the dependency graph is computed while the lattice is generated.
    *
    * However, it is generated in O(nj^3) where n is the number of nodes of the lattice, and
    * j is the number of join-irreducibles of the lattice.
    *
    * @return  the dependency graph
    */
    public DGraph getDependencyGraph() {
        if (!(this.dependencyGraph == null)) {
            return this.dependencyGraph;
        }
        this.dependencyGraph = new DGraph();
        // nodes of the dependency graph are join-irreducibles
        TreeSet<Node> joins = this.joinIrreducibles();
        for (Node j : joins) {
            this.dependencyGraph.addNode(j);
        }
        // computes the transitive closure of the join-irreducibles subgraph of this compnent
        DAGraph joinG = this.irreduciblesSubgraph();
        joinG.transitiveClosure();
        // edges of the dependency graph are dependency relation between join-irreducibles
        // they are first valuated by nodes of the lattice
        for (Node j1 : joins) {
            TreeSet<Node> majj1 = this.majorants(j1);
            for (Node j2 : joins) {
                if (!j1.equals(j2)) {
                    // computes the set S of nodes not greather than j1 and j2
                    TreeSet<Node> set = new TreeSet<Node>(this.getNodes());
                    set.removeAll(majj1);
                    set.removeAll(this.majorants(j2));
                    set.remove(j1);
                    set.remove(j2);
                    for (Node x: set) {
                        // when j2 V x greather than j1 then add a new edge from j1 to J2
                        // or only a new valuation when the edge already exists
                        if (majj1.contains(this.join(j2, x))) {
                            Edge ed = this.dependencyGraph.getEdge(j1, j2);
                            if (ed == null) {
                                ed = new Edge(j1, j2, new TreeSet<ComparableSet>());
                                this.dependencyGraph.addEdge(ed);
                            }
                            // add {Jx minus predecessors in joinG of j in Jx} as valuation of edge
                            // from j1 to j2
                            TreeSet<ComparableSet> valEd = (TreeSet<ComparableSet>) ed.getContent();
                            ComparableSet newValByNode = new ComparableSet(this.joinIrreducibles(x));
                            for (Comparable j : this.joinIrreducibles(x)) {
                                newValByNode.removeAll(joinG.getPredecessorNodes((Node) j));
                            }
                            ComparableSet newVal = new ComparableSet();
                            for (Object j : newValByNode) {
                                Node n = (Node) j;
                                newVal.add(n.getContent());
                            }
                            ((TreeSet<ComparableSet>) ed.getContent()).add(newVal);
                            // Minimalisation by inclusion of valuations on edge j1->j2
                            valEd = new TreeSet<ComparableSet>((TreeSet<ComparableSet>) ed.getContent());
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
         }
        // minimalisation of edge's content to get only inclusion-minimal valuation for each edge
        /**for (Edge ed : this.dependencyGraph.getEdges()) {
             TreeSet<ComparableSet> valEd = new TreeSet<ComparableSet>(((TreeSet<ComparableSet>)ed.getContent()));
             for (ComparableSet X1 : valEd)
                 for (ComparableSet X2 : valEd)
                     if (X1.containsAll(X2) && !X2.containsAll(X1))
                         ((TreeSet<ComparableSet>)ed.getContent()).remove(X1);
        }**/
        return this.dependencyGraph;
    }

    /**
     * Set the dependency graph.
     *
     * @param   graph  the dependency graph
     */
    protected void setDependencyGraph(DGraph graph) {
        this.dependencyGraph = graph;
    }

    /**
     * Test if this component has a dependency graph.
     *
    * @return  the truth value for this property
     */
    protected boolean hasDependencyGraph() {
        return this.dependencyGraph != null;
    }

    /**
     * Returns the canonical direct basis of the lattice.
     *
     * The canonical direct basis is a condensed representation of a lattice encoding
     * by the dependency graph.
     *
     * This canonical direct basis is deduced from the dependency graph of the lattice:
     * for each edge b -> a valuated by a subset X, the rule a+X->b is a rule of the
     * canonical direct basis.
     *
     * If not yet exists, the dependency graph of this component has to be generated
     * by method {@link #getDependencyGraph}.
     *
     * @return  the canonical direct basis of the lattice
     */
    public ImplicationalSystem getCanonicalDirectBasis() {
        DGraph odGraph = this.getDependencyGraph();
        // initialize elements of the ImplicationalSystem with nodes of the ODGraph
        ImplicationalSystem bcd = new ImplicationalSystem();
        for (Node n : odGraph.getNodes()) {
            bcd.addElement((Comparable) n.getContent());
        }
        // computes rules of the BCD from edges of the ODGraph
        for (Edge ed : odGraph.getEdges()) {
            Node from = ed.getFrom();
            Node to = ed.getTo();
            TreeSet<ComparableSet> l = (TreeSet<ComparableSet>) ed.getContent();
            for (ComparableSet set : l) {
                ComparableSet premise = new ComparableSet(set);
                premise.add((Comparable) to.getContent());
                ComparableSet conclusion = new ComparableSet();
                conclusion.add((Comparable) from.getContent());
                bcd.addRule(new Rule(premise, conclusion));
            }
        }
        //bcd.makeLeftMinimal();
        bcd.makeCompact();
        return bcd;
    }

    /**
     * Returns the minimal generators of the lattice.
     *
     * Minimal generators a condensed representation of a lattice encoding
     * by the dependency graph.
     *
     * Minimal generators are premises of the canonical direct basis.
     * that  is deduced from the dependency graph of the lattice.
     *
     * If not yet exists, the dependency graph of this component has to be generated
     * by method {@link #getDependencyGraph}.
     *
     * @return  a TreeSet of the minimal generators
     */
    public TreeSet getMinimalGenerators() {
        ImplicationalSystem bcd = this.getCanonicalDirectBasis();
        TreeSet genMin = new TreeSet();
        for (Rule r : bcd.getRules()) {
            genMin.add(r.getPremise());
        }
        return genMin;
    }

     /**
      * The arrowRelation method encodes arrow relations between meet & join-irreductibles of this component.
      *
      * Let m and j be respectively meet and join irreductibles of a lattice.
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
     public DGraph getArrowRelation() {

        /* Nodes are join or meet irreductibles of the lattice. */
        TreeSet<Node> joins = new TreeSet<Node>(this.joinIrreducibles());
        TreeSet<Node> meets = new TreeSet<Node>(this.meetIrreducibles());
        TreeSet<Node> nodes = new TreeSet<Node>(meets);
        nodes.addAll(joins);

        DGraph graph = new DGraph(nodes);

        Lattice transitiveClosure = new Lattice(this);
        transitiveClosure.transitiveClosure();
        Lattice transitiveReduction = new Lattice(this);
        transitiveReduction.transitiveReduction();
        Node jminus = new Node();
        Node mplus = new Node();
        String arrow = "";

        /* Content of edges are arrows */
        for (Node j : joins) {
            for (Node m : meets) {
                mplus = transitiveReduction.getSuccessorNodes(m).first();
                jminus = transitiveReduction.getPredecessorNodes(j).first();
                if (transitiveClosure.getSuccessorNodes(j).contains(m) || j.equals(m)) {
                    arrow = "Cross";
                } else {
                    if (transitiveClosure.getSuccessorNodes(jminus).contains(m)) {
                        arrow = "Down";
                        if (transitiveClosure.getPredecessorNodes(mplus).contains(j)) {
                            arrow = "UpDown";
                        }
                    } else {
                        if (transitiveClosure.getPredecessorNodes(mplus).contains(j)) {
                            arrow = "Up";
                        } else {
                            arrow = "Circ";
                        }
                    }
                }
                graph.addEdge(m, j, arrow);
            }
        }
        return graph;
    }

     /**
     * Returns the table of the lattice, composed of the join and meet irreducibles nodes.
     *
     * Each attribute of the table is a copy of a join irreducibles node.
     * Each observation of the table is a copy of a meet irreducibles node.
     * An attribute is extent of an observation when its join irreducible node
     * is in double arrow relation with the meet irreducible node in the lattice.
     *
     * @return  the table of the lattice
     */
    public Context getDoubleArrowTable() {
        // generation of attributes
        TreeSet<Node> join = this.joinIrreducibles();
        Context context = new Context();
        for (Node j : join) {
            context.addToObservations(j);
        }
        // generation of observations
        TreeSet<Node> meet = this.meetIrreducibles();
        for (Node m : meet) {
            context.addToAttributes(m);
        }
        // generation of extent-intent
        Lattice transitiveClosure = new Lattice(this);
        transitiveClosure.transitiveClosure();
        Lattice transitiveReduction = new Lattice(this);
        transitiveReduction.transitiveReduction();
        Node jminus = new Node();
        Node mplus = new Node();
        for (Node j : join) {
            for (Node m : meet) {
                if (!(m.equals(j) || transitiveClosure.getSuccessorNodes(j).contains(m))) {
                    mplus = transitiveReduction.getSuccessorNodes(m).first();
                    jminus = transitiveReduction.getPredecessorNodes(j).first();
                    if (transitiveClosure.getSuccessorNodes(jminus).contains(m) && (transitiveClosure.getPredecessorNodes(mplus).contains(j))) {
                        context.addExtentIntent(j, m);
                    }
                }
            }
        }
        return context;
    }

    /**
     * Returns the table of the lattice, composed of the join and meet irreducibles nodes.
     *
     * Each attribute of the table is a copy of a join irreducibles node.
     * Each observation of the table is a copy of a meet irreducibles node.
     * An attribute is extent of an observation when its join irreducible node
     * is in double arrow relation or circ relation with the meet irreducible node in the lattice.
     *
     * @return  the table of the lattice
     */
    public Context getDoubleCircArrowTable() {
        // generation of attributes
        TreeSet<Node> join = this.joinIrreducibles();
        Context context = new Context();
        for (Node j : join) {
            context.addToObservations(j);
        }
        // generation of observations
        TreeSet<Node> meet = this.meetIrreducibles();
        for (Node m : meet) {
            context.addToAttributes(m);
        }
        // generation of extent-intent

        DGraph arrow = this.getArrowRelation();
        for (Edge e : arrow.getEdges()) {
            if (e.getContent() == "UpDown" || e.getContent() == "Circ") {
                context.addExtentIntent(e.getTo(), e.getFrom());
            }
        }
        return context;
    }
}
