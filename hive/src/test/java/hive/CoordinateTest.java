package hive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void testEquals() {
        var c1 = new Coordinate(1, 2);
        var c2 = new Coordinate(10, 1);
        var c3 = new Coordinate(1, 2);
        assertNotEquals(c1, c2);
        assertEquals(c1, c3);
    }

    @Test
    void testHashCode() {
        var c1 = new Coordinate(1, 2);
        var c2 = new Coordinate(10, 1);
        var c3 = new Coordinate(1, 2);
        assertNotEquals(c1.hashCode(), c2.hashCode());
        assertEquals(c1.hashCode(), c3.hashCode());
    }

    @Test
    void testHashCodeMirroredValues() {
        var c1 = new Coordinate(1, 2);
        var c2 = new Coordinate(2, 1);
        assertNotEquals(c1, c2);
    }

    @Test
    void testEqualsMirroredValues() {
        var c1 = new Coordinate(1, 2);
        var c2 = new Coordinate(2, 1);
        assertNotEquals(c1, c2);
    }

    @Test
    void testEqualsOtherObject() {
        var c1 = new Coordinate(1, 2);
        var text = "Random string";
        assertNotEquals(c1, text);
    }

    @Test
    void testCommonNeighbours() {
        var c1 = new Coordinate(0,0);
        var c2 = new Coordinate(1, 0);
        var c3 = new Coordinate(1, -1);
        var c4 = new Coordinate(0, 1);
        var c5 = new Coordinate(-1, 1);
        var c6 = new Coordinate(-1, 0);
        var c7 = new Coordinate(0, -1);
        var res = c1.commonNeighbours(c2);
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
}