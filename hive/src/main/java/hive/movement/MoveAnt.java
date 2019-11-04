package hive.movement;

import hive.Board;
import hive.Coordinate;

import java.util.ArrayList;

public class MoveAnt {

    public static boolean canAntMove(Board board, Coordinate from, Coordinate to){
        ArrayList<Coordinate> possibleOptions = MoveCommon.getCoordinatesInRange(board, from, Integer.MAX_VALUE, false);
        return possibleOptions.contains(to);
    }

    public static boolean doesAntHaveMoveOptions(Board board, Coordinate ant){
        return MoveCommon.getCoordinatesInRange(board, ant, Integer.MAX_VALUE, false).size()>0;
    }
}
