package dgraph;

/*
 * Edge.java
 *
 * last update on December 2013
 *
 */
import java.util.StringTokenizer;
/**
 * This class gives a standard representation for an edge of a graph.
 * <p>
 * An edge is composed of a content, and two nodes <code>from</code> and <code>to</code> of class <code>Node</code>.
 * <p>
 * This class implements class <code>Comparable</code> and provides
 * the  <code>compareTo()</code> method that compares the component with
 * those in parameter by sorting indexes of the nodes that compose it.
 * <p>
 * Therefore, edges can be stored in a sorted collection since they are
 * comparable, and in particular in a sorted set where 
 * set operations are provided.
 * <p>
 * Copyright: 2013 University of La Rochelle, France
 * @license: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 * @author Karell Bertet
 * @version 2013
 */
public class Edge implements Comparable {
    /* ------------- FIELDS --------------------- */
    /** The first node of the edge **/
    private final Node from;
    /** The second node of the edge **/
    private final Node to;
    /** An object to store information about this edge.**/
    private Object content;

    /* ------------- CONSTRUCTORS ---------------- */
    /** Constructs a new edge with the specified node as first and second node, and a null value as content **/
    public Edge (Node from, Node to) {
    	this.from = from;
        this.to = to;
        this.content = null;
    }
    /** Constructs a new edge with the specified node as first and second node, with the specified content **/
    public Edge (Node from, Node to, Object content) {
    	this.from = from;
        this.to = to;
        this.content = content;
    }
    /* -------------- ACCESSORS ------------------- */
    /** Returns the first node of this component **/
    public Node from (){
        return this.from;
    }
    /** Returns the second node of this component **/
    public Node to (){
        return this.to;
    }
    /** Replaces the content of this component with the specified one **/
    public void setContent (Object content){
        this.content=content;
    }
    /** Returns the content this component **/
    public Object content (){
        return this.content;
    }
    /** Returns true if content of this component is not the null value. **/
    public boolean hasContent (){
        return (this.content!=null);
    }

    /* ------------- METHODS ------------------ */

    /** Compares this component with those in parameter, based on their identifiers.
	* The result is zero if the identifiers are equal; 1 if this component's identifier is greater,
	* and -1 otherwise
	* This comparison method is needed to define a natural ordering.
	* It allows to use objects of this class in a sorted collection
	* @param O the specified element to be compared with this component
	* @return a negative integer, zero, or a positive integer as this component is less than,
	* equal to, or greater than the specified object.
	*/
    public int compareTo (Object O)  {  
    	Edge A = (Edge) O ;  // C'est Edge qui est entre
    	if (this.from.ident == A.from().ident && this.to.ident == A.to().ident)
            return 0;
        if (this.from.ident < A.from().ident ||
            this.from.ident == A.from().ident && this.to.ident < A.to().ident)
	    return -1;    
	return 1;
    }
    /** Returns a String representation of this component */
    public String toString () { 
        String ed = this.from.toString()+"->"+this.to.toString();
        if (this.hasContent())
            ed = ed+"("+this.content+")";
        return ed;
    }
	/** Returns the dot description of this component  in a String */
    public String toDot () {
        String s = this.from.ident+"->"+this.to.ident;
        if (this.hasContent()) {
            s = s+" ["+"label=\"";
            StringTokenizer st = new StringTokenizer (this.content.toString(),"\"");
            while (st.hasMoreTokens())
				s+=st.nextToken();
            s = s+"\"]";
        }
		return s;
    }
}// end of Edge
