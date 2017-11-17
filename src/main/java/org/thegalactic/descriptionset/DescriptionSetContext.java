package org.thegalactic.descriptionset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import org.thegalactic.util.ComparableSet;

/**
 * This class represents context depicting each observation against one description.
 * It extends DescriptionSetClosureSystems.
 *
 * A context is created from a file having this format:
 *
 * *************
 * DescriptionSetContext type
 * Observations: o1 o2 ... on
 * o1: d1
 * o2: d2
 * ...
 * on: dn
 * *************
 *
 * The computation of all closures is based on nextClosure() algorithm.
 * nextClosure() relies on observation closures, so it works for all kinds of descriptions.
 *
 * @author Jessie Carbonnel
 *
 * @param <F> the type of descriptions.
 */
public class DescriptionSetContext<F extends Description> extends DescriptionSetClosureSystem<F> {

        /*
         * -------------------- ATTRIBUTES -------------------
         */

    /**
     * The type of the descriptions handled in the context.
     */
    private String type;

    /**
     * A set of ComparableDescriptionSets
     * corresponding to the ones defined in the context
     * for each observation.
     */
    private Vector<ComparableDescriptionSet<F>> initialDescriptionSets;

    /**
     * A set of ComparableDescriptionSets
     * corresponding to the ones defined in the context
     * (i.e., the ones in initialDescriptionSets)
     * plus the ones obtained with the similarity operator
     * over the set of initial DescriptionSets.
     */
    private Vector<ComparableDescriptionSet<F>> allDescriptionSets;

    /**
     * The set of observations.
     */
    private ComparableSet<String> observations;

    /*
     * ------------ ATTRIBUTES: INTENT / EXTENT ------------
     */

    /**
     * A map to associate the most specialised ComparableDescriptionSet corresponding to each observation.
     * The ComparableDescriptionSets are the ones in "initialDescriptionSets"
     */
    private TreeMap<String, ComparableDescriptionSet<F>> specIntent;

    /**
     * A map to associate to each observation, all the ComparableDescriptionSets characterising it.
     * The ComparableDescriptionSet are the ones in "allDescriptionSets".
     */
    private TreeMap<String, TreeSet<ComparableDescriptionSet<F>>> intent;

    /**
     * A map to associate a set of observations to each ComparableDescriptionSets.
     * The ComparableDescriptionSets are the ones in the set named "allDescriptionSets"
     */
    private TreeMap<ComparableDescriptionSet<F>, TreeSet<String>> extent;

        /*
         * ------------ CONSTRUCTORS ------------
         */

