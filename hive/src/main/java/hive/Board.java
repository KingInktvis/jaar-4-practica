package hive;

import nl.hanze.hive.Hive;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Board {
    private HashMap<Coordinate, Stack<BoardTile>> board;
    private int tileCount = 0;

    public Board() {
        board = new HashMap<>();
    }

    private Stack<BoardTile> getBoardPosition(Coordinate coordinate) {
        Stack<BoardTile> position = board.get(coordinate);
        if (position == null) {
            Stack<BoardTile> stack = new Stack<BoardTile>();
            board.put(coordinate, stack);
            position = board.get(coordinate);
        }
        return position;
    }

    public void placeTile(Player player, Tile tile, Coordinate coordinate) {
        BoardTile newTile = new BoardTile(tile, player);
        Stack<BoardTile> position = getBoardPosition(coordinate);
        position.push(newTile);
        ++tileCount;
    }

    public BoardTile getTile(Coordinate coordinate) {
        Stack<BoardTile> position = getBoardPosition(coordinate);
        if (position.empty()) {
            return null;
        }
        return position.peek();
    }

    public void moveTile(Coordinate origin, Coordinate destination) throws Hive.IllegalMove {
        Stack<BoardTile> ogPosition = getBoardPosition(origin);
        if (ogPosition.empty()) {
            throw new Hive.IllegalMove();
        }
        Stack<BoardTile> next = getBoardPosition(destination);
        BoardTile tile = ogPosition.pop();
        next.push(tile);
    }

    int inPlayOf(Player player, Tile tile) {
        int count = 0;
        for (Map.Entry<Coordinate, Stack<BoardTile>> position : board.entrySet()) {
            for (BoardTile entry : position.getValue()) {
                if (entry.getType() == tile && entry.getPlayer() == player) {
                    ++count;
                }
            }
        }
        return count;
    }

    ArrayList<Coordinate> getTileLocations(Tile tile, Player player) {
        ArrayList<Coordinate> list = new ArrayList<Coordinate>();
        for (Map.Entry<Coordinate, Stack<BoardTile>> position : board.entrySet()) {
            for (BoardTile entry : position.getValue()) {
                if (entry.getType() == tile && entry.getPlayer() == player) {
                    list.add(position.getKey());
                }
            }
        }
        return list;
    }

    public ArrayList<BoardTile> getNeighbours(Coordinate coordinate) {
        ArrayList<BoardTile> list = new ArrayList<BoardTile>();
        ArrayList<Coordinate> locations = coordinate.adjacentCoordinates();
        for (Coordinate location : locations) {
            Stack<BoardTile> position = getBoardPosition(location);
            if (!position.isEmpty()) {
                list.add(position.peek());
            }
        }
        return list;
    }

    public boolean allTilesConnected() {
        Coordinate start = null;
        ArrayList<BoardTile> list = new ArrayList<BoardTile>();
        for (Map.Entry<Coordinate, Stack<BoardTile>> position : board.entrySet()) {
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
        Stack<BoardTile> tiles = getBoardPosition(position);
        if (tiles.isEmpty()) return;
        for (BoardTile tile : tiles) {
            if (!list.contains(tile)) {
                list.add(tile);
                for (Coordinate c : position.adjacentCoordinates()) {
                    recursiveAddToList(list, c);
                }
            }
        }
    }

    public ArrayList<Coordinate> getEmptyAdjacentLocations(Coordinate coordinate) {
        ArrayList<Coordinate> adjacent = coordinate.adjacentCoordinates();
        ArrayList<Coordinate> empty = new ArrayList<Coordinate>();
        for (Coordinate tile : adjacent) {
            if (getTile(tile) == null) {
                empty.add(tile);
            }
        }
        return empty;
    }

    public int amountOfCoordinatesOccupied(){
        return tileCount;
    }
}
