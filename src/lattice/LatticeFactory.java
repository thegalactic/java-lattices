package lattice;
/*
 * LatticeFactory.java
 *
 * Copyright: 2013 University of La Rochelle, France
 *
 * License: http://www.cecill.info/licences/Licence_CeCILL-B_V1-en.html CeCILL-B license
 *
 * This file is part of lattice, free package. You can redistribute it and/or modify
 * it under the terms of CeCILL-B license.
 *
 * @version 2014
 */

import dgraph.DAGraph;
import dgraph.Node;
import java.util.BitSet;
import java.util.Iterator;

/**
 * This class provides a few methods to constructs lattice examples.
 *
 * ![LatticeFactory](LatticeFactory.png)
 *
 * @uml LatticeFactory.png
 * !include src/lattice/LatticeFactory.iuml
 *
 * class LatticeFactory #LightCyan
 * title LatticeFactory UML graph
 * @author jeff
 */
public class LatticeFactory {
    /**
     * Empty constructor.
     */
    protected LatticeFactory() {
        super();
    }
    /**
     * Returns a randomly generated lattice with nb nodes.
     *
     * @param nb Number of nodes in the randomly generated lattice
     * @return a randomly generated lattice with nb nodes
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
     * @param n cardinal of the boolean algebra return by this method is 2^n
     * @return the boolean algebra of cardinal 2^n
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
     * @param node this method compute successors of this node
     * @param l boolean algebra currently generated
     * @param n the number of node of l will be 2^n at the end of computation
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
     * @param n the lattice of permutations of the set 1..n
     * @return the lattice of permutations of 1..n.
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
     * @param node successors of this node are computed by this method
     * @param l lattice of permutations currently generated
     * @param n lattice of permutation of the set 1..n is currently generated
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
     * @param l Lattice of the left hand side of the product
     * @param r Lattice of the right hand side of the product
     * @return the lattice cartesian product of l and r
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
}
