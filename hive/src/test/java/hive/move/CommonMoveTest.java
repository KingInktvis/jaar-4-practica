package hive.move;

import hive.Board;
import hive.Coordinate;
import hive.movement.MoveCommon;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Skyerzz-LAPOTOP on 30/10/2019.
 */
public class CommonMoveTest {

    @Test
    void getCoordinatesInRadiusZeroTest(){
        MoveCommon moveCommon = new MoveCommon();
        ArrayList<Coordinate> coords = moveCommon.getCoordinatesInRange(new Board(), new Coordinate(0, 0), 0, true);
        assertTrue(coords.isEmpty());
    }

    @Test
    void getCoordinatesInRadiusOneTest(){
        MoveCommon moveCommon = new MoveCommon();
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 1));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 0));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE, new Coordinate(1, -1));
        ArrayList<Coordinate> coords = moveCommon.getCoordinatesInRange(board, new Coordinate(0, 0), 1, true);
        assertTrue(coords.size()==6);
        assertTrue(coords.contains(new Coordinate(-1, 0)));
        assertTrue(coords.contains(new Coordinate(0, -1)));
        assertTrue(coords.contains(new Coordinate(1, -1)));
        assertTrue(coords.contains(new Coordinate(1, 0)));
        assertTrue(coords.contains(new Coordinate(0, 1)));
        assertTrue(coords.contains(new Coordinate(-1, +1)));
    }

    @Test
    void getCoordinatesInRadiusTwoTest(){
        MoveCommon moveCommon = new MoveCommon();
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 1));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 0));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE, new Coordinate(1, -1));
        ArrayList<Coordinate> coords = moveCommon.getCoordinatesInRange(board, new Coordinate(0, 0), 2, true);
        assertTrue(coords.size()==18);
        assertTrue(coords.contains(new Coordinate(-1, 0)));
        assertTrue(coords.contains(new Coordinate(0, -1)));
        assertTrue(coords.contains(new Coordinate(1, -1)));
        assertTrue(coords.contains(new Coordinate(1, 0)));
        assertTrue(coords.contains(new Coordinate(0, 1)));
        assertTrue(coords.contains(new Coordinate(-1, +1)));
        assertTrue(coords.contains(new Coordinate(-2, 0)));
        assertTrue(coords.contains(new Coordinate(-1, -1)));
        assertTrue(coords.contains(new Coordinate(0, -2)));
        assertTrue(coords.contains(new Coordinate(1, -2)));
        assertTrue(coords.contains(new Coordinate(2, -2)));
        assertTrue(coords.contains(new Coordinate(2, -1)));
        assertTrue(coords.contains(new Coordinate(2, 0)));
        assertTrue(coords.contains(new Coordinate(1, 1)));
        assertTrue(coords.contains(new Coordinate(0, 2)));
        assertTrue(coords.contains(new Coordinate(-1, 2)));
        assertTrue(coords.contains(new Coordinate(-2, 2)));
        assertTrue(coords.contains(new Coordinate(-2, 1)));
    }

    @Test
    void getCoordinatesInRangeTwoWithPlayedBoard(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, -1));
        //open positions -1, 1 - 0,1
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-2, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-2, 2));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 2));
        //open position -1,2
        //range 2 should give -1,1 - -1,2 - 0,1
        ArrayList<Coordinate> coords = MoveCommon.getCoordinatesInRange(board, new Coordinate(0, 0), 2, false);
        assertEquals(3, coords.size());
        assertTrue(coords.contains(new Coordinate(-1, 1)));
        assertTrue(coords.contains(new Coordinate(-1, 2)));
        assertTrue(coords.contains(new Coordinate(0, 1)));
    }

    @Test
    void getCoordinatesInRangeWithBlockage(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 1));
        //route to connections
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 2));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(2, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(2, -1));
        MoveCommon moveCommon = new MoveCommon();
        ArrayList<Coordinate> coords = moveCommon.getCoordinatesInRange(board, new Coordinate(0, 0), 1, false);
        assertTrue(coords.size()==2);
        assertTrue(coords.contains(new Coordinate(-1, 0)));
        assertTrue(coords.contains(new Coordinate(-1, 1)));
    }

    @Test
    void getCoordinatesWithDisconnectedMovement(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 1));

        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-2, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-2, 2));

        ArrayList<Coordinate> coords = MoveCommon.getCoordinatesInRange(board, new Coordinate(-1, 1), 1, false);
        assertEquals(2, coords.size());
        assertTrue(coords.contains(new Coordinate(0, 0)));
        assertTrue(coords.contains(new Coordinate(-1, 2)));
    }

    @Test
    void getCoordinatesInOneStepsMovementOptions(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 1));
        ArrayList<Coordinate> coords = MoveCommon.getCoordinatesInStepAmount(board, new Coordinate(0, 0), 1);

        assertEquals(2, coords.size());
        assertTrue(coords.contains(new Coordinate(1, 0)));
        assertTrue(coords.contains(new Coordinate(-1, 1)));

    }

    @Test
    void getCoordinatesInThreeStepsMovementOptions(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 1));
        ArrayList<Coordinate> coords = MoveCommon.getCoordinatesInStepAmount(board, new Coordinate(0, 0), 3);

        assertEquals(1, coords.size());
        assertTrue(coords.contains(new Coordinate(0, 2)));

    }

    @Test
    void breakChainFully(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, -1));

        assertTrue(MoveCommon.breaksChain(board, new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(0, 0)));
    }

    @Test
    void testChainWithoutBreakingWhileMoving(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 1));

        assertFalse(MoveCommon.breaksChain(board, new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(0, 0)));
    }
}
