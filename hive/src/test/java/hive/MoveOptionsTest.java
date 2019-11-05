package hive;

import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Skyerzz-LAPOTOP on 04/11/2019.
 */
public class MoveOptionsTest {

    @Test
    void testValidPass() throws Hive.IllegalMove {
        Board board = new Board();
        board.placeTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.SOLDIER_ANT, new Coordinate(0, -1));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.SPIDER, new Coordinate(0, -2));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.SOLDIER_ANT, new Coordinate(0, -3));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.SOLDIER_ANT, new Coordinate(1, -1));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.SOLDIER_ANT, new Coordinate(2, -2));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.SPIDER, new Coordinate(3, -3));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER, new Coordinate(0, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.BEETLE, new Coordinate(0, 2));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.SPIDER, new Coordinate(-1, 1));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER, new Coordinate(-2, 2));
        board.placeTile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER, new Coordinate(-3, 3));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.SOLDIER_ANT, new Coordinate(-4, 4));

        HiveGame game = new HiveGame(board);
        game.pass();
    }

    @Test
    void testInvalidPass() throws Hive.IllegalMove {
        Board board = new Board();
        board.placeTile(Hive.Player.WHITE, Hive.Tile.QUEEN_BEE, new Coordinate(0, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(-1, 0));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(0, -1));
        board.placeTile(Hive.Player.BLACK, Hive.Tile.QUEEN_BEE, new Coordinate(1, -1));

        HiveGame game = new HiveGame(board);
        game.pass(); //white is allowed to pass
        assertThrows(Hive.IllegalMove.class, game::pass); //black is NOT allowed to pass
    }
}
