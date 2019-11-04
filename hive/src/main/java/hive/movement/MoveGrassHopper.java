package hive.movement;

import hive.Board;
import hive.Coordinate;

import java.util.ArrayList;

/**
 * Created by Skyerzz-LAPOTOP on 04/11/2019.
 */
public class MoveGrassHopper {

    public static boolean canGrassHopperMove(Board board, Coordinate from, Coordinate to){
        return getGrassHopperOptions(board, from).contains(to);
    }

    public static boolean doesGrassHopperHaveMoveOptions(Board board, Coordinate grassHopper){
        return getGrassHopperOptions(board, grassHopper).size()>0;
    }

    public static ArrayList<Coordinate> getGrassHopperOptions(Board board, Coordinate from){
        ArrayList<Coordinate> options = new ArrayList<>();
        Coordinate coordinate = getHorizontalOption(board, from, true);
        if(coordinate!=null){
            options.add(coordinate);
        }
        coordinate = getHorizontalOption(board, from, false);
        if(coordinate!=null){
            options.add(coordinate);
        }
        coordinate = getUpperLeftToBottomRightOption(board, from, true);
        if(coordinate!=null){
            options.add(coordinate);
        }
        coordinate = getUpperLeftToBottomRightOption(board, from, false);
        if(coordinate!=null){
            options.add(coordinate);
        }
        coordinate = getUpperRightToBottomLeftOption(board, from, true);
        if(coordinate!=null){
            options.add(coordinate);
        }
        coordinate = getUpperRightToBottomLeftOption(board, from, false);
        if(coordinate!=null){
            options.add(coordinate);
        }
        return filterChainBreaks(board, options, from);
    }

    public static ArrayList<Coordinate> filterChainBreaks(Board board, ArrayList<Coordinate> options, Coordinate origin){
        ArrayList<Coordinate> filteredOptions = new ArrayList<>();
        for(Coordinate coord: options){
            if(!MoveCommon.breaksChain(board, origin, coord, origin)){
                filteredOptions.add(coord);
            }
        }
        return filteredOptions;
    }

    public static Coordinate getHorizontalOption(Board board, Coordinate startingPosition, boolean positiveSide){
        int hops = 0;
        while(board.getTile(startingPosition)!=null){
            startingPosition = new Coordinate(startingPosition.getQ()+(positiveSide ? 1 : -1), startingPosition.getR());
            hops++;
        }
        return hops>1 ? startingPosition : null;
    }

    public static Coordinate getUpperLeftToBottomRightOption(Board board, Coordinate startingPosition, boolean positiveSide){
        int hops = 0;
        while(board.getTile(startingPosition)!=null){
            startingPosition = new Coordinate(startingPosition.getQ(), startingPosition.getR()+(positiveSide ? 1 : -1));
            hops++;
        }
        return hops>1 ? startingPosition : null;
    }

    public static Coordinate getUpperRightToBottomLeftOption(Board board, Coordinate startingPosition, boolean positiveSide){
        int hops = 0;
        while(board.getTile(startingPosition)!=null){
            startingPosition = new Coordinate(startingPosition.getQ() + (positiveSide ? 1 : -1), startingPosition.getR()+(positiveSide ? -1 : 1));
            hops++;
        }
        return hops>1 ? startingPosition : null;
    }
}
