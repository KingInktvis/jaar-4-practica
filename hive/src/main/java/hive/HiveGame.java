package hive;

import nl.hanze.hive.Hive;

import java.util.List;

/**
 * Created by Skyerzz-LAPOTOP on 08/10/2019.
 */
public class HiveGame implements Hive {

    private HiveBoard hiveBoard;
    private HivePlayer blackPlayer, whitePlayer;
    private boolean whiteTurn;

    public HiveGame(){
        hiveBoard = HiveBoard.getInstance();
        blackPlayer = new HivePlayer(Player.BLACK);
        whitePlayer = new HivePlayer(Player.WHITE);
        whiteTurn = true;
    }

    @Override
    public void play(Tile tile, int q, int r) throws IllegalMove {
        //get current player
        HivePlayer player = whiteTurn ? whitePlayer : blackPlayer;
        //check if player HAS the tile left over
        if(!player.hasTile(tile)){
            throw new IllegalMove("Player does not have this tile in hand!");
        }
        //get the slot they want to place in, check if not null
        if(hiveBoard.getTile(q, r)!=null){
            throw new IllegalMove("This tile is already occupied!");
        }
        //find out if this is the first move
        if(player.hasAllTiles() && getPlayerFromColor(getOpponent(player.getPlayerColor())).hasAllTiles()){
            player.removeTile(tile);
            hiveBoard.placeTile(new BoardTile(q, r, tile, player.getPlayerColor()));
            whiteTurn = !whiteTurn;
            return;
        }
        //find the tile's neighbours. May not be empty, tiles have to connect
        List<BoardTile> neighbours = hiveBoard.getNeighbours(q, r);
        if(neighbours.isEmpty()){
            throw new IllegalMove("The tile has to connect to another tile!");
        }
        //its not the first move, check if the player has any tiles placed already
        if(player.hasAllTiles()){
            //they dont have tiles on the board yet, so it needs to be placed against an opposite color tile (the only one on the field)
            player.removeTile(tile);
            hiveBoard.placeTile(new BoardTile(q, r, tile, player.getPlayerColor()));
            whiteTurn = !whiteTurn;
            return;
        }else{
            //they already have tiles on the board
            for(BoardTile boardTile: neighbours){
                if(boardTile.getColor()==player.getPlayerColor()){
                    player.removeTile(tile);
                    hiveBoard.placeTile(new BoardTile(q, r, tile, player.getPlayerColor()));
                    whiteTurn = !whiteTurn;
                    return;
                }
            }
            //we werent able to find a tile of their color to connect to
            throw new IllegalMove("Tile does not connect to your color!");
        }
    }

    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {

    }

    @Override
    public void pass() throws IllegalMove {

    }

    @Override
    public boolean isWinner(Player player) {
        return false;
    }

    @Override
    public boolean isDraw() {
        return false;
    }

    public Player getOpponent(Player currentPlayer){
        return currentPlayer==Player.BLACK ? Player.WHITE : Player.BLACK;
    }

    public HivePlayer getPlayerFromColor(Player color){
        return color==Player.BLACK ? blackPlayer : whitePlayer;
    }
}
