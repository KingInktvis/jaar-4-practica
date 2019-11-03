package hive.move;

import hive.Board;
import hive.Coordinate;
import hive.movement.MoveAnt;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AntMoveTest {

    @Test
    void AntMoveOnOneSquare(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.SOLDIER_ANT, new Coordinate(0, 1));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(1,0)));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(1,-1)));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(0,-1)));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(-1,0)));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(-1,1)));
        assertFalse(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(0,0)));
        assertFalse(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(0,1)));
    }

    @Test
    void AntMoveOnTwoSquare(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.SOLDIER_ANT, new Coordinate(0, 1));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(1,0)));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(1,-1)));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(-1,0)));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(-1,1)));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(-1,-1)));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(0,-2)));
        assertTrue(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(1,-2)));
        assertFalse(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(0,-1)));
        assertFalse(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(0,0)));
        assertFalse(MoveAnt.canAntMove(board, new Coordinate(0, 1), new Coordinate(0,1)));
    }
}
