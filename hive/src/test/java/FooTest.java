import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FooTest {

    @Test
    void testBar() {
        var foo = new Foo();
        assertEquals(foo.bar(), "bar");
    }
}
