package dgraph;

/*
 * Node.java
 *
 * last update on March 2010
 *
 */

import java.util.StringTokenizer;
/**
 * This class gives a standard representation for a node of a graph.
 *
 * A node is composed of a `content` and an
 * automatiquely and unique index.
 *
 * This class implements class <code>Comparable</code> aiming at
 * sorting nodes by providing the <code>compareTo()</code> method
 * that compares the component with those in parameter by comparing their indexes.
 *
 * Since components are comparable, they can be stored in a sorted collection,
 * and in particular in a sorted set where set operations are provided.
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
public class Node implements Comparable {

	/* ------------- CONSTRUCTORS ------------------ */	
	
	/** An uniquely defined identifier for this component*/
   public final int ident;
	/** An object to store information about the element */
   public final Object content;
	/** The total number of components. <p>
	* Initialized to 0, it is incremented by the constructor,
	* and used to inialize the identifier. */
   protected static int count = 0;

   /* ------------- CONSTRUCTORS ------------------ */

    /** Constructs a new node containing the specified object <p>
	* Ident of this component is initalized with the </code>count<code>
	* variable wich is the incremented */
    public Node (Object content) {
        this.ident = ++count; this.content = content; }
	/** Constructs a new node with a null content <p>
    * Ident of this component is initalized with the </code>count<code>
	* variable wich is the incremented */
    public Node() { 
	this.ident = ++count; this.content = null; }
	/**  Constructs this component as a copy of the specified node </code>n<code>, 
	* with the same index.  
	* @param n the node to be copied */
    public Node (Node n) { 
	this.ident = n.ident; this.content = n.content; }

	/* --------------- SAVING METHOD ------------ */

	/** Returns a String representation of this component without spaces */
 	public String toString () {
        String s = "";
		if (this.content==null) s = this.ident+"";
		else s = this.content.toString();
        StringTokenizer st = new StringTokenizer (s);
        s = "";
		while (st.hasMoreTokens())
				s+=st.nextToken();
        return s;
	}
	/** Returns the dot description of this component  in a String */
	public String toDot () {
		String s = this.ident+" [label=\""  ;
		StringTokenizer st = new StringTokenizer (this.toString(),"\"");
		while (st.hasMoreTokens()) 
				s+=st.nextToken();
		s += "\"]";
		return s;
	}

	/* --------------- OVERLAPPING METHODS ------------ */

  /** Returns a copy of this component **/
    public Node copy() {
        return new Node(this.content);
    }

    /** Compares this component with the specified one
	* @return true or false as this componant is equals to the specified object.
	*/
    public boolean equals (Object o) {
        if (!(o instanceof Node)) return false;
        Node c = (Node) o;
        return (this.ident == c.ident);
    }
   /** Compares this component with those in parameter, based on their identifiers.
	* The result is zero if the identifiers are equal; 1 if this component's identifier is greater,
	* and -1 otherwise
	* This comparison method is needed to define a natural ordering.
	* It allows to use objects of this class in a sorted collection
	* @param o the specified element to be compared with this component
	* @return a negative integer, zero, or a positive integer as this component is less than,
	* equal to, or greater than the specified object.
	*/
   public int compareTo (Object o) {       
      if (!(o instanceof Node)) return -1;
      int id =  ((Node)o).ident;
      return (this.ident > id)?1:((this.ident == id)?0:-1);
   }
}// end of Node
