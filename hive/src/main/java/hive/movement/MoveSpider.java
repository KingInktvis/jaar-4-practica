package hive.movement;

import hive.Board;
import hive.Coordinate;

public class MoveSpider {

    public static boolean canSpiderMove(Board board, Coordinate from, Coordinate to){
        return MoveCommon.getCoordinatesInStepAmount(board, from, 3).contains(to);
    }
}