    /**
     * Constructs a Context depending on a file representing observations and their descriptions.
     * We assume that an observation is described by one description, and not by a description set.
     * [This could be changed after
     * initFromDescriptionSetContext(line) would need to be redefined for Description
     * and to be added in ComparableDescriptionSet]
     *
     * The specified file should have this format:
     *
     * *************
     * DescriptionSetContext type
     * Observations: o1 o2 ... on
     * o1: d1
     * o2: d2
     * ...
     * on: dn
     * *************
     *
     * @param filename the file depicting the context
     * @throws IOException
     */
    public DescriptionSetContext(String filename) {

        System.out.println("---------------");
        System.out.println("Creating DescriptionSet Context from file");
        System.out.println("---------------");

        initialDescriptionSets = new Vector<ComparableDescriptionSet<F>>();

        observations = new ComparableSet<String>();

        specIntent = new TreeMap<String, ComparableDescriptionSet<F>>();

        try {
                // Opening the file containing the context

             File f = new File(filename);
             FileReader fr = new FileReader(f);
             BufferedReader br = new BufferedReader(fr);

                 // First line should states "DescriptionSetContext"

             String line = br.readLine();

             if (line.contains("DescriptionSetContext")) {

                     // Extracting the type

                 line = line.substring(line.indexOf(" ") + 1, line.length());
                 type = line;
             } else {
                 System.err.println("No type detected, issue with first line of the file.");
             }


                 // Second line should states "Observations"

             line = br.readLine();

             if (line.contains("Observations: ")) {

                     // Extracting all observations

                 line = line.substring(line.indexOf(" ") + 1, line.length());
                 for (String s : line.split(" ")) {
                     this.addObservation(s);
                 }
             } else {
                 System.err.println("No observations detected, issue with second line of the file.");
             }


                 // Retrieving the CDS corresponding to each observation

             line = br.readLine();

             while (line != null) {

                     // extracting the observation

                 String o = line.substring(0, line.indexOf(":"));

                     // extracting the description set

                     // new ComparableDescriptionSet and Description
                     // based on the previously extracted type

                 ComparableDescriptionSet set = ComparableDescriptionSet.create(type);
                 Description d = Description.create(type);

                     // initialises the Description depending on the line

                 line = line.substring(line.indexOf(":") + 2, line.length());

                 d.initFromDescriptionSetContext(line);

                     // adds the Description to the DescriptionSet

                 set.add(d);

                     // adds the DescriptionSet to the set of initial CDS

                 initialDescriptionSets.addElement(set.clone());

                     // Adds in SpecIntent
                     // Which associates each observation to its most specific CDS

                 specIntent.putIfAbsent(o, set.clone());

                 line = br.readLine();
             }
             br.close();
        } catch (FileNotFoundException exception) {
            System.out.println("File not found.");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // Computing all description sets

        computeAllDescriptionSets();

            // Computing intent

        computeIntent();

            // Computing extent

        computeExtent();

    }

        /*
         * -------------------- HANDLING METHODS FOR ATTRIBUTES -------------------
         */

    /**
     * Returns the set of observations.
     *
     * @return the set of observations.
     */
    public TreeSet<String> getObservations() {
        return observations;
    }

    /**
     * Returns a set containing all the description sets.
     *
     * @return a set containing all the description sets.
     */
    public Vector<ComparableDescriptionSet<F>> getAllDescriptionSets() {
        return allDescriptionSets;
    }

    /**
     * Adds the specified observation to the set of observations.
     *
     * @param o an observation
     */
    public void addObservation(String o) {
        this.observations.add(o);
    }

    /**
     * Adds the specified observations to the set of observations.
     *
     * @param o a set of observations
     */
    public void addAllObservation(TreeSet<String> o) {
        this.observations.addAll(o);
    }

    /**
     * Prints the set of observations.
     */
    public void printObservations() {
        System.out.println("Observations: " + observations);
    }

    /**
     * Adds the specified description set to initalDescriptionSets.
     *
     * @param ds a description set.
     */
    public void addDescriptionSetInIDS(ComparableDescriptionSet<F> ds) {
        this.initialDescriptionSets.add(ds);
    }

    /**
     * Adds the specified description sets to intialDescritionSets.
     *
     * @param set a set of description sets.
     */
    public void addAllDescriptionInIDS(TreeSet<ComparableDescriptionSet<F>> set) {
        this.initialDescriptionSets.addAll(set);
    }

    /**
     * Adds the specified descriptions set to allDescriptionSets.
     *
     * @param ds a description set.
     */
    public void addDescriptionSetInADS(ComparableDescriptionSet<F> ds) {
        this.allDescriptionSets.add(ds);
    }

    /**
     * Adds the specified description sets to allDescriptionSets.
     *
     * @param set a set of description sets.
     */
    public void addAllDescriptionInADS(TreeSet<ComparableDescriptionSet<F>> set) {
        this.allDescriptionSets.addAll(set);
    }

    /**
     * Prints the set of initial description sets.
     */
    public void printInitialDescriptionSets() {
        System.out.println("Initial description sets: " + initialDescriptionSets);
    }

    /**
     * Prints the set of all description sets.
     */
    public void printAllDescriptionSets() {
        System.out.println("All description sets: " + allDescriptionSets);
    }

        /*
         * -------------------- GENERATIVE METHODS -------------------
         */

    /**
     * Computes the complete intent of each observation.
     */
    public void computeIntent() {

        intent = new TreeMap<String, TreeSet<ComparableDescriptionSet<F>>>();

            // for each observation

        for (String o : observations) {

                // adds o as a new key in intent TreeMap

            intent.putIfAbsent(o, new TreeSet<ComparableDescriptionSet<F>>());

                // adds the description set characterising the observation in the context

            intent.get(o).add(getSpecIntent(o));

                // for each description set

            for (ComparableDescriptionSet ds : allDescriptionSets) {

                    // if the description set describing the observation in the context
                    // is included in the description set ds

                if (ds.isIncludedIn(getSpecIntent(o))) {

                        // adds ds to the observation intent
                    intent.get(o).add(ds);
                }
            }
        }
    }

    /**
     * Computes the extent of each description set.
     */
    public void computeExtent() {

        extent = new TreeMap<ComparableDescriptionSet<F>, TreeSet<String>>();

            // for each description set

        for (ComparableDescriptionSet ds : allDescriptionSets) {

                // adds the description set as a key in extent TreeMap

            extent.putIfAbsent(ds, new TreeSet<String>());

                // for each observation

            for (String o : observations) {

                    // if ds is included in the observation description set

                if (ds.isIncludedIn(getSpecIntent(o))) {

                        // adds o to the description set extent

                    extent.get(ds).add(o);
                }
            }
        }
    }

    /**
     * Computes all description sets.
     * O(n2)
     */
    public void computeAllDescriptionSets() {

            // adds existing description sets from the initial description sets

        allDescriptionSets = new Vector<ComparableDescriptionSet<F>>();
        allDescriptionSets.addAll(initialDescriptionSets);

            // adds the description set representing the bottom concept intent

        ComparableDescriptionSet<F> bot = ComparableDescriptionSet.create(type);
        bot.setBot();
        allDescriptionSets.add(bot);

            // vector that will contain the computed similarities of the description set
            // At each new steps

        Vector<ComparableDescriptionSet<F>> stepn = new Vector<ComparableDescriptionSet<F>>();


            // while we can compute new description sets
            // by similarity operation on existing ones

        boolean continu = true;
        while (continu) {

                // for each pair of description sets

            for (ComparableDescriptionSet<F> cds1 : allDescriptionSets) {

                for (ComparableDescriptionSet<F> cds2 : allDescriptionSets) {

                        // computes their similarity

                    ComparableDescriptionSet<F> set = ComparableDescriptionSet.create(type);
                    set = cds1.getSimilarity(cds2);

                        // adds the similarity description set in the set

                    if (!stepn.contains(set)) {

                        stepn.add(set.clone());
                    }
                }
            }

                // keeps only the new description sets

            stepn.removeAll(allDescriptionSets);

            // if no new descriptions

            if (stepn.size() == 0) {

                    // ends the loop

                continu = false;
            } else {

                // else adds the new descriptions to 'allDescription'

                allDescriptionSets.addAll(stepn);

                    // clears the buffer description set

                stepn.clear();
            }
        }
    }

        /*
         * -------------------- HANDLING METHODS FOR EXTENT/INTENT -------------------
         */

    /**
     * Returns the most specialised description set representing an observation o.
     *
     * @param o an observation.
     * @return the most specialised description set representing the observation.
     */
    public ComparableDescriptionSet<F> getSpecIntent(String o) {
        return this.specIntent.get(o).clone();
    }

    /**
     * Returns the most specific description set corresponding to a set of observations.
     * If the set of observations is emptu, returns the description set representing the
     * bottom concept intent.
     *
     * @param observations a set of observations
     * @return the most specific description set corresponding to the observations
     */
    public ComparableDescriptionSet<F> getSpecIntent(final TreeSet<String> observations) {

            // defines the most specific intent of the empty set of observations

        if (observations.isEmpty()) {
            ComparableDescriptionSet<F> cds = ComparableDescriptionSet.create(type);
            cds.setBot();
            return cds.clone();
        }

            // computes the similarity of the description sets
            // corresponding to the specified observations

        TreeSet<String> ob = new TreeSet<String>();
        ob.addAll(observations);

        ComparableDescriptionSet<F> sim = getSpecIntent(ob.pollFirst());
        for (String o : ob) {
            sim = sim.getSimilarity(getSpecIntent(o));
        }
        return sim;
    }

    /**
     * This methods takes a description set ds and returns the set of description sets
     * in which the description set ds is included.
     *
     * @param ds a description set.
     * @return the set of description sets in which ds is included.
     */
    public TreeSet<ComparableDescriptionSet<F>> getMoreGeneralDescriptionSets(ComparableDescriptionSet<F> ds) {
        TreeSet<ComparableDescriptionSet<F>> result = new TreeSet<ComparableDescriptionSet<F>>();
        result.add(ds);
        for (ComparableDescriptionSet<F> desc : allDescriptionSets) {
            if (ds.isIncludedIn(desc)) {
                result.add(desc);
            }
        }
        return result;
    }

    /**
     * Returns all the description sets corresponding to an observation o.
     *
     * @param o an observation.
     * @return a set of description sets representing the observation.
     */
    public TreeSet<ComparableDescriptionSet<F>> getIntent(String o) {
        return this.intent.get(o);
    }

    /**
     * Returns all the description sets corresponding to a set of observations o.
     *
     * @param o a set of observations
     * @return a set of description sets representing the observations
     */
    public TreeSet<ComparableDescriptionSet<F>> getIntent(TreeSet<String> o) {
        return getMoreGeneralDescriptionSets(getSpecIntent(o));
    }

    /**
     * Returns all observations represented by the specified description set.
     *
     * @param ds a description set.
     * @return the set of all observations corresponding to the specified description set.
     */
    public TreeSet<String> getExtent(ComparableDescriptionSet<F> ds) {
        if (this.extent.get(ds) != null) {
            return this.extent.get(ds);
        } else {
            return new TreeSet<String>();
        }
    }

    /**
     * Returns all observations represented by a set of description sets ds.
     *
     * @param ds a set of description sets.
     * @return the set of all observations corresponding to the description sets.
     */
    public TreeSet<String> getExtent(Vector<ComparableDescriptionSet<F>> ds) {

            // creates an empty set of observations

        TreeSet<String> ob = new TreeSet<String>();

            // adds the observations corresponding to the first description set of ds

        ob.addAll(getExtent(ds.get(0).clone()));

            // for each description in d

        for (ComparableDescriptionSet<F> de : ds) {

                // keeps only the observations present in both extents

            ob.retainAll(getExtent(de));
        }

        return ob;
    }

        /*
         * -------------------- INHERITED METHODS -------------------
         */

    @Override
    /**
     * Returns the set of all description set.
     *
     * @return the set of all description set.
     */
    public Vector<ComparableDescriptionSet<F>> getDescriptionSets() {
        return allDescriptionSets;
    }

    @Override
    /**
     * Returns the closure of the specified description set.
     *
     * @param set a description set.
     * @return a description set representing the closure of the specified one.
     */
    public ComparableDescriptionSet<F> descriptionSetClosure(ComparableDescriptionSet<F> set) {
        return getSpecIntent(getExtent(set)).clone();
    }

    /**
     * Returns the closure of the observation set.
     *
     * @param set an observation set.
     * @return an observation set representing the closure of the specified one.
     */
    public TreeSet<String> observationClosure(final TreeSet<String> set) {
        return getExtent(getSpecIntent(set));
    }

    @Override
    /**
     * Returns the set of all concepts of the closure system.
     * These concepts have a description set as intent.
     * This method computes the set of all closures by using nextClosure()
     * on the set of objects.
     * Thus it is applicable for any type of descriptions.
     *
     * @return the set of all concepts
     */
    public Vector<DescriptionSetConcept<F>> allDescriptionClosures() {

        Vector<DescriptionSetConcept<F>> allclosure = new Vector<DescriptionSetConcept<F>>();

            // first closure: observation closure of the empty set

        TreeSet<String> set = new TreeSet<String>();

        allclosure.add(new DescriptionSetConcept<F>(this.observationClosure(set), type));

        DescriptionSetConcept<F> cl = allclosure.firstElement();

            // next closure in lectic order

        boolean continu = true;
        do {
            cl = this.nextDescriptionSetClosure(cl);

            if (allclosure.contains(cl)) {
                continu = false;
            } else {
                allclosure.add(cl);
            }
        } while (continu);

        return allclosure;
    }

    /**
     * Copy/Paste from org.thegalatic.lattice.ClosureSystem.java.
     * Then, adapted to computes closure on observations
     * instead of attributes.
     *
     * Computation of the next closure based on the set of observations
     * of the specified concept.
     * Works for observations depicted by DescriptionSets.
     *
     * Returns a DescriptionSetConcept,
     * i.e., a Concept having a set of String as extent
     * and a DescriptionSet as intent.
     *
     * @param cl a description set concept
     * @return the next description set concept in the lectic order
     */
    public DescriptionSetConcept<F> nextDescriptionSetClosure(DescriptionSetConcept<F> cl) {

        TreeSet<String> set = new TreeSet<String>(this.getObservations());

        boolean success = false;

        TreeSet<String> setA = new TreeSet<String>(cl.getExtent());

        String ni = set.last();

        do {
            ni = set.last();
            set.remove(ni);

            if (!setA.contains(ni)) {

                setA.add(ni);

                TreeSet<String> setB = new TreeSet<String>();
                setB.addAll(this.observationClosure(setA));

                setB.removeAll(setA);

                if (setB.isEmpty() || setB.first().compareTo(ni) >= 1) {

                    setA = this.observationClosure(setA);

                    success = true;

                } else {

                    setA.remove(ni);
                }
            } else {

                setA.remove(ni);
            }
        } while (!success && ni.compareTo(this.getObservations().first()) >= 1);

        return new DescriptionSetConcept<F>(getSpecIntent(setA), setA);
    }
}
