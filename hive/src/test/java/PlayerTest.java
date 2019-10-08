import hive.HiveGame;
import hive.HivePlayer;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Skyerzz-LAPOTOP on 08/10/2019.
 */
public class PlayerTest {

    @Test
    void testPlayerOpponent() {
        HiveGame game = new HiveGame();
        assertEquals(game.getOpponent(Hive.Player.BLACK), Hive.Player.WHITE);
        assertEquals(game.getOpponent(Hive.Player.WHITE), Hive.Player.BLACK);
    }
}
