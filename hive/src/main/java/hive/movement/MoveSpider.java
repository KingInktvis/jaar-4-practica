package hive.movement;

import hive.Board;
import hive.Coordinate;

public class MoveSpider {

    public static boolean canSpiderMove(Board board, Coordinate from, Coordinate to){
        return MoveCommon.getCoordinatesInStepAmount(board, from, 3).contains(to);
    }

    public static boolean doesSpiderHaveMoveOptions(Board board, Coordinate spider){
        return MoveCommon.getCoordinatesInStepAmount(board, spider, 3).size()>0;
    }
}
