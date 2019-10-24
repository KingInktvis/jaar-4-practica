package hive;

import java.util.ArrayList;
import java.util.Objects;

public class Coordinate {
    private int q;
    private int r;

    Coordinate(int q, int r) {
        this.q = q;
        this.r = r;
    }

    int getQ() {
        return q;
    }

    int getR() {
        return r;
    }

    ArrayList<Coordinate> commonNeighbours(Coordinate coordinate) {
        var qDiff = q - coordinate.getQ();
        var rDiff = r - coordinate.getR();
        var res = new ArrayList<Coordinate>();
        if (qDiff == -1 && rDiff == 0) {
            res.add(new Coordinate(q+1, r-1));
            res.add(new Coordinate(q, r+1));
        } else if (qDiff == -1 && rDiff == 1) {
            res.add(new Coordinate(q, r-1));
            res.add(new Coordinate(q+1, r));
        } else if (qDiff == 0 && rDiff == -1) {
            res.add(new Coordinate(q+1, r));
            res.add(new Coordinate(q-1, r+1));
        } else if (qDiff == 1 && rDiff == -1) {
            res.add(new Coordinate(q, r+1));
            res.add(new Coordinate(q-1, r));
        } else if (qDiff == 1 && rDiff == 0) {
            res.add(new Coordinate(q-1, r+1));
            res.add(new Coordinate(q, r-1));
        } else if (qDiff == 0 && rDiff == 1) {
            res.add(new Coordinate(q-1, r));
            res.add(new Coordinate(q+1, r-1));
        }
        return res;
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
