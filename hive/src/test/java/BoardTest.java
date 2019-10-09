import hive.HiveBoard;
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
}
