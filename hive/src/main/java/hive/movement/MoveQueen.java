package hive.movement;

import hive.Board;
import hive.Coordinate;

public class MoveQueen {

    public static boolean canQueenMove(Board board, Coordinate from, Coordinate to){
        boolean move = MoveCommon.getCoordinatesInRange(board, from, 1, false).contains(to);
        return move;
    }

}
