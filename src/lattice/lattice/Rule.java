package lattice.lattice;

/*
 * Rule.java
 *
 * last update on March 2010
 *
 */
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.TreeSet;
/**
* This class gives a representation for an implicational rule.
* <p>
* A rule is composed of a premise and a conclusion that are comparable sets,
* i.e. sets of elements that can be sorted by the lectic order defined
* by class <code>ComparableSet</code>.
* <p>
* This class implements class <code>Comparable</code> aiming at
* sorting rules by providing the <code>compareTo()</code> method.
* Comparison between this component and those in parameter is realised by sorting their premises (lecticaly order),
* or their conclusion in case of equality of the premises.
* <p>
* The coherence of the lectically sort between rules isn't ensured in case of modification
* of the rule. Therefore, it is strongly advised to replace each modified rule by a new one.
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
 * @author Karell Bertet
 * @version 2010
*/
public class Rule implements Comparable
{
	/* ------------- FIELDS ------------------ */		
	/** The premise of the rule */
	private ComparableSet premise;
	/** The conclusion of the rule */
	private ComparableSet conclusion;

	/* ------------- CONSTRUCTORS ------------------ */		
	/** Constructs a new empty Rule with a empty premise and an empty conclusion */
	public Rule() {
		premise = new ComparableSet();
		conclusion = new ComparableSet();
	}
	/** Constructs a new Rule with the premise and the conclusion given in parameters.
	 * @param premise a set of indexed elements
	 * @param conclusion a set of indexed elements
	 */
	public Rule (TreeSet<Comparable> premise, TreeSet<Comparable> conclusion) {
		this.premise = new ComparableSet (premise);
		this.conclusion = new ComparableSet (conclusion);
	}
	
	/* ------------- ACCESSORS METHODS ------------------ */		
	/** Returns a copy of the premise of the Rule */
	public TreeSet<Comparable> getPremise() {return this.premise;}
	/** Returns a copy of the conclusion of the Rule*/
	public TreeSet<Comparable> getConclusion() {return this.conclusion;}

	/* ------------- MODIFICATION METHODS ------------------ */		
	/** Adds the specified comparable to the premise of this component */
	public boolean addToPremise(Comparable a) {
		return this.premise.add(a);}
	/** Removes the specified comparable from the premise of this component */
	public boolean removeFromPremise(Comparable a){
		return this.premise.remove(a);}
	/** Adds the specified element to the conclusion of this component */
	public boolean addToConclusion(Comparable a){
		return this.conclusion.add(a);}
	/** Removes the specified comparable from the conclusion of this component */
	public boolean removeFromConclusion(Object a) {
		return this.conclusion.remove(a);}
	/** Adds the specified collection of indexed elements to the premise of this component */
	public boolean addAllToPremise(Collection<Comparable> A){
		return this.premise.addAll(A);}
	/** Removes the specified collection of indexed elements from the premise of this component */
	public boolean removeAllFromPremise(Collection<Comparable> A){
		return premise.removeAll(A);}
	/** Adds the specified collection of indexed elements to the conclusion of this component */
	public boolean addAllToConclusion(Collection<Comparable> A){
		return conclusion.addAll(A);}
	/** Removes the specified collection of indexed elements from the conclusion of this component */
	public boolean removeAllFromConclusion(Collection<Comparable> A){
		return conclusion.removeAll(A);}
	
	/* ------------- OVERLAPING METHODS ------------------ */		
	/** Returns a String representation of this component.
     * The following format is used: <p>
     * [elements of the premise separated by a space] -> [elements of the conclusion separated by a space]
     * a StringTokenizer is used to delete spaces in the
     * string description of each element of premise and conclusion
     */
	public String toString() {
        String s = "";
        for (Object e : this.getPremise()) {
            StringTokenizer st = new StringTokenizer(e.toString());
            while (st.hasMoreTokens())
                s+=st.nextToken();
            s+= " ";
            }
        s+=" -> ";
        for (Object e : this.getConclusion()) {
            StringTokenizer st = new StringTokenizer(e.toString());
            while (st.hasMoreTokens())
                s+=st.nextToken();
            s+= " ";
            }
        return s;
    }
		
	/** Returns a String representation of this component to be saved in a file. <p>
	* Element in a set are separated by a space */
	public String toFile(){
		String res = "";
		for (Object e : this.premise ) res+=e.toString();
		res+="-> ";
		for (Object e : this.conclusion ) res+=e.toString();
		return res;
		}
			
	/** Compares this component with the specified one .
	* @return true or false as this componant is equals to the specified object.
	*/
	public boolean equals (Object o){
        if (!(o instanceof Rule)) return false;
		Rule r = (Rule)o;
		return (this.getPremise().equals(r.getPremise()) && this.getConclusion().equals(r.getConclusion()));
		}
	/** Compares this component with the specified one by comparing their premises,
    * or their conclusion in case of equality of the premises.
	* @return a negative integer, zero, or a positive integer as this component is less than,
	* equal to, or greater than the specified object.
	*/
	public int compareTo(Object o){
		Rule r = (Rule)o;
        ComparableSet thisPremise = (ComparableSet)this.getPremise();
        ComparableSet thisConclusion = (ComparableSet)this.getConclusion();
        if (thisPremise.compareTo(r.getPremise())==0)
            return (thisConclusion.compareTo(r.getConclusion()));
        else return (thisPremise.compareTo(r.getPremise()));
		}
}// end of Rule