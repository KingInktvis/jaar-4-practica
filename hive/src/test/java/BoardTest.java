import hive.BoardTile;
import hive.HiveBoard;
import hive.HiveGame;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Skyerzz-LAPOTOP on 09/10/2019.
 */
public class BoardTest {

    @Test
    void testBoardNeighboursWhenEmpty() {
        HiveBoard board = HiveBoard.getInstance();
        assertTrue(board.getNeighbours(999, 999).isEmpty());
    }

    @Test
    void testNeighboursFromTile() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.play(Hive.Tile.SOLDIER_ANT, 0, 0);
        game.play(Hive.Tile.SOLDIER_ANT, 1, 0);
        game.play(Hive.Tile.SOLDIER_ANT, -1, 0);
        game.play(Hive.Tile.SOLDIER_ANT, 2, 0);
        game.play(Hive.Tile.SOLDIER_ANT, -1, -1);
        game.play(Hive.Tile.SOLDIER_ANT, 1, -1);

        List<BoardTile> neighbours = HiveBoard.getInstance().getNeighbours(0, 0);
        boolean foundOneZero= false, foundNegOneNegOne = false, foundNegOneZero= false;
        for(BoardTile tile: neighbours){
            if(tile.getxCoord()==-1&&tile.getyCoord()==-1) {
                foundNegOneNegOne = true;
            }else if(tile.getxCoord()==1&&tile.getyCoord()==0){
                foundOneZero=true;
            }else if(tile.getxCoord()==-1&&tile.getyCoord()==0){
                foundNegOneZero = true;
            }else{
                fail();
            }
        }
        assertTrue(foundNegOneZero && foundNegOneNegOne && foundOneZero);

    }

    @Test
    void placeFirstTileAndFirstOpponentTileOnBoard(){
        HiveGame game = new HiveGame();
        try {
            game.play(Hive.Tile.SPIDER, 0, 0);
            game.play(Hive.Tile.QUEEN_BEE, 0, 1);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
            fail();
        }
        assertTrue(HiveBoard.getInstance().getTile(0, 0).get(0).getColor() == Hive.Player.WHITE);
        assertTrue(HiveBoard.getInstance().getTile(0, 0).get(0).getTileType() == Hive.Tile.SPIDER);
        assertTrue(HiveBoard.getInstance().getTile(0, 1).get(0).getColor() == Hive.Player.BLACK);
        assertTrue(HiveBoard.getInstance().getTile(0, 1).get(0).getTileType() == Hive.Tile.QUEEN_BEE);

        try {
            game.play(Hive.Tile.QUEEN_BEE, 0, 2);
            fail();
        } catch (Hive.IllegalMove illegalMove) {
            //its intended to fail.
        }
    }

    @Test
    void emptyBoardAtGameStart(){
        HiveBoard board = HiveBoard.getInstance();
        assertTrue(board.getBoardTiles().isEmpty());
    }
}
