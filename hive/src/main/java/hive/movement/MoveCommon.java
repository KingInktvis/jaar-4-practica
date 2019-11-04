package hive.movement;

import hive.Board;
import hive.Coordinate;
import nl.hanze.hive.Hive;

import java.util.ArrayList;

/**
 * Created by Skyerzz-LAPOTOP on 30/10/2019.
 */
public class MoveCommon {

    public static boolean isValidMove(Board board, Coordinate from, Coordinate to) throws Hive.IllegalMove {
        var tile = board.getTile(from);
        if (!isCommonValidMove(board, from, to)) {
            return false;
        }
        switch (tile.getType()) {
            case QUEEN_BEE:
                return MoveQueen.canQueenMove(board, from, to);
            case SPIDER:
                break;
            case BEETLE:
                return MoveBeetle.canBeetleMove(board, from, to);
            case GRASSHOPPER:
                break;
            case SOLDIER_ANT:
                return MoveAnt.canAntMove(board, from, to);
        }
        return true;
    }

    public static boolean isCommonValidMove(Board board, Coordinate from, Coordinate to) {
        if (from.equals(to)) {
            return false;
        }
        return true;
    }

    public static ArrayList<Coordinate> getCoordinatesInRange(Board board, Coordinate center, int range, boolean canMoveThroughTiles) {
        ArrayList<Coordinate> queue = new ArrayList<>();
        queue.add(center);
        return filterNoNeighbours(board, center, getCoordinatesInRangeRecursive(board, range, queue, canMoveThroughTiles));
    }

    private static ArrayList<Coordinate> getCoordinatesInRangeRecursive(Board board, int range, ArrayList<Coordinate> queue, boolean canMoveThroughTiles) {
        ArrayList<Coordinate> visitedNodes = new ArrayList<>();
        ArrayList<Coordinate> coordinatesInRange = new ArrayList<>();
        Coordinate startposition = queue.get(0);
        while (!queue.isEmpty() && range > 0) {
            ArrayList<Coordinate> subqueue = new ArrayList<>(queue);
            queue.clear();
            while (!subqueue.isEmpty()) {
                Coordinate current = subqueue.remove(0);
                if (visitedNodes.contains(current)) {
                    continue;
                }
                visitedNodes.add(current);
                ArrayList<Coordinate> neighbours = canMoveThroughTiles ? current.adjacentCoordinates() : board.getEmptyAdjacentLocations(current);
                if (!canMoveThroughTiles) {
                    neighbours = filterNeighboursThroughMovement(board, neighbours, current, startposition);
                }
                for (Coordinate neigh : neighbours) {
                    if (!coordinatesInRange.contains(neigh)) {
                        coordinatesInRange.add(neigh);
                    }
                }
                queue.addAll(neighbours);
            }
            range--;
        }
        coordinatesInRange.remove(startposition);
        return coordinatesInRange;
    }

    public static ArrayList<Coordinate> getCoordinatesInStepAmount(Board board, Coordinate center, int steps){
        ArrayList<Coordinate> queue = new ArrayList<>(){};
        ArrayList<Coordinate> visitedCoords = new ArrayList<>();
        queue.add(center);
        while(steps-->0){
            ArrayList<Coordinate> newQueue = new ArrayList<>();
            while(!queue.isEmpty()){
                Coordinate coord = queue.remove(0);
                if(visitedCoords.contains(coord)){
                    continue;
                }
                visitedCoords.add(coord);
                ArrayList<Coordinate> neighbours = coord.adjacentCoordinates();
                neighbours = filterNeighboursThroughMovement(board, neighbours, coord, center);
                for(Coordinate neigh: neighbours){
                    if(!newQueue.contains(neigh) && !visitedCoords.contains(neigh)){
                        newQueue.add(neigh);
                    }
                }
            }
            if(steps==0){
                return filterNoNeighbours(board, center, newQueue);
            }
            queue.addAll(newQueue);
        }
        return null;
    }

    public static ArrayList<Coordinate> filterNeighboursThroughMovement(Board board, ArrayList<Coordinate> neighbours, Coordinate startPosition, Coordinate originalPosition) {
        ArrayList<Coordinate> neighboursToReturn = new ArrayList<>();
        for (Coordinate neigh : neighbours) {
            if (!stayConnectedOneStep(board, startPosition, neigh) || breaksChain(board, startPosition, neigh, originalPosition)) {
                continue;
            }
            ArrayList<Coordinate> commonNeighbours = neigh.commonNeighbours(startPosition);
            for (Coordinate coord : commonNeighbours) {
                if (board.getTile(coord) == null) {
                    neighboursToReturn.add(neigh);
                    break;
                }
            }
        }
        return neighboursToReturn;
    }

    public static boolean breaksChain(Board board, Coordinate fromPosition, Coordinate toPosition, Coordinate originalPosition){
        ArrayList<Coordinate> visitedCoords = new ArrayList<>();
        ArrayList<Coordinate> queue = new ArrayList<>();
        queue.add(toPosition);
        while(!queue.isEmpty()){
            Coordinate coord = queue.remove(0);
            if(visitedCoords.contains(coord) || coord.equals(fromPosition) || coord.equals(originalPosition)){
                continue;
            }
            visitedCoords.add(coord);
            for(Coordinate neighbour: coord.adjacentCoordinates()){
                if(board.getTile(neighbour)!=null && !neighbour.equals(fromPosition)){
                    queue.add(neighbour);
                }
            }
        }
        return board.amountOfCoordinatesOccupied()!=visitedCoords.size();
    }

    private static ArrayList<Coordinate> filterNoNeighbours(Board board, Coordinate fromPosition, ArrayList<Coordinate> options){
        ArrayList<Coordinate> filteredOptions = new ArrayList<>();
        for(Coordinate coord:options){
            for(Coordinate neighbour:coord.adjacentCoordinates()){
                if(!neighbour.equals(fromPosition) && board.getTile(neighbour)!=null && !filteredOptions.contains(coord)){
                    filteredOptions.add(coord);
                }
            }
        }
        return filteredOptions;
    }

    public static boolean checkAdjacentToTileWithOneStep(Board board, Coordinate to) {
        var neighbours = board.getNeighbours(to);
        if (neighbours.size() <= 1) {
            return false;
        }
        return true;
    }

    public static boolean allTilesAreConnected(Board board) {
        return board.allTilesConnected();
    }

    public static boolean stayConnectedOneStep(Board board, Coordinate from, Coordinate to) {
        var shared = from.commonNeighbours(to);
        return board.getTile(shared.get(0)) != null || board.getTile(shared.get(1)) != null;
    }

    public static boolean gapForMovement(Board board, Coordinate from, Coordinate to) {
        var shared = from.commonNeighbours(to);
        return board.getTile(shared.get(0)) == null || board.getTile(shared.get(1)) == null;
    }


}
