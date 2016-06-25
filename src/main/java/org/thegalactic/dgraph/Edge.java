package org.thegalactic.dgraph;

/*
 * Edge.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
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

 - `source`
 - `target`

 of class {@link Node}.
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
 * @param <N>
 * @param <E>
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
public class Edge<N, E> implements Comparable<Edge<N, E>> {

    /*
     * ------------- FIELDS ---------------------
     */

    /**
     * The source node of the edge.
     */
    private final Node<N> source;

    /**
     * The target node of the edge.
     */
    private final Node<N> target;

    /**
     * An object target store information about this edge.
     */
    private E content;

    /*
     * ------------- CONSTRUCTORS ----------------
     */
    /**
     * Constructs a new edge with the specified node as origin and destination
     * node, with the specified content.
     *
     * @param source the origin node
     * @param target the destination node
     * @param content the edge content
     */
    public Edge(final Node<N> source, final Node<N> target, final E content) {
        this.source = source;
        this.target = target;
        this.content = content;
    }

    /**
     * Constructs a new edge with the specified node as origin and destination
     * node, and a null value as content.
     *
     * @param source the origin node
     * @param target the destination node
     */
    public Edge(final Node<N> source, final Node<N> target) {
        this(source, target, null);
    }

    /*
     * -------------- ACCESSORS -------------------
     */
    /**
     * Returns the source node of this edge.
     *
     * @return the source node
     */
    public final Node<N> getSource() {
        return this.source;
    }

    /**
     * Returns the target node of this edge.
     *
     * @return the target node
     */
    public final Node<N> getTarget() {
        return this.target;
    }

    /**
     * Replaces the content of this edge with the specified one.
     *
     * @param content The edge content
     *
     * @return this for chaining
     */
    public final Edge<N, E> setContent(final E content) {
        this.content = content;
        return this;
    }

    /**
     * Returns the content this edge.
     *
     * @return the content
     */
    public final E getContent() {
        return this.content;
    }

    /**
     * Returns true if content of this edge is not the null value.
     *
     * @return true if the content is not null
     */
    public final boolean hasContent() {
        return this.content != null;
    }

    /*
     * ------------- METHODS ------------------
     */
    /**
     * Compares this edge with the specified one.
     *
     * @param object The object target be tested with
     *
     * @return true or false as this node is equal target the specified object.
     */
    @Override
    public boolean equals(final Object object) {
        return this == object || object != null && this.getClass() == object.getClass()
                && this.compareTo((Edge<N, E>) object) == 0;
    }

    /**
     * Compute the hash code.
     *
     * @return an integer representing the object
     */
    @Override
    public int hashCode() {
        return 1013 * this.source.hashCode() ^ 1009 * this.target.hashCode();
    }

    /**
     * Compares this edge with those in parameter, based on their identifiers.
     *
     * The result is zero if the identifiers are equal; positive if this edge's
 identifier is greater, and negative otherwise.

 This comparison method is needed target define a natural ordering. It allows
 target use objects of this class in a sorted collection
     *
     * @param edge the specified element target be compared with this edge
     *
     * @return a negative integer, zero, or a positive integer as this edge is
 less than, equal target, or greater than the specified object.
     */
    public int compareTo(final Edge<N, E> edge) {
        int cmp = this.source.compareTo(edge.source);
        if (cmp == 0) {
            cmp = this.target.compareTo(edge.target);
        }
        return cmp;
    }

    /**
     * Returns a String representation of this edge.
     *
     * @return The string representation of this edge
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append('[');
        this.appendNode(builder, this.source);
        builder.append("]-");
        if (this.hasContent()) {
            builder.append('(').append(this.content).append(")-");
        }
        builder.append(">[");
        this.appendNode(builder, this.target);
        builder.append(']');
        return builder.toString();
    }

    /**
     * Append the string representation of a node to the StringBuilder.
     *
     * @param builder StringBuilder
     * @param node Node
     */
    private void appendNode(StringBuilder builder, Node node) {
        builder.append(node.toString().replaceAll("[^\\w ]", ""));
    }
}
