package org.thegalactic.dgraph;

/*
 * AbstractDGraph.java
 *
 * Copyright: 2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.io.IOException;
import java.util.SortedSet;
import org.thegalactic.dgraph.io.DGraphIOFactory;
import org.thegalactic.io.Filer;

/**
 * AbstractDGraph.
 */
public abstract class AbstractDGraph {

    /**
     * Basic constructor.
     */
    public AbstractDGraph() {
    }

    /**
     * Returns a String representation of this component.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        StringBuilder nodes = new StringBuilder();
        nodes.append(this.sizeNodes()).append(" Nodes: {");
        StringBuilder edges = new StringBuilder();
        edges.append(this.sizeEdges()).append(" Edges: {");
        for (Node node : this.getNodes()) {
            nodes.append(node.toString()).append(",");
        }
        for (Edge edge : this.getEdges()) {
            edges.append(edge.toString()).append(",");
        }
        String newLine = System.getProperty("line.separator");
        nodes.append("}").append(newLine).append(edges).append("}").append(newLine);
        return nodes.toString();
    }

    /**
     * Returns the number of edges of this component.
     *
     * @return the number of edges
     */
    public abstract int sizeEdges();

    /**
     * Returns the number of nodes of this component.
     *
     * @return the number of nodes
     */
    public abstract int sizeNodes();

    /**
     * Returns the set of edges of this component.
     *
     * @return the set of edges
     */
    public abstract SortedSet<Edge> getEdges();

    /**
     * Returns the set of nodes of this component.
     *
     * @return the set of nodes
     */
    public abstract SortedSet<Node> getNodes();

    /**
     * Save the description of this component in a file whose name is specified.
     *
     * @param filename the name of the file
     *
     * @throws IOException When an IOException occurs
     */
    public void save(final String filename) throws IOException {
        Filer.getInstance().save(this, DGraphIOFactory.getInstance(), filename);
    }
}
