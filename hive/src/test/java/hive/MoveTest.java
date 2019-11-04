package hive;

import hive.movement.MoveCommon;
import nl.hanze.hive.Hive;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoveTest {
    @Test
    void afterMoveHasToBeAdjacentNextToAnotherTile() {
        Board board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Player.BLACK, Tile.QUEEN_BEE, new Coordinate(1, 0));
        assertFalse(MoveCommon.checkAdjacentToTileWithOneStep(board, new Coordinate(2, 0)));
    }

    @Test
    void areAllTilesConnected() {
        Board board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Player.BLACK, Tile.QUEEN_BEE, new Coordinate(1, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(2, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(4, 0));
        assertFalse(MoveCommon.allTilesAreConnected(board));
    }

    @Test
    void tilesStayConnectedDuringMovement() {
        Board board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Player.BLACK, Tile.QUEEN_BEE, new Coordinate(1, -1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-1, 0));
        board.placeTile(Player.BLACK, Tile.BEETLE, new Coordinate(-1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(0, 2));
        assertFalse(MoveCommon.stayConnectedOneStep(board, new Coordinate(-1, 1), new Coordinate(0, 1)));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-1, 2));
        assertTrue(MoveCommon.stayConnectedOneStep(board, new Coordinate(-1, 1), new Coordinate(0, 1)));
    }

    @Test
    void testGapForMovement() {
        Board board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Player.BLACK, Tile.QUEEN_BEE, new Coordinate(1, -1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-1, 0));
        board.placeTile(Player.BLACK, Tile.BEETLE, new Coordinate(-1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(0, 2));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-1, 2));
        assertTrue(MoveCommon.gapForMovement(board, new Coordinate(-1, 1), new Coordinate(0, 1)));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(0, 0));
        assertFalse(MoveCommon.gapForMovement(board, new Coordinate(-1, 1), new Coordinate(0, 1)));
    }

    @Test
    void noMoveSpace() {
        Board board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, -1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(0, 1));
        assertFalse(MoveCommon.gapForMovement(board, new Coordinate(0, 0), new Coordinate(1, 0)));

    }

    @Test
    void testAntMovement() throws Hive.IllegalMove {
        Board board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Player.BLACK, Tile.QUEEN_BEE, new Coordinate(1, -1));
        board.placeTile(Player.BLACK, Tile.SOLDIER_ANT, new Coordinate(-1, 0));
        board.placeTile(Player.BLACK, Tile.BEETLE, new Coordinate(-1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(0, 2));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-1, 2));
        board.placeTile(Player.BLACK, Tile.SOLDIER_ANT, new Coordinate(0, 0));
        assertFalse(MoveCommon.isValidMove(board, new Coordinate(0, 0), new Coordinate(2, 0)));
        assertTrue(MoveCommon.isValidMove(board, new Coordinate(-1, 0), new Coordinate(2, 0)));
    }
}