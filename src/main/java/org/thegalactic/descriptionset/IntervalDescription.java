package org.thegalactic.descriptionset;

import org.thegalactic.util.Couple;

/**
 * This class implements descriptions of intervals.
 *
 *An interval is a description of type DescriptionType.interval.
 *
 *Type of the description is a Couple of Intefer, representing the min and max of the interval.
 *
 * @author Jessie Carbonnel
 *
 */
public class IntervalDescription extends Description<Couple> {

    /**
     * Constructs an empty IntervalDescription.
     */
    public IntervalDescription() {
        super(DescriptionType.interval, new Couple(null, null));
    }

    /**
     * Constructs an IntervalDescription with the specified value.
     *
     * @param c an interval
     */
    public IntervalDescription(Couple c) {
        super(DescriptionType.interval, c);
    }

    @Override
    /**
     * Compares two Intervals.
     * First compares them according to their range
     * [1,2] < [1,3],
     * then according to their min
     * [1,2] < [3,4]
     * [*,*] is the 'greatest' interval.
     *
     * @param d an interval description
     * @return a negative integer, zero, or a positive integer as this description
     *         is less than, equal to, or greater than the specified one.
     */
    public int compareTo(Description<Couple> d) {
            // if the current interval is [*,*], it is greater than all the others
        if (this.getValue().getLeft().equals("*")) {
            return 1;
        } else if (d.getValue().getLeft().equals("*")) {
            return -1;
        } else {
            int range1 = ((Integer) this.getValue().getLeft()) - ((Integer) this.getValue().getRight());
            int range2 = ((Integer) d.getValue().getLeft()) - ((Integer) d.getValue().getRight());

            if (range1 == range2) {
                return ((Integer) this.getValue().getLeft()).compareTo((Integer) d.getValue().getLeft());
            } else {
                return range1 - range2;
            }
        }
    }

    @Override
    /**
     * Takes an interval represented by a String
     * and initialises the AttributeDescription's value accordingly.
     * String Interval format: [a,b]
     *
     *@param s the interval description in String format
     */
    public void initFromDescriptionSetContext(String s) {
        this.getValue().setLeft(Integer.parseInt(s.substring(1, s.indexOf(','))));
        this.getValue().setRight(Integer.parseInt(s.substring(s.indexOf(",") + 1, s.length() - 1)));
    }

    @Override
    /**
     * Compares the two IntervalDescriptions.
     *
     * @return true if the two IntervalDescriptions have the same value.
     */
    public boolean equals(Object o) {
       return this.getValue().getLeft().equals(((IntervalDescription) o).getValue().getLeft())
               && this.getValue().getRight().equals(((IntervalDescription) o).getValue().getRight());
    }

    @Override
    /**
     * Calls HashCode from super class.
     */
    public int hashCode() {
        return super.hashCode();
    }
}
