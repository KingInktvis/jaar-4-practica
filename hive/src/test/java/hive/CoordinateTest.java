package hive;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
}