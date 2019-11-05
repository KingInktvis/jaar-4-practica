package hive;

import hive.movement.*;
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
        if(isGameOver()){
            throw new IllegalMove("The game is already over!");
        }
        Coordinate coordinate = new Coordinate(q, r);
        ArrayList<BoardTile> neighbours = board.getNeighbours(coordinate);
        // Throws an exception if there is already a tile, or are no tiles next to the location except when it is the first turn
        if (board.getTile(coordinate) != null || ((!firstTurn || turn == Player.BLACK) && neighbours.size() == 0)) {
            throw new IllegalMove();
        }
        // Throw exception if all of these kind of tiles are already in play
        if (board.inPlayOf(turn, tile) >= tileLimit.get(tile)) {
            throw new IllegalMove();
        }
        connectedToTile(neighbours); //throws exception if its connected to an opponents tile
        // Throw exception if there already 3 or more of the players tiles placed on the board and the queen is not
        isQueenPlayedCheck(tile);
        board.placeTile(turn, tile, coordinate);
        // Change the value of first turn after the first turn
        if (firstTurn && turn == Player.BLACK) {
            firstTurn = false;
        }
        switchPlayerTurn();
    }

    private void isQueenPlayedCheck(Tile tile) throws IllegalMove {
        if (tile != Tile.QUEEN_BEE && board.inPlayOf(turn, Tile.QUEEN_BEE) == 0) {
            int count = 0;
            for (Tile t : Tile.values()) {
                count += board.inPlayOf(turn, t);
            }
            if (count >= 3) {
                throw new IllegalMove();
            }
        }
    }

    private void connectedToTile(ArrayList<BoardTile> neighbours) throws IllegalMove {
        // Can not place a tile next to the opponent after turn 1
        if (!firstTurn) {
            for (BoardTile t : neighbours) {
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
        if(isGameOver()){
            throw new IllegalMove("The game is already over!");
        }
        if (board.inPlayOf(turn, Tile.QUEEN_BEE) == 0) {
            throw new IllegalMove();
        }
        Coordinate from = new Coordinate(fromQ, fromR);
        Coordinate to = new Coordinate(toQ, toR);
        if (!MoveCommon.isValidMove(board, from, to)) {
            throw new IllegalMove();
        }
        board.moveTile(from, to);
        switchPlayerTurn();
    }

    public boolean isGameOver(){
        return isWinner(Player.BLACK) || isWinner(Player.WHITE) || isDraw();
    }

    @Override
    public void pass() throws IllegalMove {
        if(isGameOver()){
            throw new IllegalMove("Game already ended!");
        }
        for(Tile t: Tile.values()){
            if(canMakeAMove(turn, t)){
                throw new IllegalMove("Player can still make a move! (" + t.name() + ")");
            }
        }
        switchPlayerTurn();
    }

    public boolean canMakeAMove(Player player, Tile tileType){
        ArrayList<Coordinate> tilesInPlay = board.getTileLocations(tileType, player);
        for(Coordinate coord: tilesInPlay){
            switch(tileType){
                case QUEEN_BEE:
                    if(MoveQueen.doesQueenHaveMoveOptions(board, coord)){
                        return true;
                    }
                    break;
                case SPIDER:
                    if(MoveSpider.doesSpiderHaveMoveOptions(board, coord)){
                        return true;
                    }
                    break;
                case GRASSHOPPER:
                    if(MoveGrassHopper.doesGrassHopperHaveMoveOptions(board, coord)){
                        return true;
                    }
                    break;
                case BEETLE:
                    if(MoveBeetle.doesBeetleHaveMoveOptions(board, coord)){
                        return true;
                    }
                    break;
                case SOLDIER_ANT:
                    if(MoveAnt.doesAntHaveMoveOptions(board, coord)){
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean isWinner(Player player) {
        Player opponent = getOpponent(player);
        return !isDraw() && queenSurrounded(opponent);
    }

    private boolean queenSurrounded(Player player) {
        ArrayList<Coordinate> locations = board.getTileLocations(Tile.QUEEN_BEE, player);
        if (locations.isEmpty()) {
            return false;
        }
        Coordinate queen = locations.get(0);
        ArrayList<BoardTile> tmp = board.getNeighbours(queen);
        return tmp.size() == 6;
    }

    @Override
    public boolean isDraw() {
        return (queenSurrounded(Player.WHITE) && queenSurrounded(Player.BLACK));
    }
}
