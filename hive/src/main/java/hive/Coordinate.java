package hive;

import java.util.ArrayList;
import java.util.Objects;

public class Coordinate {
    private int q;
    private int r;

    public Coordinate(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    public ArrayList<Coordinate> commonNeighbours(Coordinate coordinate) {
        int qDiff = q - coordinate.getQ();
        int rDiff = r - coordinate.getR();
        ArrayList<Coordinate> res = new ArrayList<Coordinate>();
        if (qDiff == -1 && rDiff == 0) {
            res.add(new Coordinate(q + 1, r - 1));
            res.add(new Coordinate(q, r + 1));
        } else if (qDiff == -1 && rDiff == 1) {
            res.add(new Coordinate(q, r - 1));
            res.add(new Coordinate(q + 1, r));
        } else if (qDiff == 0 && rDiff == -1) {
            res.add(new Coordinate(q + 1, r));
            res.add(new Coordinate(q - 1, r + 1));
        } else if (qDiff == 1 && rDiff == -1) {
            res.add(new Coordinate(q, r + 1));
            res.add(new Coordinate(q - 1, r));
        } else if (qDiff == 1 && rDiff == 0) {
            res.add(new Coordinate(q - 1, r + 1));
            res.add(new Coordinate(q, r - 1));
        } else if (qDiff == 0 && rDiff == 1) {
            res.add(new Coordinate(q - 1, r));
            res.add(new Coordinate(q + 1, r - 1));
        }
        return res;
    }

    public boolean areNeighbours(Coordinate coordinate) {
        int qDiff = q - coordinate.getQ();
        int rDiff = r - coordinate.getR();
        return -1 <= qDiff && qDiff <= 1 && -1 <= rDiff && rDiff <= 1;
    }

    public ArrayList<Coordinate> adjacentCoordinates() {
        ArrayList<Coordinate> positions = new ArrayList<Coordinate>();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i != j) {
                    Coordinate c = new Coordinate(q + i, r + j);
                    positions.add(c);
                }
            }
        }
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Coordinate) {
            Coordinate c = (Coordinate) o;
            return q == c.getQ() && r == c.getR();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r);
    }
}
