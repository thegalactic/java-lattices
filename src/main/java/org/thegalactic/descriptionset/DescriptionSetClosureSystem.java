package org.thegalactic.descriptionset;


import java.util.TreeSet;
import java.util.Vector;

/**
 * This class represents Closure Systems that handle Descriptions in place of binary attributes.
 * It defines the necessary methods to implements in concrete sub-classes representing closure systems.
 *
 * @author Jessie Carbonnel
 *
 * @param <F> the type of Description
 */
public abstract class DescriptionSetClosureSystem<F extends Description> {

    /*
     * ------------- ABSTRACT METHODS ----------------
     */

    /**
     * Returns a vector of description set containing
     * the original set of description set
     * plus the ones obtained with the similarity operation.
     *
     * @return the set of all description sets.
     */
    public abstract Vector<ComparableDescriptionSet<F>> getDescriptionSets();

    /**
     * Returns the closure of the description set in parameter.
     *
     * @param set a description set.
     * @return the closure of the specified description set.
     */
    public abstract ComparableDescriptionSet<F> descriptionSetClosure(ComparableDescriptionSet<F> set);

    /**
     * Returns the closure of the specified set of observations.
     *
     * @param set a set of observations.
     * @return the closure of the specified set of observations
     */
    public abstract TreeSet<String> observationClosure(TreeSet<String> set);

    /**
     * Returns a set of concepts representing all closures.
     *
     * @return the set of concepts representing all closures.
     */
    public abstract Vector<DescriptionSetConcept<F>> allDescriptionClosures();

}
