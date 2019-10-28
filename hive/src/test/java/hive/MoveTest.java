package hive;

import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;
import org.junit.jupiter.api.Test;

import static hive.Move.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MoveTest {
    @Test
    void afterMoveHasToBeAdjacentNextToAnotherTile() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Player.BLACK, Tile.QUEEN_BEE, new Coordinate(1, 0));
        assertFalse(checkToAdjacentTileWithOneStep(board, new Coordinate(2, 0)));
    }

    @Test
    void areAllTilesConnected() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Player.BLACK, Tile.QUEEN_BEE, new Coordinate(1, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(2, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(4, 0));
        assertFalse(allTilesAreConnected(board));
    }

    @Test
    void tilesStayConnectedDuringMovement() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Player.BLACK, Tile.QUEEN_BEE, new Coordinate(1, -1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-1, 0));
        board.placeTile(Player.BLACK, Tile.BEETLE, new Coordinate(-1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(0, 2));
        assertFalse(stayConnectedOneStep(board, new Coordinate(-1, 1), new Coordinate(0, 1)));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-1, 2));
        assertTrue(stayConnectedOneStep(board, new Coordinate(-1, 1), new Coordinate(0, 1)));
    }

    @Test
    void testGapForMovement() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Player.BLACK, Tile.QUEEN_BEE, new Coordinate(1, -1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-1, 0));
        board.placeTile(Player.BLACK, Tile.BEETLE, new Coordinate(-1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(0, 2));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-1, 2));
        assertTrue(gapForMovement(board, new Coordinate(-1, 1), new Coordinate(0, 1)));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(0, 0));
        assertFalse(gapForMovement(board, new Coordinate(-1, 1), new Coordinate(0, 1)));
    }

    @Test
    void noMoveSpace() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, -1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(0, 1));
        assertFalse(gapForMovement(board, new Coordinate(0, 0), new Coordinate(1, 0)));

    }

    @Test
    void testAntMovement() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Player.BLACK, Tile.QUEEN_BEE, new Coordinate(1, -1));
        board.placeTile(Player.BLACK, Tile.SOLDIER_ANT, new Coordinate(-1, 0));
        board.placeTile(Player.BLACK, Tile.BEETLE, new Coordinate(-1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(0, 2));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-1, 2));
        board.placeTile(Player.BLACK, Tile.SOLDIER_ANT, new Coordinate(0, 0));
        assertFalse(isValidMove(board, new Coordinate(0, 0), new Coordinate(2, 0)));
        assertTrue(isValidMove(board, new Coordinate(-1, 0), new Coordinate(2, 0)));
    }
}