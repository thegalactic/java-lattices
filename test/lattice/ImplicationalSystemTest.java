package lattice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
/**
 *
 * @author jeff
 */
public class ImplicationalSystemTest {
    /**
     * Test for the empty constructor.
     */
    @Test
    public void testImplicationalSystemEmpty() {
        ImplicationalSystem is = new ImplicationalSystem();
        assertTrue(is.getRules().isEmpty());
        assertTrue(is.getSet().isEmpty());
    }
    /**
     * Test for the constructor from a set of rules.
     */
    @Test
    public void testImplicationalSystemFromRule() {
        TreeSet<Comparable> p = new TreeSet<Comparable>();
        p.add("p");
        TreeSet<Comparable> c = new TreeSet<Comparable>();
        c.add("c1");
        c.add("c2");
        Rule r = new Rule(p, c);
        TreeSet<Rule> sigma = new TreeSet<Rule>();
        sigma.add(r);
        ImplicationalSystem is = new ImplicationalSystem(sigma);
        assertTrue(is.getRules().contains(r));
    }
    /**
     * Test for the constructor from a copy.
     */
    @Test
    public void testImplicationalSystemCopy() {
        TreeSet<Comparable> p = new TreeSet<Comparable>();
        p.add("p");
        TreeSet<Comparable> c = new TreeSet<Comparable>();
        c.add("c1");
        c.add("c2");
        Rule r = new Rule(p, c);
        TreeSet<Rule> sigma = new TreeSet<Rule>();
        sigma.add(r);
        ImplicationalSystem is = new ImplicationalSystem(sigma);
        ImplicationalSystem isCopy = new ImplicationalSystem(is);
        assertTrue(isCopy.getRules().contains(r));
    }
    /**
     * Test for the constructor from a file & the toString method.
     */
    @Test
    public void testImplicationalSystemFile() {
        try {
            File file = File.createTempFile("junit", ".rule1");
//            String filename = file.getName();
//            File outfile = new File(filename);
            FileWriter out = new FileWriter(file);
            out.write("a b c \n");
            out.write("a -> b c \n");
            out.close();
            ImplicationalSystem is = new ImplicationalSystem(file.getAbsolutePath());
            assertEquals(is.toString(), "a b c \n" + "a  -> b c \n");
            assertEquals(is.getRules().size(), 1);
            file.delete();
        } catch (IOException e) {
            System.out.println("IOException : " + e.getMessage());
        }
    }
    /**
     * Test the random method.
     */
    @Test
    public void testrandom() {
        ImplicationalSystem is = ImplicationalSystem.random(13, 7);
        assertEquals(is.getRules().size(), 7);
        assertEquals(is.getSet().size(), 13);
    }
    /**
     * Test for the accessors methods.
     */
    @Test
    public void testaccessors() {
        ImplicationalSystem is = ImplicationalSystem.random(13, 7);
        assertEquals(is.getRules().size(), 7);
        assertEquals(is.getSet().size(), 13);
        assertEquals(is.nbRules(), 7);
        assertEquals(is.getSet().size(), is.nbElements());
    }
    /**
     * Test for the addElement method.
     */
    @Test
    public void testaddElement() {
        ImplicationalSystem is = new ImplicationalSystem();
        is.addElement('e');
        assertTrue(is.getSet().contains('e'));
    }
    /**
     * Test for the addAllElements method.
     */
    @Test
    public void testaddAllElements() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('e');
        elements.add('f');
        is.addAllElements(elements);
        assertEquals(is.nbElements(), 2);
    }
    /**
     * Test for the addRule, containsRule and deleteElement methods.
     */
    @Test
    public void testdeleteElement() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('e');
        is.addAllElements(elements);
        Rule r = new Rule();
        r.addToPremise('e');
        r.addToConclusion('e');
        is.addRule(r);
        assertTrue(is.containsRule(r));
        is.deleteElement('e');
        assertFalse(is.containsRule(r));
    }
    /**
     * Test for the checkRuleElements method.
     */
    @Test
    public void testcheckRuleElements() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('e');
        elements.add('f');
        is.addAllElements(elements);
        Rule r = new Rule();
        r.addToPremise('e');
        r.addToConclusion('f');
        is.addRule(r);
        assertTrue(is.checkRuleElements(r));
    }
    /**
     * Test for the removeRule method.
     */
    @Test
    public void testremoveRule() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('e');
        is.addAllElements(elements);
        Rule r = new Rule();
        r.addToPremise('e');
        r.addToConclusion('e');
        is.addRule(r);
        assertTrue(is.containsRule(r));
        is.removeRule(r);
        assertFalse(is.containsRule(r));
    }
    /**
     * Test for the replaceRule method.
     */
    @Test
    public void testreplaceRule() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('e');
        elements.add('f');
        is.addAllElements(elements);
        Rule ra = new Rule();
        ra.addToPremise('e');
        ra.addToConclusion('e');
        is.addRule(ra);
        assertTrue(is.containsRule(ra));
        Rule rb = new Rule();
        rb.addToPremise('f');
        rb.addToConclusion('f');
        is.addRule(ra);
        is.replaceRule(ra, rb);
        assertFalse(is.containsRule(ra));
        assertTrue(is.containsRule(rb));
    }
    /**
     * Test for the toFile method.
     */
    @Test
    public void testtoFile() {
        try {
            ImplicationalSystem is = new ImplicationalSystem();
            TreeSet<Comparable> elements = new TreeSet<Comparable>();
            elements.add('a');
            elements.add('b');
            elements.add('c');
            is.addAllElements(elements);
            Rule r = new Rule();
            r.addToPremise('a');
            r.addToConclusion('b');
            r.addToConclusion('c');
            is.addRule(r);
            File file = File.createTempFile("junit", ".rule2");
            String filename = file.getName();
            file.delete();
            is.toFile(filename);
            String content = "";
            file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                content += scanner.nextLine();
            }
            assertEquals(content, "a b c a  -> b c ");
            file.delete();
        } catch (IOException e) { System.out.println("IOException : " + e.getMessage()); }
    }
    /**
     * Test for the isProper, isUnary, isCompact method.
     */
    @Test
    public void testisProper() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('a');
        elements.add('b');
        is.addAllElements(elements);
        Rule r = new Rule();
        r.addToPremise('a');
        r.addToConclusion('b');
        is.addRule(r);
        assertTrue(is.isProper());
        assertTrue(is.isUnary());
        assertTrue(is.isCompact());
    }
    /**
     * Test for the isRightMaximal method.
     *
     * @todo To be improved ...
     */
    @Test
    public void testisRightMaximal() {
        ImplicationalSystem is = new ImplicationalSystem();
//        TreeSet<Comparable> elements = new TreeSet<Comparable>();
//        elements.add('a');
//        is.addAllElements(elements);
//        Rule r = new Rule();
//        r.addToPremise('a');
//        r.addToConclusion('a');
//        is.addRule(r);
        assertTrue(is.isRightMaximal());
    }
    /**
     * Test for the isLeftMinimal method.
     */
    @Test
    public void testisLeftMinimal() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('a');
        is.addAllElements(elements);
        Rule r = new Rule();
        r.addToPremise('a');
        r.addToConclusion('a');
        is.addRule(r);
        assertTrue(is.isLeftMinimal());
    }
    /**
     * Test for the isDirect method.
     */
    public void testisDirect() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('a');
        elements.add('b');
        elements.add('c');
        is.addAllElements(elements);
        Rule r = new Rule();
        r.addToPremise('a');
        r.addToConclusion('b');
        is.addRule(r);
        assertTrue(is.isDirect());
        Rule n = new Rule();
        n.addToPremise('b');
        n.addToConclusion('c');
        is.addRule(n);
        assertFalse(is.isDirect());
    }
    /**
     * Test for the isMinimum method.
     */
    public void testisMinimum() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('a');
        elements.add('b');
        is.addAllElements(elements);
        Rule r = new Rule();
        r.addToPremise('a');
        r.addToConclusion('b');
        is.addRule(r);
        assertTrue(is.isMinimum());
    }
    /**
     * Test for the isCanonicalDirectBasis and isCanonicalBasis methods.
     */
    @Test
    public void testisBasis() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('a');
        elements.add('b');
        is.addAllElements(elements);
        Rule r = new Rule();
        r.addToPremise('a');
        r.addToConclusion('b');
        is.addRule(r);
        assertTrue(is.isCanonicalDirectBasis());
        assertTrue(is.isCanonicalBasis());
    }
    /**
     * Test for the isIncludedIn methods.
     */
    @Test
    public void testisIncludedIn() {
        ImplicationalSystem is = new ImplicationalSystem();
        TreeSet<Comparable> elements = new TreeSet<Comparable>();
        elements.add('a');
        elements.add('b');
        is.addAllElements(elements);
        Rule r = new Rule();
        r.addToPremise('a');
        r.addToConclusion('b');
        is.addRule(r);
        assertTrue(is.isIncludedIn(is));
    }
    /**
     * Test for the makeProper methods.
     */
    @Test
    public void testmakeProper() {
        ImplicationalSystem is = ImplicationalSystem.random(13, 8);
        is.makeProper();
        assertTrue(is.isProper());
    }
    /**
     * Test for the makeUnary methods.
     */
    @Test
    public void testmakeUnary() {
        ImplicationalSystem is = ImplicationalSystem.random(13, 8);
        is.makeUnary();
        assertTrue(is.isUnary());
    }
    /**
     * Test for the makeCompact methods.
     */
    @Test
    public void testmakeCompact() {
        ImplicationalSystem is = ImplicationalSystem.random(13, 8);
        is.makeCompact();
        assertTrue(is.isCompact());
    }
    /**
     * Test for the makeRightMaximal methods.
     *
     * @todo Find the bug ...
     */
    @Test
    public void testmakeRightMaximal() {
        ImplicationalSystem is = ImplicationalSystem.random(13, 8);
        //is.makeRightMaximal();
        //assertTrue(is.isRightMaximal());
    }
    /**
     * Test for the makeLeftMinimal methods.
     */
    @Test
    public void testmakeLeftMinimal() {
        ImplicationalSystem is = ImplicationalSystem.random(13, 8);
        is.makeLeftMinimal();
        assertTrue(is.isLeftMinimal());
    }
    /**
     * Test for the makeDirect methods.
     */
    @Test
    public void testmakeDirect() {
        ImplicationalSystem is = ImplicationalSystem.random(13, 8);
        is.makeDirect();
        assertTrue(is.isDirect());
    }
    /**
     * Test for the makeMinimum methods.
     */
    @Test
    public void testmakeMinimum() {
        ImplicationalSystem is = ImplicationalSystem.random(13, 8);
        is.makeMinimum();
        assertTrue(is.isMinimum());
    }
    /**
     * Test for the makeCanonicalDirectBasis methods.
     */
    @Test
    public void testmakeCanonicalDirectBasis() {
        ImplicationalSystem is = ImplicationalSystem.random(13, 8);
        is.makeCanonicalDirectBasis();
        assertTrue(is.isCanonicalDirectBasis());
    }
    /**
     * Test for the makeCanonicalBasis methods.
     */
    @Test
    public void testmakeCanonicalBasis() {
        ImplicationalSystem is = ImplicationalSystem.random(13, 8);
        is.makeCanonicalBasis();
        assertTrue(is.isCanonicalBasis());
    }
    /**
     * Test for the representativeGraph method.
     *
     * @todo implementing test.
     */
    @Test
    public void testrepresentativeGraph() {
    }
    /**
     * Test for the dependencyGraph method.
     *
     * @todo implementing test.
     */
    @Test
    public void testdependencyGraph() {
    }
    /**
     * Test for the reduction method.
     *
     * @todo implementing test.
     */
    @Test
    public void testreduction() {
    }
}
