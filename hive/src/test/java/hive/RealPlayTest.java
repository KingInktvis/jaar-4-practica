package hive;

import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Skyerzz-LAPOTOP on 05/11/2019.
 */
public class RealPlayTest {

    @Test
    void testPlayThroughOne() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();

        game.play(Hive.Tile.QUEEN_BEE, 0, 0);
        game.play(Hive.Tile.GRASSHOPPER, -1, 0);
        game.play(Hive.Tile.GRASSHOPPER, 1, 0);
        game.play(Hive.Tile.QUEEN_BEE, -2, 0);
        game.move(1, 0, -3, 0);
        game.play(Hive.Tile.SPIDER, -1, -1);

        game.play(Hive.Tile.SOLDIER_ANT, -3, -1);
        game.play(Hive.Tile.GRASSHOPPER, -2, 1);

        game.play(Hive.Tile.SOLDIER_ANT, 0, 1);
        game.move(-1, -1, 1, 0);

        game.play(Hive.Tile.SPIDER, -4, 0);
        game.play(Hive.Tile.GRASSHOPPER, 2, 0);

        game.play(Hive.Tile.GRASSHOPPER, -3, -2);
        game.move(-2, 1, 0, -1);

        game.move(-4, 0, -2, 1);
        game.move(0, -1, 0, 2);

        game.play(Hive.Tile.SOLDIER_ANT, -2, -3);
        game.play(Hive.Tile.BEETLE, 3, -1);

        game.move(-2, -3, -1, -1);
        game.play(Hive.Tile.SOLDIER_ANT, -1, 3);

        game.move(-3, -2, -3, 1);
        game.move(-1, 3, -4, -1);

        game.play(Hive.Tile.GRASSHOPPER, -2, 2);
        game.move(-4, -1, 1, -1);

        game.move(-2, 2, -2, -1);
        assertThrows(Hive.IllegalMove.class,() -> { game.move(3, -1, 2, 0); });
        assertThrows(Hive.IllegalMove.class,() -> { game.play(Hive.Tile.SOLDIER_ANT, 4, -2); });

        assertTrue(game.isGameOver());
        assertTrue(game.isWinner(Hive.Player.WHITE));
        assertFalse(game.isWinner(Hive.Player.BLACK));
        assertFalse(game.isDraw());

    }
}
