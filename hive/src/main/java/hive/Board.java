package hive;

import nl.hanze.hive.Hive;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Board {
    private HashMap<Coordinate, Stack<BoardTile>> board;

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
    }

    int inPlayOf(Player player, Tile tile) {
        var count = 0;
        for (var position: board.entrySet()) {
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
        var count = 0;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i != j) {
                    ++count;
                    var c = new Coordinate(coordinate.getQ() + i, coordinate.getR() + j);
                    var tmp = getBoardPosition(c);
                    if (!tmp.isEmpty()) {
                        ret.add(tmp.peek());
                    }
                }
            }
        }
        return ret;
    }
}
