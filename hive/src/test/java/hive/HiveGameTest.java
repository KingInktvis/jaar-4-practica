package hive;

import nl.hanze.hive.Hive.IllegalMove;
import nl.hanze.hive.Hive.Player;
import nl.hanze.hive.Hive.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HiveGameTest {

    @Test
    void playFirstTile() throws IllegalMove {
        var game = new HiveGame();
        game.play(Tile.BEETLE, 1, 1);
    }

    @Test
    void playTilesNextToAnother() throws IllegalMove {
        var game = new HiveGame();
        game.play(Tile.BEETLE, 1, 1);
        assertThrows(IllegalMove.class, () -> {
            game.play(Tile.BEETLE, 10, 1);
        });
    }

    @Test
    void playTilesCanNotBeNextToTheOtherPlayerAfterFirstTurn() throws IllegalMove {
        var game = new HiveGame();
        game.play(Tile.BEETLE, 0, 0);
        game.play(Tile.BEETLE, 1, 0);
        game.play(Tile.BEETLE, -1, 0);
        assertThrows(IllegalMove.class, () -> {
            game.play(Tile.BEETLE, 0, 1);
        });
    }

    @Test
    void playTileOnTopOfAnother() throws IllegalMove {
        var game = new HiveGame();
        game.play(Tile.BEETLE, 1, 1);
        assertThrows(IllegalMove.class, () -> {
            game.play(Tile.BEETLE, 1, 1);
        });
    }

    @Test
    void isWinnerOnGameStart() {
        var game = new HiveGame();
        assertFalse(game.isWinner(Player.WHITE));
        assertFalse(game.isWinner(Player.BLACK));
    }

    @Test
    void isWinner() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(-1, 0));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(0, -1));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(1, -1));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(1, 0));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(0, 1));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(-1, 1));
        var game = new HiveGame(board);
        assertFalse(game.isWinner(Player.WHITE));
        assertTrue(game.isWinner(Player.BLACK));
    }

    @Test
    void isDrawOnGameStart() {
        var game = new HiveGame();
        assertFalse(game.isDraw());
    }

    @Test
    void isDraw() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Player.BLACK, Tile.QUEEN_BEE, new Coordinate(-1, 0));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(0, -1));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(1, -1));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(1, 0));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(0, 1));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(-1, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-2, 1));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-2, 0));
        board.placeTile(Player.BLACK, Tile.SPIDER, new Coordinate(-1, -1));

        var game = new HiveGame(board);
        assertFalse(game.isWinner(Player.WHITE));
        assertFalse(game.isWinner(Player.BLACK));
        assertTrue(game.isDraw());
    }

    @Test
    void playOnlyOneQueen() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.QUEEN_BEE, new Coordinate(0, 0));
        var game = new HiveGame(board);
        assertThrows(IllegalMove.class, () -> {
            game.play(Tile.QUEEN_BEE, 0, 1);
        });
    }

    @Test
    void playOnlyTwoSpiders() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(0, 0));
        board.placeTile(Player.WHITE, Tile.SPIDER, new Coordinate(1, 0));
        var game = new HiveGame(board);
        assertThrows(IllegalMove.class, () -> {
            game.play(Tile.SPIDER, 0, 1);
        });
    }

    @Test
    void moveTile() throws IllegalMove {
        var game = new HiveGame();
        game.play(Tile.QUEEN_BEE, 0,0);
        game.play(Tile.QUEEN_BEE, 1,0);
        game.move(0,0, 0, 1);
    }

    @Test
    void queenMustBePlayedWhenThreeTilesAreAlreadyPlayed() {
        var board = new Board();
        board.placeTile(Player.WHITE, Tile.GRASSHOPPER, new Coordinate(0, 0));
        board.placeTile(Player.WHITE, Tile.GRASSHOPPER, new Coordinate(0, 0));
        board.placeTile(Player.WHITE, Tile.GRASSHOPPER, new Coordinate(0, 0));
        var game = new HiveGame(board);
        assertThrows(IllegalMove.class, () -> {
            game.play(Tile.SPIDER, 0, 1);
        });
    }

    @Test
    void canNotMoveBeforePlayingQueen() throws IllegalMove {
        var game = new HiveGame();
        game.play(Tile.SOLDIER_ANT, 0, 0);
        game.play(Tile.QUEEN_BEE, 0,1);
        assertThrows(IllegalMove.class, () -> {
            game.move(0, 0, 1, 0);
        });
    }

}