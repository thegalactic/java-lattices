package fr.kbertet.lattice;
/*
 * Permutation.java
 *
 * Copyright: 2013-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

/**
 * This class provides a representation of permutations.
 *
 * If this component transforms : 0 -> 1, 1 -> 0 & 2 -> 2.
 * Then its length is 3 and
 *
 * The content contains :
 *
 * ~~~
 * content[0]=1
 * content[1]=0
 * content[2]=2
 * ~~~
 *
 * ![Permutation](Permutation.png)
 *
 * @uml Permutation.png
 * !include src/fr/kbertet/lattice/Permutation.iuml
 *
 * class Permutation #LightCyan
 * title Permutation UML graph
 * @author jeff
 */
public class Permutation {
    /**
     * This component is a permutation of 0..length-1.
     */
    private int length;

    /**
     * The transformation represented by this component.
     *
     * If this component transforms : 0 -> 1, 1 -> 0 & 2 -> 2.
     * The field content contains :
     *
     * ~~~
     * content[0]=1
     * content[1]=0
     * content[2]=2
     * ~~~
     */
    private int[] content;

    /**
     * Constructs identity of the set 0..n-1.
     *
     * @param   n  permutation of the set 0..n-1.
     */
    public Permutation(int n) {
        this.length = n;
        this.content = new int[n];
        for (int i = 0; i < n; i++) {
            this.content[i] = i;
        }
    }

    /**
     * Returns the transformation coded by this component.
     *
     * @return  the transformation coded by this component.
     */
    public int[] getContent() {
        return this.content;
    }

    /**
     * Set the transformation coded by this component.
     *
     * Length of this component is update by this method.
     *
     * @param   c  the transformation coded in this component.
     */
    public void setContent(int[] c) {
        this.content = c;
        this.length = c.length;
    }

    /**
     * Return length of this component.
     *
     * @return  length of this component.
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Set length of this componenet.
     *
     * @param   l  length of this component.
     *
     * @return  true if update is successful.
     */
    public boolean setLength(int l) {
        if ((this.content.length == l) && (l <= this.getLength())) {
            this.length = l;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of this component.
     *
     * @return  a string representation of this component.
     */
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.length; i++) {
            str = str + this.content[i];
        }
        return str;
    }

    /**
     * Returns true if this component is equal to s.
     *
     * @param   s  test if this component is equal to s
     *
     * @return  true if this component is equal to s
     */
    public boolean equals(Permutation s) {
        if (!(s.getLength() == this.length)) {
            return false;
        } else {
            boolean tmp = true;
            for (int i = 0; i < this.length; i++) {
                tmp = tmp && (this.content[i] == s.getContent()[i]);
            }
            return tmp;
        }
    }

    /**
     * Compute the hash code.
     *
     * @return  an integer representing the object
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
