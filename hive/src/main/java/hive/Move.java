package hive;

import java.util.stream.Collectors;

public class Move {
    static boolean isValidMove(Board board, Coordinate from, Coordinate to) {
        return true;
    }

    static boolean checkToAdjacentTileWithOneStep(Board board, Coordinate to) {
        var neighbours = board.getNeighbours(to);
        if (neighbours.size() <= 1) {
            return false;
        }
        return true;
    }

    static boolean allTilesAreConnected(Board board) {
        return board.allTilesConnected();
    }

    static boolean stayConnectedOneStep(Board board, Coordinate from, Coordinate to) {
//        var fromNeighbours = board.getNeighbours(from);
//        var toNeighbours = board.getNeighbours(to);
//        var sharedNeighbours = fromNeighbours.stream()
//                .distinct()
//                .filter(toNeighbours::contains)
//                .collect(Collectors.toSet());
//        return sharedNeighbours.size() >= 1;
        var shared = from.commonNeighbours(to);
        return board.getTile(shared.get(0)) != null || board.getTile(shared.get(1)) != null;
    }

    static boolean gapForMovement(Board board, Coordinate from, Coordinate to) {
        var shared = from.commonNeighbours(to);
        return board.getTile(shared.get(0)) == null || board.getTile(shared.get(1)) == null;
    }
}
