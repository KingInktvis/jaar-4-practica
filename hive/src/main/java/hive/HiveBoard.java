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
        ArrayList<BoardTile> t;
        //left
        t = getTile(x-1, y);
        if(t!=null){
            list.addAll(t);
        }
        //right
        t = getTile(x+1, y);
        if(t!=null){
            list.addAll(t);
        }
        //bottom right
        t = getTile(x, y-1);
        if(t!=null){
            list.addAll(t);
        }
        //bottom left
        t = getTile(x-1, y-1);
        if(t!=null){
            list.addAll(t);
        }
        //top right
        t = getTile(x+1, y+1);
        if(t!=null){
            list.addAll(t);
        }
        //top left
        t = getTile(x, y+1);
        if(t!=null){
            list.addAll(t);
        }
        return list;
    }

    public ArrayList<BoardTile> getBoardTiles(){
        return boardTiles;
    }

    public BoardTile getQueenByPlayer(Hive.Player color){
        for(BoardTile tile: boardTiles){
            if(tile.getTileType()== Hive.Tile.QUEEN_BEE && tile.getColor()==color){
                return tile;
            }
        }
        return null;
    }

    public void moveTile(int x, int y, BoardTile tile) throws Hive.IllegalMove {
        List<BoardTile> tilesAtXY = getTile(x, y);
        if(tilesAtXY.isEmpty()){
            tile.setCoords(x, y);
            return;
        }else {
            if (tile.getTileType() == Hive.Tile.BEETLE) {
                int highestZindex = 0;
                for(BoardTile tileAtPosition: tilesAtXY){
                    highestZindex = Math.max(tileAtPosition.getzCoord(), highestZindex);
                }
                tile.setCoords(x, y);
                tile.setZCoord(highestZindex+1);
            } else {
                throw new Hive.IllegalMove("This tile is already occupied!");
            }
        }
    }

    public boolean isTopTile(BoardTile tile){
        for(BoardTile t: boardTiles){
            if(t.getxCoord()==tile.getxCoord()&&t.getyCoord()==tile.getyCoord()){
                if(tile.getzCoord() < t.getzCoord()){
                    return false;
                }
            }
        }
        return true;
    }
}
