import hive.HiveBoard;
import hive.HiveGame;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void placeFirstTileAndFirstOpponentTileOnBoard(){
        HiveGame game = new HiveGame();
        try {
            game.play(Hive.Tile.SPIDER, 0, 0);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }
        try {
            game.play(Hive.Tile.QUEEN_BEE, 0, 1);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        }
        assertTrue(HiveBoard.getInstance().getTile(0, 0).getColor() == Hive.Player.WHITE);
        assertTrue(HiveBoard.getInstance().getTile(0, 0).getTileType() == Hive.Tile.SPIDER);
        assertTrue(HiveBoard.getInstance().getTile(0, 1).getColor() == Hive.Player.BLACK);
        assertTrue(HiveBoard.getInstance().getTile(0, 1).getTileType() == Hive.Tile.QUEEN_BEE);

        try {
            game.play(Hive.Tile.QUEEN_BEE, 0, 2);
            assertTrue(false);
        } catch (Hive.IllegalMove illegalMove) {
            //its intended to fail.
            assertTrue(true);
        }
    }
}
