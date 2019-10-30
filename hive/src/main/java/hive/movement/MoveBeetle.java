package hive.movement;

import hive.Board;
import hive.Coordinate;
import nl.hanze.hive.Hive;

/**
 * Created by Skyerzz-LAPOTOP on 30/10/2019.
 */
public class MoveBeetle {

    static boolean beetleMovement(Board board, Coordinate from, Coordinate to) {
        if (!from.areNeighbours(to)) {
            return false;
        }
        try {
            board.moveTile(from, to);
        } catch (Hive.IllegalMove illegalMove) {
            return false;
        }
        var value = board.allTilesConnected();
        try {
            board.moveTile(from, to);
        } catch (Hive.IllegalMove illegalMove) {
            return false;
        }
        return value;
    }
}
