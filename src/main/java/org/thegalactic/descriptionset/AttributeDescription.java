package org.thegalactic.descriptionset;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 * This class implements descriptions of binary attribute sets.
 * 
 * A set of binary attributes is a description of type DescritionType.attributeSet.
 * Type type of the description is a sorted set of String, representing the binary attributes.
 * 
 * The attributes in the set are ordered based on the lexicographic order on the attributes' name.
 * (default compareTo of String)
 * 
 * @author Jessie Carbonnel
 *
 */
public class AttributeDescription extends Description<SortedSet<String>> {

		/*
		 * ------------ CONSTRUCTORS ------------------
		 */
	
	/**
	 * Constructs a new AttributeDescription with the value v.
	 * 
	 * @param v the value of the new AttributeDescription.
	 */
	public AttributeDescription(SortedSet<String> v){
		super(DescriptionType.attributeSet,v);
	}
	
	/**
	 * Constructs a new AttributeDescription without specifying a value.
	 */
	public AttributeDescription(){
		super(DescriptionType.attributeSet);
	}

		/*
		 * ------------- INHERITED METHODS ------------
		 */
	
	@Override
	/**
	 * Binary attribute sets form a poset.
	 * The order is based on lexicographic order of attribute sets
	 * 
	 * @param d an AttributeDescription.
	 * @return a negative integer, zero, or a positive integer as this AttributeDescription
     *         is less than, equal to, or greater than the specified one.
	 */
	public int compareTo(Description<SortedSet<String>> d) {
		
		// if the two sets have the same elements, then returns 0
		if(this.getValue().containsAll(d.getValue()) && d.getValue().containsAll(this.getValue())){
			return 0;
		}
		
		// if the two sets are not equal but have the same size
		else if(this.getValue().size() == d.getValue().size()){
			
			SortedSet<String> set1 = new TreeSet<String>();
			set1.addAll(this.getValue());
			
			SortedSet<String> set2 = new TreeSet<String>();
			set2.addAll(d.getValue());
			
			// for each attribute of the specified AttributeDescription
			while(set2.size() < 0){
				
				// we compare the first attributes of the two sets
				int res = set1.first().compareTo(set2.first());
				
				// if the two attributes are equals, repeat on the two following attributes
				if(res == 0){
					set1 = set1.headSet(set1.first());
					set2 = set2.headSet(set2.first());
				}
				
				// else returns the results
				else{
					return res;
				}
			}
			return 0;
		}
		
		// if the current set is smaller than the specified one, then returns -1
		else if(this.getValue().size() < d.getValue().size()){
			return -1;
		}
		
		// if the current set is larger, then return 1
		else return 1;
	}

	@Override
	/**
	 * Takes a binary attributes set represented by a String
	 * and initialises the AttributeDescription's value accordingly.
	 * 
	 * @param s the description in String format
	 */
	public void initFromDescriptionSetContext(String s) {
		this.setValue(new TreeSet<String>());
		
		for(String att : s.split(" ")){
			 this.getValue().add(att);
		 }
	}
	
	@Override
	/**
	 * Compares the two AttributeDescriptions
	 * 
	 * @return true if the two AttributeDescriptions have the same value.
	 */
	public boolean equals(Object o){
		if(this.getValue().equals(((AttributeDescription)o).getValue())){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
}
