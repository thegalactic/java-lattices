package lattice.lattice;

/*
 * ComparableSet.java
 *
 * last update on March 2010
 *
 */

import java.util.StringTokenizer;
import java.util.TreeSet;
/**
* This class gives a minimal representation of a comparable set where sets
* are compared using the lectic order.
* <p>
* This class extends class <code>TreeSet</code>, implements class <code>Comparable</code> and provides
* a <code>compareTo</code> method that implements the lectic order between two sets. 
* Therefore, a comparable set can be stored in a sorted collection, and in particular in a sorted set where
* set operations are provided.
* <p>
* The lectic order extends the inclusion, and is defined only for comparable elements,
* i.e. elements that can be sorted, as follows:<p>
* "a set A is smaller than a set B iff there exists an element in B\A
* such that any smaller element belonging to A also belongs to B."
* <p> 
 * <img src="..\..\..\images\lgpl.png" height="20" alt="lgpl"/>
 * Copyright 2010 Karell Bertet<p>
 * This file is part of lattice.
 * lattice is free package: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with lattice.  If not, see <a href="http://www.gnu.org/licenses/" target="_blank">license</a>
*
* @author  Karell Bertet
* @version 2010
 */
public class ComparableSet extends TreeSet implements Comparable, Cloneable
{

	/* ------------- CONSTRUCTORS ------------------ */
	/** Constructs a new and empty ComparableSet
	 */
	public ComparableSet () {
		super();
	}
    /** Constructs a new ComparableSet with the set from the specified set
	 * @param S a comparable set
	 */
	public ComparableSet (TreeSet<Comparable> S) {
		super(S);
	}

	/* --------------- OVERLAPPING METHODS ------------ */

	/** Returns a String representation of this component without spaces */
 	public String toFile () {
		String res="";
		StringTokenizer st = new StringTokenizer(this.toString());
		while (st.hasMoreTokens())
			res+=st.nextToken();
		return res;
	}
	/** Compares this component with the specified one
	* @return true or false as this componant is equals to the specified object.
	*/
    public boolean equals (Object o) {
        //if (!(o instanceof lattice.ComparableSet)) return false;
        if (!(o instanceof ComparableSet)) return false;
        ComparableSet A = (ComparableSet) o;
        return (super.equals(A));
    }

    /** Returns a copy of this component **/
    public ComparableSet copy() {
        return new ComparableSet((TreeSet)super.clone());
    }


   /** Compares this component with those in parameter according to the lectic order.<p>
	* The lectic order defines a sort on sets of elements extending the inclusion order
    * as follows:<p>
    * A set A is smaller than a set B iff there exists an element in B\A
    * such that any smaller element belonging to A also belongs to B.
    * The result is zero if the identifiers are equal; 1 if this component's identifier is greater,
	* and -1 otherwise. 
	* This comparison method is needed to define a natural and total sort on a sets.
	* It allows to use sets of this class in a sorted collection
	* @param o the specified element to be compared with this component
	* @return a negative integer, zero, or a positive integer as this component is less than,
	* equal to, or greater than the specified object according to the lectic order.
	*/    
   public int compareTo (Object o) {
        if (!(o instanceof ComparableSet)) return -1;
        // case of equality between this component and o
        if (this.equals(o)) return 0;
        // test if this component is smaller than B by lectic order
        ComparableSet B = (ComparableSet) o;
        // computes index i of the first element in B minus A
        // if i doesn't exist, then this component is not smaller than B by lectic order
		TreeSet<Comparable> BminusA = new TreeSet(B);
        BminusA.removeAll(this);
        if (BminusA.isEmpty()) return 1;
        Comparable i = BminusA.first();
        // compute this inter {1, ...,i-1}
        TreeSet AiMinus1 = new TreeSet();
        for (Object c : this)
            if (i.compareTo(c)>=1) AiMinus1.add(c);
        // compute B inter {1, ...,i-1}
        TreeSet BiMinus1 = new TreeSet();
        for (Object c : B)
            if (i.compareTo(c)>=1) BiMinus1.add(c);
        // if Aiminus1 and Biminus1 are equal then this component is smaller than B by lectic order
        if  (AiMinus1.equals(BiMinus1))
            return -1;
        else
            return 1;
		}
}// end of ComparableSe