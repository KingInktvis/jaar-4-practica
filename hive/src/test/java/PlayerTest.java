import hive.HiveGame;
import hive.HivePlayer;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testTileRemovalFromPlayer(){
        HivePlayer player = new HivePlayer(Hive.Player.BLACK);
        player.removeTile(Hive.Tile.SOLDIER_ANT);
        player.removeTile(Hive.Tile.SOLDIER_ANT);
        player.removeTile(Hive.Tile.SPIDER);
        player.removeTile(Hive.Tile.QUEEN_BEE);
        assertTrue(player.hasTile(Hive.Tile.SPIDER));
        assertTrue(player.hasTile(Hive.Tile.SOLDIER_ANT));
        assertFalse(player.hasTile(Hive.Tile.QUEEN_BEE));
        player.removeTile(Hive.Tile.SOLDIER_ANT);
        player.removeTile(Hive.Tile.SPIDER);
        assertFalse(player.hasTile(Hive.Tile.SOLDIER_ANT));
        assertFalse(player.hasTile(Hive.Tile.SPIDER));
    }
}
