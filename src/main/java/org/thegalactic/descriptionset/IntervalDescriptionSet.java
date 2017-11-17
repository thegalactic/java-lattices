package org.thegalactic.descriptionset;

import java.util.TreeSet;

import org.thegalactic.util.Couple;

/**
 * This class implements interval description sets.
 *
 * @author Jessie Carbonnel
 *
 */
public class IntervalDescriptionSet extends ComparableDescriptionSet<IntervalDescription> {

    /**
     * Generated version UID.
     */
    private static final long serialVersionUID = 7907048096315817000L;

    /**
     * Constructs an IntervalDescriptionSet base on the specified set of interval descriptions.
     *
     * @param s a set of interval descriptions
     */
    public IntervalDescriptionSet(TreeSet<IntervalDescription> s) {
        super(s);
    }

    @Override
    /**
     * Compares two IntervalDescriptionSets.
     * An IntervalDescriptionSet has at most one description.
     *
     * Uses the compareTo of IntervalDescription
     */
    public int compareTo(ComparableDescriptionSet<IntervalDescription> set) {
        if (this.isBot() && set.isBot()) {
            return 0;
        } else if (this.isBot()) {
            return 1;
        } else if (set.isBot()) {
            return -1;
        } else {
            return this.first().compareTo(set.first());
        }
    }

    @Override
    /**
     * Returns true if the current interval is included
     * in the specified interval.
     *
     * @param set a set of interval
     * @return true if included in the specified interval
     */
    public boolean isIncludedIn(ComparableDescriptionSet<IntervalDescription> set) {
         if (!this.isEmpty() && !set.isEmpty()) {
            if (set.isBot()) {
                return true; // everything included in the bottom concept
            } else if (this.isBot()) {
                return false; //the bottom concept is included in nothing
            } else {
                return (((Integer) this.first().getValue().getLeft()) >= ((Integer) set.first().getValue().getLeft())
                        && ((Integer) this.first().getValue().getRight()) <= ((Integer) set.first().getValue().getRight()));
            }
        } else {
            // case of top concept
            return this.isEmpty();
        }
    }

    @Override
    /**
     * Returns the larger common interval of the two intervals.
     *
     * @return an interval set containing one interval representing the similarity of the two specified ones
     */
    public ComparableDescriptionSet<IntervalDescription> getSimilarity(
            ComparableDescriptionSet<IntervalDescription> set) {

        // handling special case of bottom concept intent
        if (this.isBot()) {
            return set.clone();
        } else if (set.isBot()) {
            return this.clone();
        }

        IntervalDescription d = new IntervalDescription();
        TreeSet<IntervalDescription> s = new TreeSet<IntervalDescription>();

        if (!this.isEmpty() && !((IntervalDescriptionSet) set).isEmpty()) {
            Integer min = new Integer(0);
            Integer max = new Integer(0);
            min = Math.max((Integer) this.first().getValue().getLeft(), (Integer) set.first().getValue().getLeft());
            max = Math.min((Integer) this.first().getValue().getRight(), (Integer) set.first().getValue().getRight());
            Couple c  = new Couple(min, max);
            d.setValue(c);
            if (min <= max) {
                s.add(d);
            }
        }
        return new IntervalDescriptionSet(s);
    }

    @Override
    /**
     * Bottom concept intent is [*,*]
     */
    public void setBot() {
        IntervalDescription id = new IntervalDescription(new Couple("*", "*"));
        this.clear();
        this.add(id);
    }

    @Override
    /**
     * Returns true if the current interval description set represents the bottom concept intent.
     *
     *@return true if the current interval description set represents the bottom concept intent
     */
    public boolean isBot() {
        if (this.isEmpty()) {
            return false;
        } else {
            return this.first().getValue().getLeft().equals("*") && this.first().getValue().getRight().equals("*");
        }
    }

    @Override
    /**
    * Compares the two IntervalDescriptionSets.
    *
    * @return true if the two IntervalDescriptions
    *             contained in the two IntervalDescriptionSets
    *             have the same value.
    */
   public boolean equals(Object s) {
        if (!this.isEmpty() && !((IntervalDescriptionSet) s).isEmpty()) {
            return (this.first().getValue().equals(((IntervalDescriptionSet) s).first().getValue()));
        } else {
            return (this.isEmpty() && ((IntervalDescriptionSet) s).isEmpty());
        }
   }

    @Override
   /**
    * Returns a clone of this component.
    *
    * @return a clone of this component.
    */
   public IntervalDescriptionSet clone() {
       return (IntervalDescriptionSet) super.clone();
   }

    @Override
    /**
     * Calls HashCode from super class.
     */
    public int hashCode() {
        return super.hashCode();
    }

}
