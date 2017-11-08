package org.thegalactic.util;

/**
 * This class gives the minimal representation of a description for complex data.
 * A description is parametrised by any type of elements which are Comparable.
 * A description can be compared with descriptions of the same type.
 * 
 * This class has two abstract methods: 
 *  - {@link #intersection(Description)} returning the intersection of two descriptions.
 *  - {@link #isIncludedIn(Description)} returning true if the current description is 
 *  	included in the description in parameter, else false.
 * 
 * @author Jessie Carbonnel
 *
 * @param <E> the type of the description
 */

public abstract class Description<E extends Comparable<E>> implements Comparable<Description<E>> {
	
	/*
	 * --------------- ATTRIBUTES ---------------
	 */
	/**
	 * The value of the description of type E
	 */
	private E value;
	
	/*
	 * --------------- CONSTRUCTORS ---------------
	 */
	/**
	 * Construct a new description with the value v.
	 * @param v the value of the constructed description.
	 */
	public Description (E v){
		this.value = v;
	}
	
	/*
	 * --------------- ABSTRACT METHODS ---------------
	 */
	
	/**
	 * This method returns the intersection of the current description
	 * and the description in parameter.
	 * The two descriptions should have the same type.
	 * 
	 * @param d the second description.
	 * @return a description of type E representing the intersection of the two initial descriptions.
	 */
	public abstract Description<E> intersection (Description<E> d);
	
	/**
	 * This method returns true if the current description is included in the description in parameter.
	 * Else, it returns false.
	 * The two descriptions should have the same type.
	 * 
	 * @param d the description to be checked.
	 * @return true is the current description is included in the description in parameter, else false.
	 */
	public abstract boolean isIncludedIn (Description<E> d);
	
	/*
	 * --------------- IMPLEMENTED METHODS ---------------
	 */
	/**
	 * This method returns the value of the description.
	 * @return the value of type E of the description.
	 */
	public E getValue (){
		return this.value;
	}
	
	/**
	 * This method replaces the value of the description by the one in parameter.
	 * @param v the new value of the description.
	 */
	public void setValue (E v){
		this.value = v;
	}
	
	/**
	 * This method compares the values of the two descriptions.
	 * @param d the value to be compared.
	 */
	public int compareTo(Description<E> d){
		return this.value.compareTo(d.getValue());
		
	}
}
