package org.thegalactic.dgraph;

/*
 * Node.java
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
 * This class gives a standard representation for a node of a graph.
 *
 * A node is composed of a `content` and an automatically computed and unique
 * index.
 *
 * This class implements the `Comparable` interface aiming at sorting nodes by
 * providing the {@link #compareTo} method that compares the node with those in
 * parameter by comparing their indexes.
 *
 * Since nodes are comparable, they can be stored in a sorted collection, and in
 * particular in a sorted set where set operations are provided.
 *
 * ![Node](Node.png)
 *
 * @param <N> the content type
 *
 * @uml Node.png !include resources/org/thegalactic/dgraph/Node.iuml
 *
 * hide members show Node members class Node #LightCyan title Node UML graph
 */
public class Node<N> implements Comparable<Node<N>>, Cloneable {

    /*
     * ------------- FIELDS ---------------------
     */
    /**
     * An uniquely defined identifier for this node.
     */
    private int identifier;

    /**
     * An object to store information about the element.
     */
    private N content;

    /**
     * The total number of nodes.
     *
     * Initialised to 0, it is incremented by the constructor, and used to
     * inialize the identifier.
     */
    private static int count;

    /*
     * ------------- CONSTRUCTORS ------------------
     */
    /**
     * Constructs a new node containing the specified content.
     *
     * Identifier of this node is initalized with the `count` variable which is
     * the incremented.
     *
     * @param content Content for this node
     */
    public Node(final N content) {
        this.identifier = ++count;
        this.content = content;
    }

    /**
     * Constructs a new node with a null content.
     *
     * Identifier of this node is initalized with the `count` variable which is
     * the incremented.
     */
    public Node() {
        this(null);
    }

    /*
     * -------------- ACCESSORS -------------------
     */
    /**
     * Get the identifier.
     *
     * @return the node identifier
     */
    public final int getIdentifier() {
        return this.identifier;
    }

    /**
     * Get the content.
     *
     * @return the node content
     */
    public final N getContent() {
        return this.content;
    }

    /**
     * Set the content.
     *
     * @param content Content for this node
     *
     * @return this for chaining
     */
    public final Node setContent(final N content) {
        this.content = content;
        return this;
    }

    /**
     * Test if this node has a content.
     *
     * @return true if this node has a content
     */
    public final boolean hasContent() {
        return this.content != null;
    }

    /*
     * --------------- SAVING METHOD ------------
     */
    /**
     * Returns a string representation of this node without spaces.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        String string;
        if (this.content == null) {
            string = String.valueOf(this.identifier);
        } else {
            string = this.content.toString();
        }
        return string;
    }

    /*
     * --------------- OVERRIDDEN METHODS ------------
     */
    /**
     * Returns a clone of this node.
     *
     * @return a clone of this node
     *
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Node clone() throws CloneNotSupportedException {
        final Node node = (Node) super.clone();
        node.identifier = ++count;
        return node;
    }

    /**
     * Compares this node with the specified one.
     *
     * @param object The object to be tested with
     *
     * @return true or false as this node is equal to the specified object.
     */
    @Override
    public boolean equals(final Object object) {
        return this == object || object != null && this.getClass() == object.getClass()
                && this.identifier == ((Node) object).identifier;
    }

    /**
     * Compute the hash code.
     *
     * @return an integer representing the object
     */
    @Override
    public int hashCode() {
        return this.identifier;
    }

    /**
     * Compares this node with those in parameter, based on their identifiers.
     *
     * The result is zero if the identifiers are equal; positive if this node's
     * identifier is greater, and negative otherwise. This comparison method is
     * needed to define a natural ordering. It allows to use objects of this
     * class in a sorted collection.
     *
     * @param node the specified element to be compared with this node
     *
     * @return a negative integer, zero, or a positive integer as this node is
     *         less than, equal to, or greater than the specified object.
     */
    public final int compareTo(final Node node) {
        return this.identifier - node.identifier;
    }
}
