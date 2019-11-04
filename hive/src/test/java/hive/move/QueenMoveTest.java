package hive.move;

import hive.Board;
import hive.Coordinate;
import hive.Move;
import hive.movement.MoveBeetle;
import hive.movement.MoveQueen;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueenMoveTest {

    @Test
    void QueenMoveEmptySurroundings(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.SOLDIER_ANT, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, 0));
        assertTrue(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(0,1)));
        assertTrue(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(1,-1)));

        assertFalse(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(-1,1)));
        assertFalse(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(0,-1)));
        assertFalse(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(1,0)));
        assertFalse(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(0,0)));
        assertFalse(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(2,0)));
        assertFalse(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(0,-2)));
        assertFalse(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(1,-2)));
        assertFalse(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(-1,2)));
    }

    @Test
    void QueenMoveBlockedOption(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.BEETLE, new Coordinate(1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.BEETLE, new Coordinate(0, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.BEETLE, new Coordinate(-1, 0));

        //to keep connection
        board.placeTile(Hive.Player.BLACK, Hive.Tile.BEETLE, new Coordinate(-1, 2));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.BEETLE, new Coordinate(-2, 2));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.BEETLE, new Coordinate(-2, 1));

        assertTrue(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(0,-1)));
        assertTrue(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(1,-1)));

        assertFalse(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(0, 0)));
        assertFalse(MoveQueen.canQueenMove(board, new Coordinate(0, 0), new Coordinate(-1,1)));

    }
}
