package hive;

import nl.hanze.hive.Hive;

public class BoardTile {
    private Hive.Tile type;
    private Hive.Player player;

    BoardTile(Hive.Tile type, Hive.Player player) {
        this.type = type;
        this.player = player;
    }

    public Hive.Tile getType() {
        return type;
    }

    public Hive.Player getPlayer() {
        return player;
    }
}
