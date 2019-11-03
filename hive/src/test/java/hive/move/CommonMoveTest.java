package hive.move;

import hive.Board;
import hive.Coordinate;
import hive.movement.MoveCommon;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        MoveCommon moveCommon = new MoveCommon();
        ArrayList<Coordinate> coords = moveCommon.getCoordinatesInRange(board, new Coordinate(0, 0), 2, false);
        assertTrue(coords.size()==3);
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

        MoveCommon moveCommon = new MoveCommon();
        ArrayList<Coordinate> coords = moveCommon.getCoordinatesInRange(board, new Coordinate(-1, 1), 1, false);
        assertTrue(coords.size()==2);
        assertTrue(coords.contains(new Coordinate(0, 0)));
        assertTrue(coords.contains(new Coordinate(-1, 2)));
    }
}
