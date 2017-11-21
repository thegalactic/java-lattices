package org.thegalactic.descriptionset;

import org.thegalactic.lattice.ConceptLattice;

/**
 * This class tests methods handling description sets.
 *
 * @author Jessie Carbonnel
 *
 */
public final class TestDescriptionSet {

    /**
     * Constructor.
     */
     private TestDescriptionSet() {
     }

    /**
     * Main.
     *
     * @param args main's arguments
     */
    public static void main(String[] args) {

        try {

                // Creating description set context
                // from an example file
                // representing attributeSet descriptions

            String filename = "src/examples/descriptionSet/attributeSet.txt";

            DescriptionSetContext<AttributeDescription> context = new DescriptionSetContext<AttributeDescription>(filename);

                // PRINTING OBSERVATIONS

            context.printObservations();

                // PRINTING INITIAL DESCRIPTION SETS

            context.printInitialDescriptionSets();

                // PRINTING ALL DESCRIPTION SETS

            context.printAllDescriptionSets();

                // PRINTING MORE SPECIALISED INTENT

            System.out.println();
            for (String o : context.getObservations()) {
                System.out.println("SpecInt(" + o + ") = " + context.getSpecIntent(o));
            }

                // PRINTING INTENT

            System.out.println();
            for (String o : context.getObservations()) {
                System.out.println("Int(" + o + ") = " + context.getIntent(o));
            }

                // PRINTING EXTENT

            System.out.println();
            for (ComparableDescriptionSet ds : context.getAllDescriptionSets()) {
                System.out.println("Ext(" + ds + ") = " + context.getExtent(ds));
            }

                // COMPUTING AND PRINTING ALL CLOSURES

            System.out.println();
            System.out.println("--------- ALL CLOSURES ---------");
            System.out.println();

            System.out.println(context.allDescriptionClosures());

            ConceptLattice cl = ConceptLattice.completeDescriptionLatticeLattice(context);
            System.out.println(cl);

            System.out.println("---------------------------------------------------------------");

            // Creating description set context
            // from an example file
            // representing attributeSet descriptions

        String filename2 = "src/examples/descriptionSet/interval.txt";

        DescriptionSetContext<IntervalDescription> context2 = new DescriptionSetContext<IntervalDescription>(filename2);

            // PRINTING OBSERVATIONS

        context2.printObservations();

            // PRINTING INITIAL DESCRIPTION SETS

        context2.printInitialDescriptionSets();

            // PRINTING ALL DESCRIPTION SETS

        context2.printAllDescriptionSets();

            // PRINTING MORE SPECIALISED INTENT

        System.out.println();
        for (String o : context2.getObservations()) {
            System.out.println("SpecInt(" + o + ") = " + context2.getSpecIntent(o));
        }

            // PRINTING INTENT

        System.out.println();
        for (String o : context2.getObservations()) {
            System.out.println("Int(" + o + ") = " + context2.getIntent(o));
        }

            // PRINTING EXTENT

        System.out.println();
        for (ComparableDescriptionSet ds : context2.getAllDescriptionSets()) {
            System.out.println("Ext(" + ds + ") = " + context2.getExtent(ds));
        }

            // COMPUTING AND PRINTING ALL CLOSURES

        System.out.println();
        System.out.println("--------- ALL CLOSURES ---------");
        System.out.println();

        System.out.println(context2.allDescriptionClosures());

        ConceptLattice cl2 = ConceptLattice.completeDescriptionLatticeLattice(context2);
        System.out.println(cl2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
