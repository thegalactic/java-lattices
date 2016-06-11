package org.thegalactic.dgraph;

/*
 * Edge.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
/**
 * This class gives a standard representation for an edge of a graph.
 *
 * An edge is composed of a content, and two nodes
 *
 * - `from`
 * - `to`
 *
 * of class {@link Node}.
 *
 * This class implements class `Comparable` and provides the {@link #compareTo}
 * method that compares the edge with those in parameter by sorting indexes of
 * the nodes that compose it.
 *
 * Therefore, edges can be stored in a sorted collection since they are
 * comparable, and in particular in a sorted set where set operations are
 * provided.
 *
 * ![Edge](Edge.png)
 *
 * @uml Edge.png
 * !include resources/org/thegalactic/dgraph/Edge.iuml
 * !include resources/org/thegalactic/dgraph/Node.iuml
 *
 * hide members
 * show Edge members
 * class Edge #LightCyan
 * title Edge UML graph
 */
public class Edge implements Comparable<Object> {

    /*
     * ------------- FIELDS ---------------------
     */

    /**
     * The origin node of the edge.
     */
    private final Node from;

    /**
     * The destination node of the edge.
     */
    private final Node to;

    /**
     * An object to store information about this edge.
     */
    private Object content;

    /*
     * ------------- CONSTRUCTORS ----------------
     */
    /**
     * Constructs a new edge with the specified node as origin and destination
     * node, with the specified content.
     *
     * @param from the origin node
     * @param to the destination node
     * @param content the edge content
     */
    public Edge(final Node from, final Node to, final Object content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    /**
     * Constructs a new edge with the specified node as origin and destination
     * node, and a null value as content.
     *
     * @param from the origin node
     * @param to the destination node
     */
    public Edge(final Node from, final Node to) {
        this(from, to, null);
    }

    /*
     * -------------- ACCESSORS -------------------
     */
    /**
     * Returns the origin node of this edge.
     *
     * @return the origin node
     */
    public Node getFrom() {
        return this.from;
    }

    /**
     * Returns the destination node of this edge.
     *
     * @return the destination node
     */
    public Node getTo() {
        return this.to;
    }

    /**
     * Replaces the content of this edge with the specified one.
     *
     * @param content The edge content
     *
     * @return this for chaining
     */
    public Edge setContent(final Object content) {
        this.content = content;
        return this;
    }

    /**
     * Returns the content this edge.
     *
     * @return the content
     */
    public Object getContent() {
        return this.content;
    }

    /**
     * Returns true if content of this edge is not the null value.
     *
     * @return true if the content is not null
     */
    public boolean hasContent() {
        return this.content != null;
    }

    /*
     * ------------- METHODS ------------------
     */
    /**
     * Compares this edge with the specified one.
     *
     * @param object The object to be tested with
     *
     * @return true or false as this node is equal to the specified object.
     */
    @Override
    public boolean equals(final Object object) {
        return this.compareTo(object) == 0;
    }

    /**
     * Compute the hash code.
     *
     * @return an integer representing the object
     */
    @Override
    public int hashCode() {
        return 1013 * (from.hashCode()) ^ 1009 * (to.hashCode());
    }

    /**
     * Compares this edge with those in parameter, based on their identifiers.
     *
     * The result is zero if the identifiers are equal; positive if this edge's
     * identifier is greater, and negative otherwise.
     *
     * This comparison method is needed to define a natural ordering. It allows
     * to use objects of this class in a sorted collection
     *
     * @param object the specified element to be compared with this edge
     *
     * @return a negative integer, zero, or a positive integer as this edge is
     * less than, equal to, or greater than the specified object.
     */
    public int compareTo(final Object object) {
        if (object instanceof Edge) {
            Edge edge = (Edge) object;
            int cmp = this.from.compareTo(edge.from);
            if (cmp == 0) {
                return this.to.compareTo(edge.to);
            } else {
                return cmp;
            }
        } else {
            return -1;
        }
    }

    /**
     * Returns a String representation of this edge.
     *
     * @return The string representation of this edge
     */
    @Override
    public String toString() {
        String string = this.from + "->" + this.to;
        if (this.hasContent()) {
            string += "(" + this.content + ")";
        }
        return string;
    }
}
