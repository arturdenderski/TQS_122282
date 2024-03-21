/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        setD = new BoundedSetOfNaturals(2);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    public void testAddElement() {
        setA.add(21);
        assertTrue(setA.contains(21));
        assertEquals(1, setA.size());

        assertThrows(IllegalArgumentException.class, () -> setB.add(22));
        assertFalse(setB.contains(22));
        assertEquals(6, setB.size());

        setD.add(11);
        assertThrows(IllegalArgumentException.class, () -> setD.add(11));

        assertThrows(IllegalArgumentException.class, () -> setA.add(0));
        assertThrows(IllegalArgumentException.class, () -> setA.add(-100));
    }

    @Test
    public void testIntersection() {
        assertTrue(setB.intersects(setC));
        setA.add(70);
        assertFalse(setA.intersects(setC));
    }

    @Test
    public void testEquals() {
        BoundedSetOfNaturals setE = setA;
        assertTrue(setE.equals(setA));
        assertFalse(setE.equals(null));
        assertFalse(setE.equals(5));
    }

    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }
}
