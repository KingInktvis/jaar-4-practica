package hive.move;

import hive.Coordinate;
import hive.movement.MoveCommon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Skyerzz-LAPOTOP on 30/10/2019.
 */
public class CommonMoveTest {

    @Test
    void getCoordinatesInRadiusZeroTest(){
        MoveCommon moveCommon = new MoveCommon();
        ArrayList<Coordinate> coords = moveCommon.getCoordinatesInRange(new Coordinate(0, 0), 0);
        assertTrue(coords.isEmpty());
    }

    @Test
    void getCoordinatesInRadiusOneTest(){
        MoveCommon moveCommon = new MoveCommon();
        ArrayList<Coordinate> coords = moveCommon.getCoordinatesInRange(new Coordinate(0, 0), 1);
        assertTrue(coords.size()==6);
        assertTrue(coords.contains(new Coordinate(-1, 0)));
        assertTrue(coords.contains(new Coordinate(0, -1)));
        assertTrue(coords.contains(new Coordinate(1, -1)));
        assertTrue(coords.contains(new Coordinate(1, 0)));
        assertTrue(coords.contains(new Coordinate(0, 1)));
        assertTrue(coords.contains(new Coordinate(-1, +1)));
    }

    @Test
    void getCoordinatesInRadiusTwoTest(){
        MoveCommon moveCommon = new MoveCommon();
        ArrayList<Coordinate> coords = moveCommon.getCoordinatesInRange(new Coordinate(0, 0), 2);
        assertTrue(coords.size()==18);
        assertTrue(coords.contains(new Coordinate(-1, 0)));
        assertTrue(coords.contains(new Coordinate(0, -1)));
        assertTrue(coords.contains(new Coordinate(1, -1)));
        assertTrue(coords.contains(new Coordinate(1, 0)));
        assertTrue(coords.contains(new Coordinate(0, 1)));
        assertTrue(coords.contains(new Coordinate(-1, +1)));
        assertTrue(coords.contains(new Coordinate(-2, 0)));
        assertTrue(coords.contains(new Coordinate(-1, -1)));
        assertTrue(coords.contains(new Coordinate(0, -2)));
        assertTrue(coords.contains(new Coordinate(1, -2)));
        assertTrue(coords.contains(new Coordinate(2, -2)));
        assertTrue(coords.contains(new Coordinate(2, -1)));
        assertTrue(coords.contains(new Coordinate(2, 0)));
        assertTrue(coords.contains(new Coordinate(1, 1)));
        assertTrue(coords.contains(new Coordinate(0, 2)));
        assertTrue(coords.contains(new Coordinate(-1, 2)));
        assertTrue(coords.contains(new Coordinate(-2, 2)));
        assertTrue(coords.contains(new Coordinate(-2, 1)));
    }
}
