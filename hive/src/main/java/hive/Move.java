package hive;

import nl.hanze.hive.Hive;

import java.util.ArrayList;

public class Move {
    static boolean isValidMove(Board board, Coordinate from, Coordinate to) {
        var tile = board.getTile(from);
        switch (tile.getType()) {
            case QUEEN_BEE:
                return queenMovement(board, from, to);
            case SPIDER:
                break;
            case BEETLE:
                return beetleMovement(board, from, to);
            case GRASSHOPPER:
                break;
            case SOLDIER_ANT:
                return antMovement(board, from, to);
        }
        return true;
    }

    //todo remove function
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
        var shared = from.commonNeighbours(to);
        return board.getTile(shared.get(0)) != null || board.getTile(shared.get(1)) != null;
    }

    static boolean gapForMovement(Board board, Coordinate from, Coordinate to) {
        var shared = from.commonNeighbours(to);
        return board.getTile(shared.get(0)) == null || board.getTile(shared.get(1)) == null;
    }

    private static boolean beetleMovement(Board board, Coordinate from, Coordinate to) {
        if (!from.areNeighbours(to) || from.equals(to)) {
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

    private static boolean queenMovement(Board board, Coordinate from, Coordinate to) {
        if (!from.areNeighbours(to) || from.equals(to)) {
            return false;
        }
        if (stayConnectedOneStep(board, from, to) && gapForMovement(board, from, to)) {
            try {
                board.moveTile(from, to);
            } catch (Hive.IllegalMove illegalMove) {
                illegalMove.printStackTrace();
            }
            boolean value;
            value = board.allTilesConnected();
            try {
                board.moveTile(to, from);
            } catch (Hive.IllegalMove illegalMove) {
                illegalMove.printStackTrace();
            }
            return value;
        } else {
            return false;
        }
    }

    private static boolean antMovement(Board board, Coordinate from, Coordinate to) {
        var history = new ArrayList<Coordinate>();
        history.add(from);
        return dfsPath(board, history, from, to);
    }

    private static boolean dfsPath(Board board, ArrayList<Coordinate> visited, Coordinate start, Coordinate destination) {
        var adjacent = board.getEmptyAdjacentLocations(start);
        for (var i : visited) {
            adjacent.remove(i);
        }
        for (var i : adjacent) {
            if (stayConnectedOneStep(board, start, i) && gapForMovement(board, start, i)) {
                try {
                    board.moveTile(start, i);
                } catch (Hive.IllegalMove illegalMove) {
                    illegalMove.printStackTrace();
                }
                if (board.allTilesConnected()) {
                    if (i.equals(destination)) {
                        return true;
                    }else {
                        visited.add(i);
                        var tmp = dfsPath(board, visited, i, destination);
                        visited.remove(i);
                        if (tmp) {
                            return true;
                        }
                    }
                }
                try {
                    board.moveTile(i, start);
                } catch (Hive.IllegalMove illegalMove) {
                    illegalMove.printStackTrace();
                }
            }
        }
        return false;
    }
}
