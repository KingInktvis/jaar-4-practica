package hive;

import nl.hanze.hive.Hive;

/**
 * Created by Skyerzz-LAPOTOP on 08/10/2019.
 */
public class HiveGame implements Hive {

    @Override
    public void play(Tile tile, int q, int r) throws IllegalMove {

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
}
