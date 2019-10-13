package hive;

import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HiveGameTest {

    @Test
    void play() {
        var game = new HiveGame();
        try {
            game.play(Hive.Tile.BEETLE, 4, 5);
        } catch (Hive.IllegalMove illegalMove) {
            fail();
        }
    }

    @Test
    void moveOnEmptyBoard() {
        var game = new HiveGame();
        try {
            game.move(5,5, 7,8);
            fail();
        } catch (Hive.IllegalMove ignored) {

        }
    }

    @Test
    void passOnFirstTurn() {
        var game = new HiveGame();
        try {
            game.pass();
            fail();
        } catch (Hive.IllegalMove ignored) {

        }
    }

    @Test
    void isWinner() {
        var game = new HiveGame();
        assertFalse(game.isWinner(Hive.Player.BLACK));
        assertFalse(game.isWinner(Hive.Player.WHITE));
    }

    @Test
    void isDraw() {
        var game = new HiveGame();
        assertFalse(game.isDraw());
        assertFalse(game.isDraw());
    }
}