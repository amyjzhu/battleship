package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Ship {

    public enum Type {
        CARRIER(5),
        BATTLESHIP(4),
        CRUISER(3),
        SUBMARINE(3),
        DESTROYER(2);

        int size;

        Type(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    private List<Integer> locations;

    private Type type;
    private boolean sunk;

    public Ship(Type type, List<Integer> locationsChosen) {
        this.type = type;
        locations = locationsChosen;
        sunk = false;
    }

    public static int totalNumShipTiles() {
        return Arrays.stream(Type.values())
                .map(Ship.Type::getSize)
                .reduce((Integer size, Integer acc) -> size + acc).get();
    }

    public static Ship createRandomShip(Type type, List<Tile> freeSpaces) {

        List<Integer> location;
        boolean horizontal = new Random().nextBoolean();

        if (horizontal) {
            location = createHorizontalShipLocation(type, freeSpaces);
        } else {
            location = createVerticalShipLocation(type, freeSpaces);
        }

        return new Ship(type, location);
    }

    private static List<Integer> createVerticalShipLocation(Type type, List<Tile> freeSpaces) {
        return createShipLocationByDirection(type, freeSpaces, Tile.Direction.DOWN);
    }

    private static List<Integer> createHorizontalShipLocation(Type type, List<Tile> freeSpaces) {
        return createShipLocationByDirection(type, freeSpaces, Tile.Direction.RIGHT);
    }

    // TODO there's probably a better algorithm for implementing this random ship generation
    private static List<Integer> createShipLocationByDirection(Type type, List<Tile> freeSpaces, Tile.Direction direction) {
        // recursive?
        List<Integer> potentialSpaces = new ArrayList<>();
        while (true) {
            int index = new Random().nextInt(freeSpaces.size());
            Tile tile = freeSpaces.get(index);
            for (int size = type.getSize(); size > 0; size--) {
                potentialSpaces.add(tile.getTileIndex());
                if (tile.canGetAdjacentTile(direction) &&
                        freeSpaces.contains(new Tile(tile.getNextTileIndex(direction)))) {
                    tile = new Tile(tile.getNextTileIndex(direction));
                } else {
                    potentialSpaces.clear();
                    break;
                }
            }
            if (potentialSpaces.size() == type.getSize()) {
                return potentialSpaces;
            }
        }
    }

    public List<Integer> getLocations() {
        return locations;
    }

}
