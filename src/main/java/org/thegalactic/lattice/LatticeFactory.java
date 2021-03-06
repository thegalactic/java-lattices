package org.thegalactic.lattice;

/*
 * LatticeFactory.java
 *
 * Copyright: 2010-2015 Karell Bertet, France
 * Copyright: 2015-2016 The Galactic Organization, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices.
 * You can redistribute it and/or modify it under the terms of the CeCILL-B license.
 */
import java.util.BitSet;
import java.util.Iterator;

import org.thegalactic.dgraph.DAGraph;
import org.thegalactic.dgraph.DAGraphFactory;
import org.thegalactic.dgraph.Node;
import org.thegalactic.util.Couple;

/**
 * This class provides a few methods to constructs lattice examples.
 *
 * ![LatticeFactory](LatticeFactory.png)
 *
 * @uml LatticeFactory.png
 * !include resources/org/thegalactic/lattice/LatticeFactory.iuml
 *
 * class LatticeFactory #LightCyan
 * title LatticeFactory UML graph
 * @author jeff
 */
public class LatticeFactory {

    /**
     * Returns a randomly generated lattice with nb nodes.
     *
     * @param nb Number of nodes in the randomly generated lattice
     *
     * @return a randomly generated lattice with nb nodes
     */
    public static Lattice<Integer, ?> random(int nb) {
        boolean done = false;
        Lattice l = new Lattice();
        while (!done) {
            DAGraph dag = DAGraphFactory.getInstance().random(nb - 2); // what an ugly strategy :-(
            Lattice<Integer, ?> tmp = new Lattice(dag);
            Node<Integer> top = new Node(nb - 1);
            tmp.addNode(top);
            for (Node<Integer> node : tmp.max()) {
                if (!node.equals(top)) {
                    tmp.addEdge(node, top);
                }
            }
            Node<Integer> bot = new Node(nb);
            tmp.addNode(bot);
            for (Node<Integer> node : tmp.min()) {
                if (!node.equals(bot)) {
                    tmp.addEdge(bot, node);
                }
            }
            if (tmp.isLattice()) {
                done = true;
                l = tmp;
            }
        }
        return l;
    }

    /**
     * Returns the boolean algebra of cardinal 2^n.
     *
     * @param n cardinal of the boolean algebra return by this method is 2^n
     *
     * @return the boolean algebra of cardinal 2^n
     */
    public static Lattice booleanAlgebra(int n) {
        Lattice<BitSet, ?> l = new Lattice();
        BitSet b = new BitSet(n);
        Node<BitSet> bot = new Node(b);
        l.addNode(bot);
        for (int i = 0; i < n; i++) {
            BitSet bs = new BitSet(n);
            bs.set(i, true);
            Node<BitSet> next = new Node(bs);
            l.addNode(next);
            l.addEdge(bot, next);
            recursiveBooleanAlgebra(next, l, n);
        }
        return l;
    }

    /**
     * Returns the lattice of permutations of 1..n.
     *
     * Permutation are ordered as follows : A permutation s2 is a succesor of a
     * permutation s1, if s2 is obtained from s1 by inverting two consecutive
     * elements i and j such that before inversion j > i.
     *
     * Example : 124356 has following successors 214356, 142356, 124536 and
     * 124365.
     *
     * The bottom of this lattice is identity (for exemple 123456) and the top
     * is for instance 654321.
     *
     * @param n the lattice of permutations of the set 1..n
     *
     * @return the lattice of permutations of 1..n.
     */
    public static Lattice permutationLattice(int n) {
        Lattice<Permutation, ?> l = new Lattice();
        int[] content = new int[n];
        for (int i = 0; i < n; i++) {
            content[i] = i;
        }
        Permutation s = new Permutation(n);
        s.setContent(content);
        Node<Permutation> bot = new Node(s);
        l.addNode(bot);
        for (int i = 0; i < n - 1; i++) {
            int[] newC = content.clone();
            newC[i] = content[i + 1];
            newC[i + 1] = content[i];
            Permutation newS = new Permutation(n);
            newS.setContent(newC);
            Node<Permutation> succ = new Node(newS);
            l.addNode(succ);
            l.addEdge(bot, succ);
            recursivePermutationLattice(succ, l, n);
        }
        return l;
    }

