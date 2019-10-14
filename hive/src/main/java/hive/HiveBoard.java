package hive;

import nl.hanze.hive.Hive;

import java.util.ArrayList;
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

    public ArrayList<BoardTile> getTiles(int xCoord, int yCoord, Hive.Tile type){
        ArrayList<BoardTile> tiles = new ArrayList<>();
        for(BoardTile tile: boardTiles){
            if(tile.getxCoord()==xCoord && tile.getyCoord()==yCoord && (type==null||type==tile.getTileType())){
                tiles.add(tile);
            }
        }
        return tiles.isEmpty() ? null : tiles;
    }

    public ArrayList<BoardTile> getTile(int x, int y){
        return getTiles(x, y, null);
    }

    public void placeTile(BoardTile tile){
        boardTiles.add(tile);
    }

    public List<BoardTile> getNeighbours(int x, int y){
        ArrayList<BoardTile> list = new ArrayList<>();
        ArrayList<BoardTile> t = getTile(x, y+1);
        if(t!=null){
            list.addAll(t);
        }
        t = getTile(x, y-1);
        if(t!=null){
            list.addAll(t);
        }
        t = getTile(x-1, y);
        if(t!=null){
            list.addAll(t);
        }
        t = getTile(x+1, y);
        if(t!=null){
            list.addAll(t);
        }
        t = getTile(x-1, y-1);
        if(t!=null){
            list.addAll(t);
        }
        t = getTile(x+1, y+1);
        if(t!=null){
            list.addAll(t);
        }
        return list;
    }

    public ArrayList<BoardTile> getBoardTiles(){
        return boardTiles;
    }
}
