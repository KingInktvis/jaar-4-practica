package hive.move;

import hive.Board;
import hive.Coordinate;
import hive.movement.MoveBeetle;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BeetleMovementTest {
    
    @Test
    void beetleMoveEmptySurroundings(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.BEETLE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, 0));
        assertTrue(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(1,-1)));
        assertTrue(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(0,1)));
        //its not allowed on top if it doesnt connect to another tile (which there are none of right now)
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(1,0)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(0,-1)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(-1,0)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(-1,1)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(0,0)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(2,0)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(0,-2)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(1,-2)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(-1,2)));
    }

    @Test
    void beetleMoveFullSurroundings(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.BEETLE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 1));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 0));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE, new Coordinate(1, -1));
        assertTrue(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(1,0)));
        assertTrue(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(1,-1)));
        assertTrue(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(0,-1)));
        assertTrue(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(-1,0)));
        assertTrue(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(-1,1)));
        assertTrue(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(0,1)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(0,0)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(2,0)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(0,-2)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(1,-2)));
        assertFalse(MoveBeetle.canBeetleMove(board, new Coordinate(0, 0), new Coordinate(-1,2)));
    }
}
