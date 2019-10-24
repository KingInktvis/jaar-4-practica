package hive;

import nl.hanze.hive.Hive;

import java.util.ArrayList;
import java.util.HashMap;

public class HiveGame implements Hive {
    private Player turn = Player.WHITE;
    private Board board;
    private boolean firstTurn = true;
    private HashMap<Tile, Integer> tileLimit = new HashMap<Tile, Integer>() {{
        put(Tile.QUEEN_BEE, 1);
        put(Tile.SPIDER, 2);
        put(Tile.BEETLE, 2);
        put(Tile.SOLDIER_ANT, 3);
        put(Tile.GRASSHOPPER, 3);
    }};

    HiveGame() {
        board = new Board();
    }

    HiveGame(Board board) {
        this.board = board;
    }

    @Override
    public void play(Tile tile, int q, int r) throws IllegalMove {
        var coordinate = new Coordinate(q, r);
        var existing = board.getTile(coordinate);
        if (existing != null) {
            throw new IllegalMove();
        }
        var neighbours = board.getNeighbours(coordinate);
        // Throws an exception if there are no tiles next to the location except when it is the first turn
        if ((!firstTurn || turn == Player.BLACK) && neighbours.size() == 0) {
            throw new IllegalMove();
        }
        // Throw exception if all of these kind of tiles are already in play
        var currentlyInPlay = board.inPlayOf(turn, tile);
        var limit = tileLimit.get(tile);
        if (currentlyInPlay >= limit) {
            throw new IllegalMove();
        }
        connectedToTile(neighbours);

        // Throw exception if there already 3 or more of the players tiles placed on the board and the queen is not
        if (tile != Tile.QUEEN_BEE && board.inPlayOf(turn, Tile.QUEEN_BEE) == 0) {
            var count = 0;
            for (var t : Tile.values()) {
                count += board.inPlayOf(turn, t);
            }
            if (count >= 3) {
                throw new IllegalMove();
            }
        }

        board.placeTile(turn, tile, coordinate);
        // Change the value of first turn after the first turn
        if (firstTurn && turn == Player.BLACK) {
            firstTurn = false;
        }
        switchPlayerTurn();
    }

    private void connectedToTile(ArrayList<BoardTile> neighbours) throws IllegalMove {
        // Can not place a tile next to the opponent after turn 1
        if (!firstTurn) {
            for (var t : neighbours) {
                if (t.getPlayer() != turn) {
                    throw new IllegalMove();
                }
            }
        }
    }

    /**
     * Switch who's players turn it is
     */
    private void switchPlayerTurn() {
        turn = getOpponent(turn);
    }

    private Player getOpponent(Player player) {
        return player == Player.WHITE ? Player.BLACK : Player.WHITE;
    }

    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        if (board.inPlayOf(turn, Tile.QUEEN_BEE) == 0) {
            throw new IllegalMove();
        }
        var from = new Coordinate(fromQ, fromR);
        var to = new Coordinate(toQ, toR);
        if (!Move.isValidMove(board, from, to)) {
            throw new IllegalMove();
        }
        board.moveTile(from, to);
        switchPlayerTurn();
    }

    @Override
    public void pass() throws IllegalMove {
        switchPlayerTurn();
    }

    @Override
    public boolean isWinner(Player player) {
        var opponent = getOpponent(player);
        return !isDraw() && queenSurrounded(opponent);
    }

    private boolean queenSurrounded(Player player) {
        var locations = board.getTileLocations(Tile.QUEEN_BEE, player);
        if (locations.isEmpty()) {
            return false;
        }
        var queen = locations.get(0);
        var tmp =  board.getNeighbours(queen);
        return tmp.size() == 6;
    }

    @Override
    public boolean isDraw() {
        return (queenSurrounded(Player.WHITE) && queenSurrounded(Player.BLACK));
    }
}
