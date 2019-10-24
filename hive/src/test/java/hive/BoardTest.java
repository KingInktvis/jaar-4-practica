package hive;

import nl.hanze.hive.Hive;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {
    @Test
    void placeTileOnEmptyBoard() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(1, 1));
        var tile = board.getTile(new Coordinate(1, 1));
        assertEquals(Tile.QUEEN_BEE, tile.getType());
        assertEquals(Player.WHITE, tile.getPlayer());
    }

    @Test
    void moveNonExistentTile() {
        var board = new Board();
        assertThrows(Hive.IllegalMove.class, () -> {
            board.moveTile(new Coordinate(1, 1), new Coordinate(1, 2));
        });
    }

    @Test
    void inPlayOnEmptyBoard() {
        var board = new Board();
        assertEquals(0, board.inPlayOf(Player.WHITE, Tile.BEETLE));
    }

    @Test
    void inPlayTileBelongingToOnePlayer() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.BEETLE, new Coordinate(1, 1));
        assertEquals(1, board.inPlayOf(Player.WHITE, Tile.BEETLE));
        assertEquals(0, board.inPlayOf(Player.BLACK, Tile.BEETLE));
    }

    @Test
    void inPlayWithStackedTiles() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.BEETLE, new Coordinate(1, 1));
        board.placeTile(Player.BLACK, Tile.BEETLE, new Coordinate(1, 1));
        assertEquals(1, board.inPlayOf(Player.WHITE, Tile.BEETLE));
        assertEquals(1, board.inPlayOf(Player.BLACK, Tile.BEETLE));
    }

    @Test
    void testNeighbours() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.BEETLE, new Coordinate(0, 0));
        board.placeTile(Player.BLACK, Tile.BEETLE, new Coordinate(-1, 1));
        board.placeTile(Player.BLACK, Tile.BEETLE, new Coordinate(-1, 1));
        board.placeTile(Player.BLACK, Tile.BEETLE, new Coordinate(1, -1));
        board.placeTile(Player.BLACK, Tile.BEETLE, new Coordinate(-4, 0));
        assertEquals(2, board.getNeighbours(new Coordinate(0,0)).size());
    }
    @Test
    void testNeighboursWhenFullySurrounded() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.BEETLE, new Coordinate(0, 0));
        board.placeTile(Player.WHITE, Tile.BEETLE, new Coordinate(-1, 0));
        board.placeTile(Player.WHITE, Tile.BEETLE, new Coordinate(0, -1));
        board.placeTile(Player.WHITE, Tile.BEETLE, new Coordinate(1, -1));
        board.placeTile(Player.WHITE, Tile.BEETLE, new Coordinate(1, 0));
        board.placeTile(Player.WHITE, Tile.BEETLE, new Coordinate(0, 1));
        board.placeTile(Player.WHITE, Tile.BEETLE, new Coordinate(-1, 1));
        assertEquals(6, board.getNeighbours(new Coordinate(0, 0)).size());
    }
}