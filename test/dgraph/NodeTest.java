package dgraph;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the dgraph.Node class.
 */
public class NodeTest {
    /**
     * Test the full constructor.
     */
    @Test
    public void testConstructorFull() {
        Object content = new Object();
        Node node1 = new Node(content);
        assertEquals(node1.content(), content);

        Node node2 = new Node();
        assertEquals(node1.identifier() + 1, node2.identifier());
    }

    /**
     * Test the empty constructor.
     */
    @Test
    public void testConstructorEmpty() {
        Node node = new Node();
        assertEquals(node.content(), null);
    }

    /**
     * Test the copy constructor.
     */
    @Test
    public void testConstructorCopy() {
        Object content = new Object();
        Node node = new Node(content);
        Node copy = new Node(node);
        assertEquals(copy.content(), content);
        assertEquals(node.identifier(), copy.identifier());
    }

    /**
     * Test the toString() method.
     */
    @Test
    public void testToString() {
        String content = new String("Hello World");
        Node node = new Node(content);
        assertEquals(node.toString(), "HelloWorld");
    }

    /**
     * Test the toDot() method.
     */
    @Test
    public void testToDot() {
        String content = new String("Hello \"World\"");
        Node node = new Node(content);
        assertEquals(node.toDot(), node.identifier() + " [label=\"HelloWorld\"]");
    }

    /**
     * Test the copy() method.
     */
    @Test
    public void testCopy() {
        Object content = new Object();
        Node node = new Node(content);
        Node copy = node.copy();
        assertEquals(copy.content(), content);
    }

    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        Node node = new Node();
        Node copy = new Node(node);
        assertTrue(copy.equals(node));
    }

    /**
     * Test the hashCode() method.
     */
    @Test
    public void testHashCode() {
        Node node = new Node();
        assertEquals(node.identifier(), node.hashCode());
    }

    /**
     * Test the compareTo() method.
     */
    @Test
    public void testCompareTo() {
        Node node1 = new Node();
        Node node2 = new Node();
        assertEquals(node1.compareTo(node1), 0);
        assertTrue(node1.compareTo(node2) < 0);
        assertTrue(node2.compareTo(node1) > 0);
    }
}

