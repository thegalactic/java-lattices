/*
 * Node.java
 *
 * Copyright: 2013 University of La Rochelle, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 *
 * @author Karell Bertet
 * @version 2014
 */

package dgraph;

import java.util.StringTokenizer;

/**
 * This class gives a standard representation for a node of a graph.
 *
 * A node is composed of a `content` and an
 * automatically computed and unique index.
 *
 * This class implements the `Comparable` interface aiming at
 * sorting nodes by providing the {@link #compareTo} method
 * that compares the node with those in parameter by comparing their indexes.
 *
 * Since nodes are comparable, they can be stored in a sorted collection,
 * and in particular in a sorted set where set operations are provided.
 *
 * ![Node](Node.png)
 *
 * @uml Node.png
 * !include src/dgraph/Node.iuml
 *
 * hide members
 * show Node members
 * class Node #LightCyan
 * title Node UML graph
 *
 * @todo  Do we implement mutator for content?
 */
public class Node implements Comparable<Object> {
    /* ------------- FIELDS --------------------- */

    /**
     * An uniquely defined identifier for this node.
     */
   private int identifier;

    /**
     * An object to store information about the element.
     */
   private Object content;

    /**
     * The total number of nodes.
     *
     * Initialized to 0, it is incremented by the constructor,
     * and used to inialize the identifier.
     */
   private static int count = 0;

    /* ------------- CONSTRUCTORS ------------------ */

    /**
     * Constructs a new node containing the specified content.
     *
     * Identifier of this node is initalized with the `count`
     * variable which is the incremented.
     *
     * @param   content  Content for this node
     */
    public Node(final Object content) {
        this.identifier = ++count;
        this.content = content;
    }

    /**
     * Constructs a new node with a null content.
     *
     * Identifier of this node is initalized with the `count`
     * variable which is the incremented.
     */
    public Node() {
        this.identifier = ++count;
        this.content = null;
    }

    /**
     * Constructs this node as a copy of the specified `node`, with the same index.
     *
     * @param   node  the node to be copied
     *
     * @todo  Is this constructor usefull since it breaks identifier unicity
     */
    public Node(final Node node) {
        this.identifier = node.identifier;
        this.content = node.content;
    }

    /* -------------- ACCESSORS ------------------- */

    /**
     * Get the identifier.
     *
     * @return  the node identifier
     */
    public int getIdentifier() {
        return this.identifier;
    }

    /**
     * Get the content.
     *
     * @return  the node content
     */
    public Object getContent() {
        return this.content;
    }

    /* --------------- SAVING METHOD ------------ */

    /**
     * Returns a string representation of this node without spaces.
     *
     * @return  the string representation
     *
     * @todo  Why do we need to remove spaces?
     */
     public String toString() {
        String string = "";
        if (this.content == null) {
            string = this.identifier + "";
        } else {
            string = this.content.toString();
        }
        StringTokenizer tokenizer = new StringTokenizer(string);
        string = "";
        while (tokenizer.hasMoreTokens()) {
            string += tokenizer.nextToken();
        }
        return string;
    }

    /**
     * Returns the dot description of this node in a String.
     *
     * @return  the dot description
     */
    public String toDot() {
        String dot = this.identifier + " [label=\"";
        StringTokenizer tokenizer = new StringTokenizer(this.toString(), "\"");
        while (tokenizer.hasMoreTokens()) {
            dot += tokenizer.nextToken();
        }
        dot += "\"]";
        return dot;
    }

    /* --------------- OVERLAPPING METHODS ------------ */

    /**
     * Returns a copy of this node.
     *
     * @return  a clone of this node
     */
    public Node copy() {
        return new Node(this.content);
    }

    /**
     * Compares this node with the specified one.
     *
     * @param  object  The object to be tested with
     *
     * @return  true or false as this node is equal to the specified object.
     */
    public boolean equals(final Object object) {
        if (!(object instanceof Node)) {
            return false;
        }
        Node node = (Node) object;
        return this.identifier == node.identifier;
    }

    /**
     * Compute the hash code.
     *
     * @return  an integer representing the object
     */
    public int hashCode() {
        return this.identifier;
    }

   /**
    * Compares this node with those in parameter, based on their identifiers.
    *
    * The result is zero if the identifiers are equal; 1 if this node's identifier is greater, and -1 otherwise.
    * This comparison method is needed to define a natural ordering.
    * It allows to use objects of this class in a sorted collection.
    *
    * @param   object  the specified element to be compared with this node
    *
    * @return  a negative integer, zero, or a positive integer as this node is less than, equal to, or greater than the specified object.
    */
    public int compareTo(final Object object) {
        if (!(object instanceof Node)) {
            return -1;
        }
        int id = ((Node) object).identifier;
        if (this.identifier > id) {
            return 1;
        } else if (this.identifier == id) {
            return 0;
        } else {
            return -1;
        }
    }
}

