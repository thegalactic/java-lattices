package fr.kbertet.lattice;
/*
 * LatticeFactory.java
 *
 * Copyright: 2010-2014 Karell Bertet, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of java-lattices, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 */

import java.util.BitSet;
import java.util.Iterator;

import fr.kbertet.util.Couple;
import fr.kbertet.dgraph.DAGraph;
import fr.kbertet.dgraph.Node;

/**
 * This class provides a few methods to constructs lattice examples.
 *
 * ![LatticeFactory](LatticeFactory.png)
 *
 * @uml LatticeFactory.png
 * !include resources/fr/kbertet/lattice/LatticeFactory.iuml
 *
 * class LatticeFactory #LightCyan
 * title LatticeFactory UML graph
 * @author jeff
 */
public class LatticeFactory {
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
     */
    private static class Permutation {
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
         *
         * @return  this for chaining
         */
        public Permutation setContent(int[] c) {
            this.content = c;
            this.length = c.length;
            return this;
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

    /**
     * Empty constructor.
     */
    protected LatticeFactory() {
        super();
    }

    /**
     * Returns a randomly generated lattice with nb nodes.
     *
     * @param   nb  Number of nodes in the randomly generated lattice
     *
     * @return  a randomly generated lattice with nb nodes
     */
    public static Lattice random(int nb) {
        boolean done = false;
        Lattice l = new Lattice();
        while (!done) {
            DAGraph dag = DAGraph.random(nb - 2); // what an ugly strategy :-(
            Lattice tmp = new Lattice(dag);
            Node top = new Node(new Integer(nb - 1));
            tmp.addNode(top);
            for (Node n : tmp.max()) {
                if (!n.equals(top)) {
                    tmp.addEdge(n, top);
                }
            }
            Node bot = new Node(new Integer(nb));
            tmp.addNode(bot);
            for (Node n : tmp.min()) {
                if (!n.equals(bot)) {
                    tmp.addEdge(bot, n);
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
     * @param   n  cardinal of the boolean algebra return by this method is 2^n
     *
     * @return  the boolean algebra of cardinal 2^n
     */
    public static Lattice booleanAlgebra(int n) {
        Lattice l = new Lattice();
        BitSet b = new BitSet(n);
        Node bot = new Node(b);
        l.addNode(bot);
        for (int i = 0; i < n; i++) {
            BitSet bs = new BitSet(n);
            bs.set(i, true);
            Node next = new Node(bs);
            l.addNode(next);
            l.addEdge(bot, next);
            recursiveBooleanAlgebra(next, l, n);
        }
        return l;
    }

    /**
     * Computes successors of node n in the boolean algebra currently generated.
     *
     * @param   node  this method compute successors of this node
     * @param   l     boolean algebra currently generated
     * @param   n     the number of node of l will be 2^n at the end of computation
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
     * Returns the lattice of permutations of 1..n.
     *
     * Permutation are ordered as follows :
     * A permutation s2 is a succesor of a permutation s1, if s2 is obtained from s1 by inverting two consecutive
     * elements i and j such that before inversion j > i.
     *
     * Example : 124356 has following successors 214356, 142356, 124536 and 124365.
     *
     * The bottom of this lattice is identity (for exemple 123456) and the top is for instance 654321.
     *
     * @param   n  the lattice of permutations of the set 1..n
     *
     * @return  the lattice of permutations of 1..n.
     */
    public static Lattice permutationLattice(int n) {
        Lattice l = new Lattice();
        int[] content = new int[n];
        for (int i = 0; i < n; i++) {
            content[i] = i;
        }
        Permutation s = new Permutation(n);
        s.setContent(content);
        Node bot = new Node(s);
        l.addNode(bot);
        for (int i = 0; i < n - 1; i++) {
            int[] newC = content.clone();
            newC[i] = content[i + 1];
            newC[i + 1] = content[i];
            Permutation newS = new Permutation(n);
            newS.setContent(newC);
            Node succ = new Node(newS);
            l.addNode(succ);
            l.addEdge(bot, succ);
            recursivePermutationLattice(succ, l, n);
        }
        return l;
    }

    /**
     * Computes successors of node n in the lattice l.
     *
     * @param   node  successors of this node are computed by this method
     * @param   l     lattice of permutations currently generated
     * @param   n     lattice of permutation of the set 1..n is currently generated
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
     * Returns the lattice cartesian product of l and r.
     *
     * A node in the product is a cartesian product of two nodes
     *
     * There is an edge (n1, m1) -> (n2, m2) if and only if there are edges n1 -> n2 and m1 -> m2
     *
     * @param   l  Lattice of the left hand side of the product
     * @param   r  Lattice of the right hand side of the product
     *
     * @return  the lattice cartesian product of l and r
     */
    public static Lattice product(Lattice l, Lattice r) {
        Lattice prod = new Lattice();
        // Create nodes
        for (Node nL : l.getNodes()) {
            for (Node nR : r.getNodes()) {
                prod.addNode(new Node(new Couple(nL.getContent(), nR.getContent())));
            }
        }
        // Create edges
        for (Node from : prod.getNodes()) {
            for (Node to : prod.getNodes()) {
                if (l.containsEdge(l.getNodeByContent(((Couple) from.getContent()).getLeft()),
                        l.getNodeByContent(((Couple) to.getContent()).getLeft()))
                        && r.containsEdge(r.getNodeByContent(((Couple) from.getContent()).getRight()),
                                r.getNodeByContent(((Couple) to.getContent()).getRight()))) {
                    prod.addEdge(from, to);
                }
            }
        }
        return prod;
    }
    /**
     * Returns lattice l in which convex c has been doubled.
     *
     * @param   l  a lattice
     * @param   c  a convex subset of l, to be doubled.
     *
     * @return  a lattice construct from l by doubling the convex subset c.
     */
    public static Lattice doublingConvex(Lattice l, DAGraph c) {
        Lattice doubled = new Lattice();
        // Copy nodes by Content
        for (Node n : l.getNodes()) {
            if (c.containsNode(n)) {
                // These nodes are doubled
                Couple cpl0 = new Couple(n.getContent(), 0);
                Node n0 = new Node(cpl0);
                Couple cpl1 = new Couple(n.getContent(), 1);
                Node n1 = new Node(cpl1);
                doubled.addNode(n0);
                doubled.addNode(n1);
            } else {
                // These nodes are just copied
                doubled.addNode(new Node(n.getContent()));
            }
        }
        // Construct edges of doubled
        Couple test = new Couple(0, 0); // used to test class of contents
        for (Node x : doubled.getNodes()) {
            for (Node y : doubled.getNodes()) {
                // Add an edge if x < y
                if (x.getContent().getClass() == test.getClass()) { // x was in convex c
                    if (y.getContent().getClass() == test.getClass()) { // y was also in convex c
                        // x & y were in convex c
                        Couple cX = (Couple) x.getContent();
                        Couple cY = (Couple) y.getContent();
                        if ((cX.getLeft() == cY.getLeft()) && (((Integer) cX.getRight()).intValue() == 0)
                                && (((Integer) cY.getRight()).intValue() == 1)) {
                            // Same content means same node. x is of the form (cX, 0) and y is of the for (cX, 1) so x < y in doubled.
                            doubled.addEdge(x, y);
                        } else {
                            if (l.majorants(l.getNodeByContent(cX.getLeft())).contains(l.getNodeByContent(cY.getLeft()))
                                    && (cX.getRight() == cY.getRight())) {
                                // x < y in l and x & y have the same second component si x < y in doubled.
                                doubled.addEdge(x, y);
                            }
                        }
                    } else { // y wasn't in convex c
                        // x was in c & y wasn't
                        Couple cX = (Couple) x.getContent();
                        if (l.majorants(l.getNodeByContent(cX.getLeft())).contains(l.getNodeByContent(y.getContent()))
                                && (((Integer) cX.getRight()).intValue() == 1)) {
                            // x < y in l and second component of x is 1.
                            doubled.addEdge(x, y);
                        }
                    }
                } else { // x wasn't in convex c
                    if (y.getContent().getClass() == test.getClass()) { // y was in convex c
                        // x wasn't in c but y was
                        Couple cY = (Couple) y.getContent();
                        if (l.majorants(l.getNodeByContent(x.getContent())).contains(l.getNodeByContent(cY.getLeft()))
                                && (((Integer) cY.getRight()).intValue() == 0)) {
                                // x < y in l and x & second component of y is 0.
                                doubled.addEdge(x, y);
                            }
                    } else { // y wasn't in convex c
                        // x wasn't in c nor y
                        if (l.majorants(l.getNodeByContent(x.getContent())).contains(l.getNodeByContent(y.getContent()))) {
                                // x < y in l and x & second component of y is 0.
                                doubled.addEdge(x, y);
                            }
                    }
                }
            }
        }
        doubled.transitiveReduction();
        return doubled;
    }
}
