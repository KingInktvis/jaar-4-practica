package hive;

import nl.hanze.hive.Hive;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Board {
    private HashMap<Coordinate, Stack<BoardTile>> board;
    private int tileCount = 0;

    Board() {
        board = new HashMap<>();
    }

    private Stack<BoardTile> getBoardPosition(Coordinate coordinate) {
        var res = board.get(coordinate);
        if (res == null) {
            var stack = new Stack<BoardTile>();
            board.put(coordinate, stack);
            res = board.get(coordinate);
        }
        return res;
    }

    void placeTile(Player player, Tile tile, Coordinate coordinate) {
        var newTile = new BoardTile(tile, player);
        var position = getBoardPosition(coordinate);
        position.push(newTile);
        ++tileCount;
    }

    BoardTile getTile(Coordinate coordinate) {
        var position = getBoardPosition(coordinate);
        if (position.empty()) {
            return null;
        }
        return position.peek();
    }

    void moveTile(Coordinate origin, Coordinate destination) throws Hive.IllegalMove {
        var ogPosition = getBoardPosition(origin);
        if (ogPosition.empty()) {
            throw new Hive.IllegalMove();
        }
        var next = getBoardPosition(destination);
        var tile = ogPosition.pop();
        next.push(tile);
    }

    int inPlayOf(Player player, Tile tile) {
        var count = 0;
        for (var position : board.entrySet()) {
            for (var entry : position.getValue()) {
                if (entry.getType() == tile && entry.getPlayer() == player) {
                    ++count;
                }
            }
        }
        return count;
    }

    ArrayList<Coordinate> getTileLocations(Tile tile, Player player) {
        var ret = new ArrayList<Coordinate>();
        for (var position : board.entrySet()) {
            for (var entry : position.getValue()) {
                if (entry.getType() == tile && entry.getPlayer() == player) {
                    ret.add(position.getKey());
                }
            }
        }
        return ret;
    }

    ArrayList<BoardTile> getNeighbours(Coordinate coordinate) {
        var ret = new ArrayList<BoardTile>();
        var positions = neighbouringCoordinates(coordinate);
        for (var position : positions) {
            var tmp = getBoardPosition(position);
            if (!tmp.isEmpty()) {
                ret.add(tmp.peek());
            }
        }
        return ret;
    }

    private ArrayList<Coordinate> neighbouringCoordinates(Coordinate coordinate) {
        return coordinate.adjacentCoordinates();
    }

    boolean allTilesConnected() {
        Coordinate start = null;
        var list = new ArrayList<BoardTile>();
        for (var position : board.entrySet()) {
            if (!position.getValue().isEmpty()) {
                start = position.getKey();
                break;
            }
        }
        if (start == null) {
            return true;
        }
        recursiveAddToList(list, start);
        return list.size() == tileCount;
    }

    private void recursiveAddToList(ArrayList<BoardTile> list, Coordinate position) {
        var tiles = getBoardPosition(position);
        if (tiles.isEmpty()) return;
        for (var tile : tiles) {
            if (!list.contains(tile)) {
                list.add(tile);
                for (var c : neighbouringCoordinates(position)) {
                    recursiveAddToList(list, c);
                }
            }
        }
    }

    ArrayList<Coordinate> getEmptyAdjacentLocations(Coordinate coordinate) {
        var adjacent = coordinate.adjacentCoordinates();
        var empty = new ArrayList<Coordinate>();
        for (var tile : adjacent) {
            if (getTile(tile) == null) {
                empty.add(tile);
            }
        }
        return empty;
    }
}
