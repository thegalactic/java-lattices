package org.thegalactic.descriptionset;

/**
 * This class gives the minimal representation of the description of a piece data.
 * This class encapsulates a piece of data of any type, the type being characterised by E.
 * 
 * A description ensures to return a value of type E representing the data,
 * to provide a String representing its type ,
 * and to determine a total order over descriptions of the same type. 
 * 
 * The elements of a given type may not form a total order, so it needs to be defined.
 * 
 * @author Jessie Carbonnel
 *
 * @param <E> the type of the description
 */

public abstract class Description<E> implements Comparable<Description<E>> {
	
		/*
		 * --------------- ATTRIBUTES ---------------
		 */
	
	/**
	 * The value of the description of type E
	 */
	private E value;
	
	/**
	 * The type of the description
	 */
	private DescriptionType type;
	
		/*
		 * --------------- CONSTRUCTORS ---------------
		 */
	
	/**
	 * Constructs a new description with the value v and of type t.
	 * 
	 * @param t the type of the new description.
	 * @param v the value of the new description.
	 */
	public Description (DescriptionType t, E v){
		this.type = t;
		this.value = v;
	}
	
	/**
	 * Constructs a new description of type t, without specifying a value.
	 * 
	 * @param t the type of the new description.
	 */
	public Description (DescriptionType t){
		this.type = t;
	}
	
		/* 
		 * -------- FACTORY-----------
		 */
	
	/**
	 * Takes a type of description in String format and return the corresponding instance.
	 * This methods has to be updated for each newly implemented description type.
	 * 
	 * @param type the type of the description
	 * @return an instance of the description with the specified type
	 * 			or null if the specified type is unknown.
	 * 
	 */
	public static Description create(String type){
		if(type.equals("attribute")){
			return new AttributeDescription();
		}
		else{
			System.err.println("Unknow description type: "+type);
			return null;
		}
	}
	
		/*
		 * --------------- ABSTRACT METHODS ---------------
		 */
	
	/**
	 * Compares the values of the two descriptions.
	 * The defined order can be arbitrary, based on a poset, or based on the compareTo() of E.
	 * 
	 * @param d a value of the same type.
	 * @return a negative integer, zero, or a positive integer as this description
     *         is less than, equal to, or greater than the specified one.
	 */
	public abstract int compareTo(Description<E> d);
	
	/**
	 * Initialises the description value depending on the specified String.
	 * The String should correspond to the description as extracted from a file representing a DescriptionSetContext
	 * 
	 * @param s the description in String format
	 */
	public abstract void initFromDescriptionSetContext(String s);
	
	
		/*
		 * --------------- IMPLEMENTED METHODS ---------------
		 */
	
	/**
	 * Returns the value of the description.
	 * 
	 * @return the value of the description.
	 */
	public E getValue(){
		return this.value;
	}
	
	/**
	 * Replaces the value of the description by the specified one.
	 * 
	 * @param v the new value of the description.
	 */
	public void setValue(E v){
		this.value = v;
	}
	
	/**
	 * Returns the type of the description.
	 * 
	 * @return the description's type.
	 */
	public DescriptionType getType(){
		return this.type;
	}
	
	/**
	 * Replaces the type of the description by the specified one.
	 * 
	 * @param t the new type of the description.
	 */
	public void setType(DescriptionType t){
		this.type = t;
	}
	
	/**
	 * Returns a String representing the value of the description.
	 * 
	 * @return a String representing the description.
	 */
	public String toString(){
		return value.toString();
	}
}







