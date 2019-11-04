package hive.movement;

import hive.Board;
import hive.Coordinate;
import nl.hanze.hive.Hive;

/**
 * Created by Skyerzz-LAPOTOP on 30/10/2019.
 */
public class MoveBeetle {

    public static boolean canBeetleMove(Board board, Coordinate from, Coordinate to) {
        return MoveCommon.getCoordinatesInRange(board, from, 1, true).contains(to);
    }

    public static boolean doesBeetleHaveMoveOptions(Board board, Coordinate beetle){
        return MoveCommon.getCoordinatesInRange(board, beetle, 1, true).size()>0;
    }
}
