package org.thegalactic.util;

/*
 * Couple.java
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
 * This class provides a representation of couple (A,B) of objects.
 *
 * ![Couple](Couple.png)
 *
 * @uml Couple.png
 * !include resources/org/thegalactic/util/Couple.iuml
 *
 * class Couple #LightCyan
 * title Couple UML graph
 * @author jeff
 */
public class Couple {

    /**
     * Left hand side of this component.
     */
    private Object left;

    /**
     * Right hand side of this component.
     */
    private Object right;

    /**
     * Constructor from two objects.
     *
     * @param l left hand side of this component
     * @param r right hand side of this component
     */
    public Couple(Object l, Object r) {
        this.left = l;
        this.right = r;
    }

    /**
     * Returns left hand side of this component.
     *
     * @return left hand side of this component
     */
    public Object getLeft() {
        return left;
    }

    /**
     * Set left hand side of this component.
     *
     * @param left hand side of this component
     *
     * @return this for chaining
     */
    public Couple setLeft(Object left) {
        this.left = left;
        return this;
    }

    /**
     * Returns right hand side of this component.
     *
     * @return right hand side of this component
     */
    public Object getRight() {
        return right;
    }

    /**
     * Set right hand side of this component.
     *
     * @param right hand side of this component
     *
     * @return this for chaining
     */
    public Couple setRight(Object right) {
        this.right = right;
        return this;
    }

    /**
     * Returns true if c is equals to this component.
     *
     * @param c Couple tested with this component
     *
     * @return true if c is equals to this component.
     */
    public boolean equals(Couple c) {
        return (this.left == c.getLeft()) && (this.right == c.getRight());
    }

    /**
     * Compute the hash code.
     *
     * @return an integer representing the object
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Returns a string representations of this component.
     *
     * @return a string representations of this component.
     */
    @Override
    public String toString() {
        return "(" + this.left.toString() + "," + this.right.toString() + ")";
    }
}