    /**
     * Returns the lattice cartesian product of l and r.
     *
     * A node in the product is a cartesian product of two nodes
     *
     * There is an edge (n1, m1) -> (n2, m2) if and only if there are edges n1
     * -> n2 and m1 -> m2
     *
     * @param l Lattice of the left hand side of the product
     * @param r Lattice of the right hand side of the product
     *
     * @return the lattice cartesian product of l and r
     */
    public static Lattice product(Lattice l, Lattice r) {
        Lattice prod = new Lattice();
        // Create nodes
        for (Object nL : l.getNodes()) {
            for (Object nR : r.getNodes()) {
                prod.addNode(new Node(new Couple(((Node) nL).getContent(), ((Node) nR).getContent())));
            }
        }
        // Create edges
        for (Object source : prod.getNodes()) {
            for (Object target : prod.getNodes()) {
                if (l.containsEdge(l.getNodeByContent(((Couple) ((Node) source).getContent()).getLeft()),
                        l.getNodeByContent(((Couple) ((Node) target).getContent()).getLeft()))
                        && r.containsEdge(r.getNodeByContent(((Couple) ((Node) source).getContent()).getRight()),
                                r.getNodeByContent(((Couple) ((Node) target).getContent()).getRight()))) {
                    prod.addEdge((Node) source, (Node) target);
                }
            }
        }
        return prod;
    }

    /**
     * Returns lattice l in which convex c has been doubled.
     *
     * @param l a lattice
     * @param c a convex subset of l, to be doubled.
     *
     * @return a lattice construct from l by doubling the convex subset c.
     */
    public static Lattice doublingConvex(Lattice l, DAGraph c) {
        Lattice doubled = new Lattice();
        // Copy nodes by Content
        for (Object node : l.getNodes()) {
            if (c.containsNode((Node) node)) {
                // These nodes are doubled
                Couple cpl0 = new Couple(((Node) node).getContent(), 0);
                Node n0 = new Node(cpl0);
                Couple cpl1 = new Couple(((Node) node).getContent(), 1);
                Node n1 = new Node(cpl1);
                doubled.addNode(n0);
                doubled.addNode(n1);
            } else {
                // These nodes are just copied
                doubled.addNode(new Node(((Node) node).getContent()));
            }
        }
        // Construct edges of doubled
        Couple test = new Couple(0, 0); // used to test class of contents
        for (Object x : doubled.getNodes()) {
            for (Object y : doubled.getNodes()) {
                // Add an edge if x < y
                if (((Node) x).getContent().getClass() == test.getClass()) { // x was in convex c
                    if (((Node) y).getContent().getClass() == test.getClass()) { // y was also in convex c
                        // x & y were in convex c
                        Couple cX = (Couple) ((Node) x).getContent();
                        Couple cY = (Couple) ((Node) y).getContent();
                        if ((cX.getLeft() == cY.getLeft()) && (((Integer) cX.getRight()) == 0)
                                && (((Integer) cY.getRight()) == 1)) {
                            // Same content means same node. x is of the form (cX, 0) and y is of the for (cX, 1) so x < y in doubled.
                            doubled.addEdge((Node) x, (Node) y);
                        } else if (l.majorants(l.getNodeByContent(cX.getLeft())).contains(l.getNodeByContent(cY.getLeft()))
                                && (cX.getRight() == cY.getRight())) {
                            // x < y in l and x & y have the same second component si x < y in doubled.
                            doubled.addEdge((Node) x, (Node) y);
                        }
                    } else { // y wasn't in convex c
                        // x was in c & y wasn't
                        Couple cX = (Couple) ((Node) x).getContent();
                        if (l.majorants(l.getNodeByContent(cX.getLeft())).contains(l.getNodeByContent(((Node) y).getContent()))
                                && (((Integer) cX.getRight()) == 1)) {
                            // x < y in l and second component of x is 1.
                            doubled.addEdge((Node) x, (Node) y);
                        }
                    }
                } else // x wasn't in convex c
                 if (((Node) y).getContent().getClass() == test.getClass()) { // y was in convex c
                        // x wasn't in c but y was
                        Couple cY = (Couple) ((Node) y).getContent();
                        if (l.majorants(l.getNodeByContent(((Node) x).getContent())).contains(l.getNodeByContent(cY.getLeft()))
                                && (((Integer) cY.getRight()) == 0)) {
                            // x < y in l and x & second component of y is 0.
                            doubled.addEdge((Node) x, (Node) y);
                        }
                    } else // y wasn't in convex c
                    // x wasn't in c nor y
                     if (l.majorants(l.getNodeByContent(((Node) x).getContent())).contains(l.getNodeByContent(((Node) y).getContent()))) {
                            // x < y in l and x & second component of y is 0.
                            doubled.addEdge((Node) x, (Node) y);
                        }
            }
        }
        doubled.transitiveReduction();
        return doubled;
    }

