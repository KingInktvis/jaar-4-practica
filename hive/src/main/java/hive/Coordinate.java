package hive;

import java.util.Objects;

public class Coordinate {
    private int q;
    private int r;

    Coordinate(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Coordinate) {
            var c = (Coordinate) o;
            return q == c.getQ() && r == c.getR();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r);
    }
}
