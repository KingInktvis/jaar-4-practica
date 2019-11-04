package hive;

import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTileTest {

    @Test
    void getType() {
        BoardTile tile = new BoardTile(Hive.Tile.BEETLE, Hive.Player.WHITE);
        assertEquals(Hive.Tile.BEETLE, tile.getType());
    }

    @Test
    void getPlayer() {
        BoardTile tile = new BoardTile(Hive.Tile.BEETLE, Hive.Player.WHITE);
        assertEquals(Hive.Player.WHITE, tile.getPlayer());
    }
}