package hive;

import nl.hanze.hive.Hive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Skyerzz-LAPOTOP on 08/10/2019.
 */
public class HiveBoard {

    //todo would this better be stored in a hashmap with keys = coords?
    private ArrayList<BoardTile> boardTiles = new ArrayList<>();

    private static HiveBoard instance = null;

    private HiveBoard(){}

    public static HiveBoard getInstance(){
        if(instance==null){
            instance = new HiveBoard();
        }
        return instance;
    }

    public BoardTile getTile(int xCoord, int yCoord, Hive.Tile type){
        for(BoardTile tile: boardTiles){
            if(tile.getxCoord()==xCoord && tile.getyCoord()==yCoord && (type==null||type==tile.getTileType())){
                return tile;
            }
        }
        return null;
    }

    public BoardTile getTile(int x, int y){
        return getTile(x, y, null);
    }

    public void placeTile(BoardTile tile){
        boardTiles.add(tile);
    }

    public List<BoardTile> getNeighbours(int x, int y){
        ArrayList<BoardTile> list = new ArrayList<>();
        BoardTile t = getTile(x, y+1);
        if(t!=null){
            list.add(t);
        }
        t = getTile(x, y-1);
        if(t!=null){
            list.add(t);
        }
        t = getTile(x-1, y);
        if(t!=null){
            list.add(t);
        }
        t = getTile(x+1, y);
        if(t!=null){
            list.add(t);
        }
        t = getTile(x-1, y-1);
        if(t!=null){
            list.add(t);
        }
        t = getTile(x+1, y+1);
        if(t!=null){
            list.add(t);
        }
        return list;
    }
}
