package hive.move;

import hive.Board;
import hive.Coordinate;
import hive.movement.MoveGrassHopper;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Skyerzz-LAPOTOP on 04/11/2019.
 */
public class GrassHopperTest {

    @Test
    void testGrassHopperOneJump(){
        Board board = new Board();

        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(-1, 0));

        assertTrue(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(-1, 0), new Coordinate(1, 0)));
        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(-1, 0), new Coordinate(0, 0)));
    }

    @Test
    void testGrassHopperJumpLockedIn(){
        //if grashopper moves it breaks the chain entirely
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 1));

        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(-2, 0)));
        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(2, -2)));
        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(0, 2)));

    }


    @Test
    void testGrassHopperJumpAllDirections(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 1));

        assertTrue(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(-2, 0)));
        assertTrue(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(0, -2)));
        assertTrue(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(2, -2)));
        assertTrue(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(2, 0)));
        assertTrue(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(0, 2)));
        assertTrue(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(-2, 2)));

        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(-1, -1)));
        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(1, -2)));
        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(2, -1)));
        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(1, 1)));
        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(-1, 2)));
        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(-2, 1)));

        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(-3, 0)));
        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(3, 0)));
        assertFalse(MoveGrassHopper.canGrassHopperMove(board, new Coordinate(0, 0), new Coordinate(0, 0)));

    }

    @Test
    void getNextEmptyPositiveHorizontalCoordinate(){
        Board board = new Board();

        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(-1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(-2, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(-3, 0));

        assertNull(MoveGrassHopper.getHorizontalOption(board, new Coordinate(0, 0), true));
        assertEquals(MoveGrassHopper.getHorizontalOption(board, new Coordinate(-1, 0), true), new Coordinate(1, 0));
        assertEquals(MoveGrassHopper.getHorizontalOption(board, new Coordinate(-2, 0), true), new Coordinate(1, 0));
        assertEquals(MoveGrassHopper.getHorizontalOption(board, new Coordinate(-3, 0), true), new Coordinate(1, 0));

    }

    @Test
    void getNextEmptyNegativeHorizontalCoordinate(){
        Board board = new Board();

        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(2, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(3, 0));

        assertNull(MoveGrassHopper.getHorizontalOption(board, new Coordinate(0, 0), false));
        assertEquals(MoveGrassHopper.getHorizontalOption(board, new Coordinate(1, 0), false), new Coordinate(-1, 0));
        assertEquals(MoveGrassHopper.getHorizontalOption(board, new Coordinate(2, 0), false), new Coordinate(-1, 0));
        assertEquals(MoveGrassHopper.getHorizontalOption(board, new Coordinate(3, 0), false), new Coordinate(-1, 0));

    }


    @Test
    void getNextEmptyNegativeUpperLeftToBottomRightCoordinate(){
        Board board = new Board();

        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(0, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(0, 2));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(0, 3));

        assertNull(MoveGrassHopper.getUpperLeftToBottomRightOption(board, new Coordinate(0, 0), false));
        assertEquals(MoveGrassHopper.getUpperLeftToBottomRightOption(board, new Coordinate(0, 1), false), new Coordinate(0, -1));
        assertEquals(MoveGrassHopper.getUpperLeftToBottomRightOption(board, new Coordinate(0, 2), false), new Coordinate(0, -1));
        assertEquals(MoveGrassHopper.getUpperLeftToBottomRightOption(board, new Coordinate(0, 3), false), new Coordinate(0, -1));

    }

    @Test
    void getNextEmptyPositiveUpperLeftToBottomRightCoordinate(){
        Board board = new Board();

        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(0, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(0, 2));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(0, 3));

        assertEquals(MoveGrassHopper.getUpperLeftToBottomRightOption(board, new Coordinate(0, 0), true), new Coordinate(0, 4));
        assertEquals(MoveGrassHopper.getUpperLeftToBottomRightOption(board, new Coordinate(0, 1), true), new Coordinate(0, 4));
        assertEquals(MoveGrassHopper.getUpperLeftToBottomRightOption(board, new Coordinate(0, 2), true), new Coordinate(0, 4));
        assertNull(MoveGrassHopper.getUpperLeftToBottomRightOption(board, new Coordinate(0, 3), true));

    }

    @Test
    void getNextEmptyNegativeUpperRightToBottomLeftCoordinate(){
        Board board = new Board();

        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(1, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(2, -2));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(3, -3));

        assertNull(MoveGrassHopper.getUpperRightToBottomLeftOption(board, new Coordinate(0, 0), false));
        assertEquals(MoveGrassHopper.getUpperRightToBottomLeftOption(board, new Coordinate(1, -1), false), new Coordinate(-1, 1));
        assertEquals(MoveGrassHopper.getUpperRightToBottomLeftOption(board, new Coordinate(2, -2), false), new Coordinate(-1, 1));
        assertEquals(MoveGrassHopper.getUpperRightToBottomLeftOption(board, new Coordinate(3, -3), false), new Coordinate(-1, 1));

    }

    @Test
    void getNextEmptyPositiveUpperRightToBottomLeftCoordinate(){
        Board board = new Board();

        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(1, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(2, -2));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.GRASSHOPPER, new Coordinate(3, -3));

        assertEquals(MoveGrassHopper.getUpperRightToBottomLeftOption(board, new Coordinate(0, 0), true), new Coordinate(4, -4));
        assertEquals(MoveGrassHopper.getUpperRightToBottomLeftOption(board, new Coordinate(1, -1), true), new Coordinate(4, -4));
        assertEquals(MoveGrassHopper.getUpperRightToBottomLeftOption(board, new Coordinate(2, -2), true), new Coordinate(4, -4));
        assertNull(MoveGrassHopper.getUpperRightToBottomLeftOption(board, new Coordinate(3, -3), true));

    }
}
