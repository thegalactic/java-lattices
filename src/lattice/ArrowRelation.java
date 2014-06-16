package lattice;

/*
 * ArrowRelation.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import dgraph.Edge;
import dgraph.DGraph;
import dgraph.Node;
import java.util.TreeSet;

/**
 * The ArrowRelation class encodes arrow relation between meet & join-irreductibles of a lattice.
 *
 * Let m and b be respectively meet and join irreductibles of a lattice.
 * Recall that m has a unique successor say m+ and j has a unique predecessor say j-, then :
 *
 * - j "Up Arrow" m (stored as "Up") iff j is not less or equal than m and j is less than m+
 * - j "Down Arrow" m (stored as "Down") iff j is not less or equal than m and j- is less than m
 * - j "Up Down Arrow" m (stored as "UpDown") iff j "Up" m and j "Down" m
 * - j "Cross" m (stored as "Cross") iff j is less or equal than m
 * - j "Circ" m (stored as "Circ") iff neither j "Up" m nor j "Down" m nor j "Cross" m
 *
 * ![ArrowRelation](ArrowRelation.png)
 *
 * @uml ArrowRelation.png
 * !include src/dgraph/DGraph.iuml
 * !include src/dgraph/Edge.iuml
 * !include src/dgraph/Node.iuml
 * !include src/lattice/ArrowRelation.iuml
 *
 * hide members
 * show ArrowRelation members
 * class ArrowRelation #LightCyan
 * title ArrowRelation UML graph
 */
public class ArrowRelation extends DGraph  {
    /**
     * Field used to encode up arrow relation.
     */
    private static Object up = "Up";
    /**
     * Field used to encode down arrow relation.
     */
    private static Object down = "Down";
    /**
     * Field used to encode up-down arrow relation.
     */
    private static Object updown = "UpDown";
    /**
     * Field used to encode cross arrow relation.
     */
    private static Object cross = "Cross";
    /**
     * Field used to encode circ arrow relation.
     */
    private static Object circ = "Circ";

    /*
     * Register tex writer
     */
    static {
        if (ArrowRelationWriterFactory.get("tex") == null) {
            ArrowRelationWriterTeX.register();
        }
    }

    /**
     * Unique constructor of this component from a lattice.
     *
     * Nodes are join or meet irreductibles of the lattice.
     * Edges content encodes arrows as String "Up", "Down", "UpDown", "Cross", "Circ".
     *
     * @param   lattice  Lattice from which this component is deduced.
     */
    public ArrowRelation(Lattice lattice) {

        /* Nodes are join or meet irreductibles of the lattice. */
        TreeSet<Node> joins = new TreeSet<Node>(lattice.joinIrreducibles());
        for (Node n : joins) {
            this.addNode(n);
        }
        TreeSet<Node> meets = new TreeSet<Node>(lattice.meetIrreducibles());
        for (Node n : meets) {
            this.addNode(n);
        }
        Lattice transitiveClosure = new Lattice(lattice);
        transitiveClosure.transitiveClosure();
        Lattice transitiveReduction = new Lattice(lattice);
        transitiveReduction.transitiveReduction();
        Node jminus = new Node();
        Node mplus = new Node();
        Object arrow = new Object();

        /* Content of edges are arrows */
        for (Node j : joins) {
            for (Node m : meets) {
                mplus = transitiveReduction.getSuccessorNodes(m).first();
                jminus = transitiveReduction.getPredecessorNodes(j).first();
                if (transitiveClosure.getSuccessorNodes(j).contains(m) || j.equals(m)) {
                    arrow = this.cross;
                } else {
                    if (transitiveClosure.getSuccessorNodes(jminus).contains(m) || jminus.equals(m)) {
                        arrow = this.down;
                        if (transitiveClosure.getPredecessorNodes(mplus).contains(j) || mplus.equals(j)) {
                            arrow = this.updown;
                        }
                    } else {
                        if (transitiveClosure.getPredecessorNodes(mplus).contains(j)) {
                            arrow = this.up;
                        } else {
                            arrow = this.circ;
                        }
                    }
                }
                this.addEdge(j, m, arrow);
            }
        }
    }

    /**
     * Save the description of this component in a file whose name is specified.
     *
     * @param   filename  the name of the file
     *
     * @throws  IOException  When an IOException occurs
     */
    public void save(final String filename) throws IOException {
        String extension = "";
        int index = filename.lastIndexOf('.');
        if (index > 0) {
            extension = filename.substring(index + 1);
        }
        BufferedWriter file = new BufferedWriter(new FileWriter(filename));
        ArrowRelationWriterFactory.get(extension).write(this, file);
        file.close();
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
        Context context = new Context();
        // observations are join irreductibles
        // attributes are meet irreductibles
        for (Edge e : this.getEdges()) {
            context.addToObservations(e.getFrom());
            context.addToAttributes(e.getTo());
        }
        // generation of extent-intent
        for (Edge e : this.getEdges()) {
            if (e.getContent() == this.updown) {
                context.addExtentIntent(e.getFrom(), e.getTo());
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
        Context context = new Context();
        // observations are join irreductibles
        // attributes are meet irreductibles
        for (Edge e : this.getEdges()) {
            context.addToObservations(e.getFrom());
            context.addToAttributes(e.getTo());
        }
        // generation of extent-intent
        for (Edge e : this.getEdges()) {
            if (e.getContent() == this.updown || e.getContent() == this.circ) {
                context.addExtentIntent(e.getFrom(), e.getTo());
            }
        }
        return context;
    }
    /**
     * Returns true if and only if there is an up arrow between from and to of edge e.
     *
     * @param e edge to be tested
     * @return true if and only if there is an up arrow between from and to of edge e
     */
    public boolean isUp(Edge e) {
        return (e.getContent() == this.up);
    }
    /**
     * Returns true if and only if there is an down arrow between from and to of edge e.
     *
     * @param e edge to be tested
     * @return true if and only if there is an down arrow between from and to of edge e
     */
    public boolean isDown(Edge e) {
        return (e.getContent() == this.down);
    }
    /**
     * Returns true if and only if there is an up-down arrow between from and to of edge e.
     *
     * @param e edge to be tested
     * @return true if and only if there is an up-down arrow between from and to of edge e
     */
    public boolean isUpDown(Edge e) {
        return (e.getContent() == this.updown);
    }
    /**
     * Returns true if and only if there is an cross arrow between from and to of edge e.
     *
     * @param e edge to be tested
     * @return true if and only if there is an cross arrow between from and to of edge e
     */
    public boolean isCross(Edge e) {
        return (e.getContent() == this.cross);
    }
    /**
     * Returns true if and only if there is an circ arrow between from and to of edge e.
     *
     * @param e edge to be tested
     * @return true if and only if there is an circ arrow between from and to of edge e
     */
    public boolean isCirc(Edge e) {
        return (e.getContent() == this.circ);
    }
}
