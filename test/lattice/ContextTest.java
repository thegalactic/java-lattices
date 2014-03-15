/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lattice;

import java.util.Collections;
import java.util.TreeSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 *
 * @author cguerin
 */
public class ContextTest {
    /**
     * Test the empty constructor of Context
     */
    @Test
    public void testEmptyContext() {
        Context context = new Context();
        assertEquals(context.getAttributes(), new TreeSet<Comparable>());
        assertEquals(context.getObservations(), new TreeSet<Comparable>());
    }
    /**
     * Test the copy constructor of Context
     */
    @Test
    public void testCopyContext() {
        Context context = new Context();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addToObservations("2");
        context.addToObservations("3");        
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        context.addExtentIntent("2", "a");
        context.addExtentIntent("3", "b");
        context.addExtentIntent("3", "c");
        Context copy = new Context(context);
        assertEquals(context.getAttributes(), copy.getAttributes());
        assertEquals(context.getObservations(), copy.getObservations());
        assertEquals(context.getIntent("1"), copy.getIntent("1"));
        assertEquals(context.getExtent("c"), copy.getExtent("c"));
    }
    /**
     * Test containsAttribute
     */
    @Test
    public void testContainsAttribute() {
        Context context = new Context();
        context.addToAttributes("a");
        assertTrue(context.containsAttribute("a"));
        assertFalse(context.containsObservation("b"));
    }
    /**
     * Test containsAttributes
     */
    @Test
    public void testContainsAllAttributes() {
        Context context = new Context();
        context.addToAttributes("a");
        context.addToAttributes("b");
        TreeSet<Comparable> attributes = new TreeSet();
        attributes.add("a");
        attributes.add("b");
        TreeSet<Comparable> attributesFalse = new TreeSet();
        attributesFalse.add("a");
        attributesFalse.add("c");
        assertTrue(context.containsAllAttributes(attributes));
        assertFalse(context.containsAllAttributes(attributesFalse));
    }
    /**
     * Test containsObservation
     */
    @Test
    public void testContainsObservation() {
        Context context = new Context();
        context.addToObservations("1");
        assertTrue(context.containsObservation("1"));
        assertFalse(context.containsObservation("2"));
    }
    /**
     * Test containsObservations
     */
    @Test
    public void testContainsAllObservations() {
        Context context = new Context();
        context.addToObservations("1");
        context.addToObservations("2");
        TreeSet<Comparable> observations = new TreeSet();
        observations.add("1");
        observations.add("2");
        TreeSet<Comparable> observationsFalse = new TreeSet();
        observationsFalse.add("1");
        observationsFalse.add("3");
        assertTrue(context.containsAllObservations(observations));
        assertFalse(context.containsAllObservations(observationsFalse));
    }
    /**
     * Test add attribute
     */
    @Test
    public void testAddAttribute() {
        Context context = new Context();
        assertTrue(context.addToAttributes("a"));
        assertFalse(context.addToAttributes("a"));
    }
    /**
     * Test add attributes
     */
    @Test
    public void testAddAttributes() {
        Context context = new Context();
        TreeSet<Comparable> attributes = new TreeSet();
        attributes.add("a");
        attributes.add("b");
        TreeSet<Comparable> attributesFalse = new TreeSet();
        attributesFalse.add("c");
        attributesFalse.add("a");
        assertTrue(context.addAllToAttributes(attributes));
        assertFalse(context.addAllToAttributes(attributesFalse));
    }
    /**
     * Test add observation
     */
    @Test
    public void testAddObservation() {
        Context context = new Context();
        assertTrue(context.addToObservations("1"));
        assertFalse(context.addToObservations("1"));
    }
    /**
     * Test add observations
     */
    @Test
    public void testAddObservations() {
        Context context = new Context();
        TreeSet<Comparable> observations = new TreeSet();
        observations.add("1");
        observations.add("2");
        TreeSet<Comparable> observationsFalse = new TreeSet();
        observationsFalse.add("3");
        observationsFalse.add("1");
        assertTrue(context.addAllToObservations(observations));
        assertFalse(context.addAllToObservations(observationsFalse));
    }
    /**
     * Test remove attribute
     */
    @Test
    public void testRemoveAttribute() {
        Context context = new Context();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addToObservations("2");
        context.addToObservations("3");
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        context.addExtentIntent("2", "a");
        context.addExtentIntent("3", "b");
        context.addExtentIntent("3", "c");
        assertTrue(context.removeFromAttributes("a"));
        assertFalse(context.getIntent("1").contains("a"));
        assertFalse(context.getIntent("2").contains("a"));
        assertFalse(context.removeFromAttributes("d"));
    }
    /**
     * Test remove observation
     */
    @Test
    public void testRemoveObservation() {
        Context context = new Context();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addToObservations("2");
        context.addToObservations("3");
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        context.addExtentIntent("2", "a");
        context.addExtentIntent("3", "b");
        context.addExtentIntent("3", "c");
        assertTrue(context.removeFromObservations("1"));
        assertFalse(context.getExtent("a").contains("1"));
        assertFalse(context.getExtent("b").contains("1"));
        assertFalse(context.removeFromAttributes("4"));
    }
    /**
     * Test extentNb
     */
    @Test
    public void testExtentNb() {
        Context context = new Context();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addToObservations("2");
        context.addToObservations("3");
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        context.addExtentIntent("2", "a");
        context.addExtentIntent("3", "b");
        context.addExtentIntent("3", "c");
        TreeSet<Comparable> attributes = new TreeSet();
        attributes.add("a");
        assertTrue(context.getExtentNb(attributes) == 2);
        attributes.add("b");
        assertTrue(context.getExtentNb(attributes) == 1);
        attributes.add("c");
        assertTrue(context.getExtentNb(attributes) == 0);
        attributes.remove("a");
        assertTrue(context.getExtentNb(attributes) == 1);
        attributes.remove("c");
        assertTrue(context.getExtentNb(attributes) == 2);
        attributes.remove("b");
        assertTrue(context.getExtentNb(attributes) == 3);
    }
    /**
     * Test intentNb
     */
    @Test
    public void testIntentNb() {
        Context context = new Context();
        context.addToAttributes("a");
        context.addToAttributes("b");
        context.addToAttributes("c");
        context.addToObservations("1");
        context.addToObservations("2");
        context.addToObservations("3");
        context.addExtentIntent("1", "a");
        context.addExtentIntent("1", "b");
        context.addExtentIntent("2", "a");
        context.addExtentIntent("3", "b");
        context.addExtentIntent("3", "c");
        TreeSet<Comparable> observations = new TreeSet();
        observations.add("1");
        assertTrue(context.getIntentNb(observations) == 2);
        observations.add("2");
        assertTrue(context.getIntentNb(observations) == 1);
        observations.add("3");
        assertTrue(context.getIntentNb(observations) == 0);        
        observations.remove("2");
        assertTrue(context.getIntentNb(observations) == 1);
        observations.remove("1");
        assertTrue(context.getIntentNb(observations) == 2);
    }
}
