package hive;

import nl.hanze.hive.Hive;

import java.util.HashMap;

/**
 * Created by Skyerzz-LAPOTOP on 08/10/2019.
 */
public class HivePlayer {

    private Hive.Player playerColor;
    private HashMap<Hive.Tile, Integer> availableTiles = new HashMap<>();

    public HivePlayer(Hive.Player color){
        playerColor = color;
        instantiateTiles();
    }

    private void instantiateTiles(){
        availableTiles.put(Hive.Tile.QUEEN_BEE, 1);
        availableTiles.put(Hive.Tile.BEETLE, 2);
        availableTiles.put(Hive.Tile.SPIDER, 2);
        availableTiles.put(Hive.Tile.SOLDIER_ANT, 3);
        availableTiles.put(Hive.Tile.GRASSHOPPER, 3);
    }

    public Hive.Player getPlayerColor(){
        return playerColor;
    }
}
