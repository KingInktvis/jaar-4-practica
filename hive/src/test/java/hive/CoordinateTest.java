package hive;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void testEquals() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(10, 1);
        Coordinate c3 = new Coordinate(1, 2);
        assertNotEquals(c1, c2);
        assertEquals(c1, c3);
    }

    @Test
    void testHashCode() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(10, 1);
        Coordinate c3 = new Coordinate(1, 2);
        assertNotEquals(c1.hashCode(), c2.hashCode());
        assertEquals(c1.hashCode(), c3.hashCode());
    }

    @Test
    void testHashCodeMirroredValues() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(2, 1);
        assertNotEquals(c1, c2);
    }

    @Test
    void testEqualsMirroredValues() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(2, 1);
        assertNotEquals(c1, c2);
    }

    @Test
    void testEqualsOtherObject() {
        Coordinate c1 = new Coordinate(1, 2);
        String text = "Random string";
        assertNotEquals(c1, text);
    }

    @Test
    void testCommonNeighbours() {
        Coordinate c1 = new Coordinate(0, 0);
        Coordinate c2 = new Coordinate(1, 0);
        Coordinate c3 = new Coordinate(1, -1);
        Coordinate c4 = new Coordinate(0, 1);
        Coordinate c5 = new Coordinate(-1, 1);
        Coordinate c6 = new Coordinate(-1, 0);
        Coordinate c7 = new Coordinate(0, -1);
        ArrayList<Coordinate> res = c1.commonNeighbours(c2);
        assertTrue(res.contains(c3));
        assertTrue(res.contains(c4));
        res = c1.commonNeighbours(c3);
        assertTrue(res.contains(c2));
        assertTrue(res.contains(c7));
        res = c1.commonNeighbours(c4);
        assertTrue(res.contains(c2));
        assertTrue(res.contains(c5));
        res = c1.commonNeighbours(c5);
        assertTrue(res.contains(c6));
        assertTrue(res.contains(c4));
        res = c1.commonNeighbours(c6);
        assertTrue(res.contains(c5));
        assertTrue(res.contains(c7));
        res = c1.commonNeighbours(c7);
        assertTrue(res.contains(c6));
        assertTrue(res.contains(c3));
    }

    @Test
    void testAreNeighbours() {
        Coordinate c1 = new Coordinate(0, 0);
        Coordinate c2 = new Coordinate(1, 0);
        Coordinate c3 = new Coordinate(1, -1);
        Coordinate c4 = new Coordinate(0, 1);
        Coordinate c5 = new Coordinate(-1, 1);
        Coordinate c6 = new Coordinate(-1, 0);
        Coordinate c7 = new Coordinate(0, -1);
        assertTrue(c1.areNeighbours(c2));
        assertTrue(c1.areNeighbours(c3));
        assertTrue(c1.areNeighbours(c4));
        assertTrue(c1.areNeighbours(c5));
        assertTrue(c1.areNeighbours(c6));
        assertTrue(c1.areNeighbours(c7));
        assertFalse(c2.areNeighbours(c5));
    }
}