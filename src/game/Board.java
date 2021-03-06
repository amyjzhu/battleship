package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

    private Tile[] boardPieces;
    private List<Ship> ships;

    public Board() {
        boardPieces = new Tile[Game.BOARD_SIZE*Game.BOARD_SIZE];
        for (int i = 0; i < 100 ; i++) {
            Tile tile = new Tile(i);
            boardPieces[i] = tile;
        }

        ships = new ArrayList<>();
    }

    public boolean isFullyPopulated() {
        return // DO A REAL CHECK
        ships.size() == Ship.Type.values().length;
    }

    public Tile[] getTiles() {
        return boardPieces;
    }

    public boolean isTileAlreadyHit(int index) {
        return (boardPieces[index].getStatus() == Tile.Status.HIT);
    }

    public Ship hit(Tile tile) {
        return hit(tile.getTileIndex());
    }

    public Ship hit(int index) {
        Ship ship = getShipIfHit(index);
        Tile tile = boardPieces[index];

        if (ship == null) {
            tile.miss();
        } else {
            tile.sink();
        }

        return ship;
    }

    public Tile getTileWithIndex(int index) {
        return boardPieces[index];
    }

    public List<Integer> getAllTilesThatAreNotNeutral() {
        return Arrays.stream(boardPieces).filter(t -> t.getStatus() != Tile.Status.NEUTRAL).map(Tile::getTileIndex).collect(Collectors.toList());
    }

    public boolean allShipsSunk() {
        return ships.stream().allMatch(this::isShipSunk);
    }

    public boolean isShipSunk(Ship ship) {
        return ship.getLocations().stream().allMatch(
                t -> boardPieces[t].getStatus() == Tile.Status.HIT);
    }

    private Ship getShipIfHit(int index) {
        for (Ship ship : ships) {
            if (ship.getLocations().contains(index)) {
                return ship;
            }
        }
        return null;
    }

    public void addShip(Ship s) {
        ships.add(s);
    }

    public void generateAllShips() {
        List<Tile> freeSpaces = getFreeSpaces();

        for (Ship.Type shipType : Ship.Type.values()) {
            Ship ship = Ship.createRandomShip(shipType, getFreeSpaces());
            addShip(ship);
            freeSpaces.removeAll(ship.getLocations().stream().map(Tile::new).collect(Collectors.toList()));
        }
    }

    public boolean areNSpacesInXDirectionFree(int n, Tile.Direction x) {
        // TODO just use Tile implementation and use this to make sure placements are valid in UI
        return false;
    }

    public List<Tile> getFreeSpaces() {
        List<Integer> used = new ArrayList<>();
        ships.forEach(ship -> used.addAll(ship.getLocations()));

        return Arrays.stream(boardPieces).filter(
                tile -> !used.contains(tile.getTileIndex()))
                .collect(Collectors.toList());
    }
}
