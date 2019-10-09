package hive;

import nl.hanze.hive.Hive;

/**
 * Created by Skyerzz-LAPOTOP on 09/10/2019.
 */
public class BoardTile {

    private int xCoord, yCoord, zCoord;
    private Hive.Tile tileType;
    private Hive.Player tileColor;

    public BoardTile(int xCoord, int yCoord, Hive.Tile tileType, Hive.Player tileColor){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = 0;
        this.tileType = tileType;
        this.tileColor = tileColor;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setCoords(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public void setZCoord(int zCoord){
        this.zCoord = zCoord;
    }

    public int getzCoord(){
        return zCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public Hive.Tile getTileType() {
        return tileType;
    }

    public Hive.Player getColor(){
        return tileColor;
    }
}