    /**
     * Computes successors of node n in the boolean algebra currently generated.
     *
     * @param node this method compute successors of this node
     * @param l    boolean algebra currently generated
     * @param n    the number of node of l will be 2^n at the end of computation
     */
    private static void recursiveBooleanAlgebra(Node node, Lattice l, int n) {
        for (int i = 0; i < n; i++) {
            BitSet b = (BitSet) node.getContent();
            if (!b.get(i)) {
                BitSet bs = (BitSet) b.clone();
                bs.set(i, true);
                if (l.getNodeByContent(bs) == null) {
                    Node next = new Node(bs);
                    l.addNode(next);
                    l.addEdge(node, next);
                    recursiveBooleanAlgebra(next, l, n);
                } else {
                    l.addEdge(node, l.getNodeByContent(bs));
                }
            }
        }
    }

    /**
     * Computes successors of node n in the lattice l.
     *
     * @param node successors of this node are computed by this method
     * @param l    lattice of permutations currently generated
     * @param n    lattice of permutation of the set 1..n is currently generated
     */
    private static void recursivePermutationLattice(Node node, Lattice l, int n) {
        Permutation s = (Permutation) node.getContent();
        for (int i = 0; i < s.getLength() - 1; i++) {
            if (s.getContent()[i] < s.getContent()[i + 1]) {
                int[] newC = s.getContent().clone();
                Node currentNode = new Node();
                newC[i] = s.getContent()[i + 1];
                newC[i + 1] = s.getContent()[i];
                Permutation newP = new Permutation(n);
                newP.setContent(newC);
                boolean newNode = true;
                Iterator<Node> it = l.getNodes().iterator();
                while (it.hasNext() && newNode) {
                    currentNode = it.next();
                    Permutation currentContent = (Permutation) currentNode.getContent();
                    newNode = !(currentContent.equals(newP));
                }
                if (newNode) {
                    Permutation newS = new Permutation(n);
                    newS.setContent(newC);
                    Node next = new Node(newS);
                    l.addNode(next);
                    l.addEdge(node, next);
                    recursivePermutationLattice(next, l, n);
                } else {
                    l.addEdge(node, currentNode);
                }
            }
        }
    }

    /**
     * Empty constructor.
     */
    protected LatticeFactory() {
        super();
    }

    /**
     * This class provides a representation of permutations.
     *
     * If this component transforms : 0 -> 1, 1 -> 0 & 2 -> 2. Then its length
     * is 3 and
     *
     * The content contains :
     *
     * ~~~
     * content[0]=1 content[1]=0 content[2]=2
     * ~~~
     */
    private static class Permutation {

        /**
         * This component is a permutation of 0..length-1.
         */
        private int length;

        /**
         * The transformation represented by this component.
         *
         * If this component transforms : 0 -> 1, 1 -> 0 & 2 -> 2. The field
         * content contains :
         *
         * ~~~
         * content[0]=1 content[1]=0 content[2]=2
         * ~~~
         */
        private int[] content;

        /**
         * Constructs identity of the set 0..n-1.
         *
         * @param n permutation of the set 0..n-1.
         */
        Permutation(int n) {
            this.length = n;
            this.content = new int[n];
            for (int i = 0; i < n; i++) {
                this.content[i] = i;
            }
        }

        /**
         * Returns the transformation coded by this component.
         *
         * @return the transformation coded by this component.
         */
        public int[] getContent() {
            return this.content;
        }

        /**
         * Set the transformation coded by this component.
         *
         * Length of this component is update by this method.
         *
         * @param c the transformation coded in this component.
         *
         * @return this for chaining
         */
        public Permutation setContent(int[] c) {
            this.content = c;
            this.length = c.length;
            return this;
        }

        /**
         * Return length of this component.
         *
         * @return length of this component.
         */
        public int getLength() {
            return this.length;
        }

        /**
         * Set length of this componenet.
         *
         * @param l length of this component.
         *
         * @return true if update is successful.
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
         * @return a string representation of this component.
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
         * @param s test if this component is equal to s
         *
         * @return true if this component is equal to s
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
         * @return an integer representing the object
         */
        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}
