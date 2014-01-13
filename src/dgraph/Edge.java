package dgraph;

/*
 * Edge.java
 *
 * last update on January 2014
 */

import java.util.StringTokenizer;

/**
 * This class gives a standard representation for an edge of a graph.
 *
 * An edge is composed of a content, and two nodes
 *
 * - `from`
 * - `to`
 *
 * of class `Node`.
 *
 * This class implements class `Comparable` and provides
 * the  `compareTo()` method that compares the edge with
 * those in parameter by sorting indexes of the nodes that compose it.
 *
 * Therefore, edges can be stored in a sorted collection since they are
 * comparable, and in particular in a sorted set where
 * set operations are provided.
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
public class Edge implements Comparable<Object> {
    /* ------------- FIELDS --------------------- */

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

    /* ------------- CONSTRUCTORS ---------------- */

    /**
     * Constructs a new edge with the specified node as origin and destination node, with the specified content.
     *
     * @param   from     the origin node
     * @param   to       the destination node
     * @param   content  the edge content
     */
    public Edge(final Node from, final Node to, final Object content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    /**
     * Constructs a new edge with the specified node as origin and destination node, and a null value as content.
     *
     * @param   from     the origin node
     * @param   to       the destination node
     */
    public Edge(final Node from, final Node to) {
        this.from = from;
        this.to = to;
        this.content = null;
    }

    /* -------------- ACCESSORS ------------------- */

    /**
     * Returns the origin node of this edge.
     *
     * @return  the origin node
     */
    public Node from() {
        return this.from;
    }

    /**
     * Returns the destination node of this edge.
     *
     * @return  the destination node
     */
    public Node to() {
        return this.to;
    }

    /**
     * Replaces the content of this edge with the specified one.
     *
     * @param   content  The edge content
     *
     * @return  this for chaining
     */
    public Edge setContent(final Object content) {
        this.content = content;
        return this;
    }

    /**
     * Returns the content this edge.
     *
     * @return  the content
     */
    public Object content() {
        return this.content;
    }

    /**
     * Returns true if content of this edge is not the null value.
     *
     * @return  true if the content is not null
     */
    public boolean hasContent() {
        return this.content != null;
    }

    /* ------------- METHODS ------------------ */

    /**
     * Compares this edge with those in parameter, based on their identifiers.
     *
     * The result is zero if the identifiers are equal; 1 if this edge's identifier is greater,
     * and -1 otherwise.
     *
     * This comparison method is needed to define a natural ordering.
     * It allows to use objects of this class in a sorted collection
     *
     * @param   object  the specified element to be compared with this edge
     *
     * @return  a negative integer, zero, or a positive integer as this edge is less than, equal to, or greater than the specified object.
     */
    public int compareTo(final Object object)  {
        if (!(object instanceof Edge)) {
            return -1;
        }
        Edge edge = (Edge) object;
        if (this.from.identifier() == edge.from().identifier() && this.to.identifier() == edge.to().identifier()) {
            return 0;
        }
        if (this.from.identifier() < edge.from().identifier()
            || this.from.identifier() == edge.from().identifier() && this.to.identifier() < edge.to().identifier()) {
            return -1;
        }
        return 1;
    }

    /**
     * Returns a String representation of this edge.
     *
     * @return  The string representation of this edge
     */
    public String toString() {
        String string = this.from.toString() + "->" + this.to.toString();
        if (this.hasContent()) {
            string = string + "(" + this.content + ")";
        }
        return string;
    }

    /**
     * Returns the dot description of this edge in a String.
     *
     * @return  The dot representation of this edge
     */
    public String toDot() {
        String dot = this.from.identifier() + "->" + this.to.identifier();
        if (this.hasContent()) {
            dot = dot + " [" + "label=\"";
            StringTokenizer tokenizer = new StringTokenizer(this.content.toString(), "\"");
            while (tokenizer.hasMoreTokens()) {
                dot += tokenizer.nextToken();
            }
            dot = dot + "\"]";
        }
        return dot;
    }
}

