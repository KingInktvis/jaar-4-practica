package hive.move;

import hive.Board;
import hive.Coordinate;
import hive.movement.MoveSpider;
import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpiderMoveTest {

    @Test
    void spiderSingleSurroundment(){
        Board board = new Board();
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.SPIDER, new Coordinate(1, 0));


        assertTrue(MoveSpider.canSpiderMove(board, new Coordinate(0, 0), new Coordinate(2,0)));
    }
}
