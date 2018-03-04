package game;

public class Tile {

    public enum Status {
        NEUTRAL,
        HIT,
        MISS
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    Status status;
    int tileIndex;


    public Tile(int i) {
        tileIndex = i;
        status = Status.NEUTRAL;
    }

    public Status getStatus() {
        return status;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public void sink() {
        status = Status.HIT;
    }

    public void miss() {
        status = Status.MISS;
    }

    public boolean canGetAdjacentTile(Direction direction) {
        if (direction.equals(Direction.LEFT)) {
            return tileIndex % Game.BOARD_SIZE != 0;
        } else if (direction.equals(Direction.RIGHT)) {
            return tileIndex % Game.BOARD_SIZE != (Game.BOARD_SIZE - 1);
        } else if (direction.equals(Direction.UP)) {
            return tileIndex / Game.BOARD_SIZE != 0;
        } else if (direction.equals(Direction.DOWN)) {
            return tileIndex / Game.BOARD_SIZE != (Game.BOARD_SIZE - 1);
        }

        return false;
    }

    public int getNextTileIndex(Direction direction) {
        switch (direction) {
            case UP:
                return tileIndex - Game.BOARD_SIZE;
            case DOWN:
                return tileIndex + Game.BOARD_SIZE;
            case LEFT:
                return tileIndex - 1;
            case RIGHT:
                return tileIndex + 1;
        }

        return -1;
    }


    public static String fromIndex(int i) {
        // board starts by going 1 2 3 4 across the alphabet
        String num = String.valueOf((i / Game.BOARD_SIZE) + 1);
        String alpha = getAlphabet((i % Game.BOARD_SIZE) + 1);
        return alpha + num;
    }

    public static int fromGrid(String grid) {
        String alpha = grid.substring(0,1);
        int num = Integer.parseInt(grid.substring(1));

        int tens = (num - 1) * Game.BOARD_SIZE;
        int ones = getNumeric(alpha) - 1;

        return tens + ones;
    }
    private static String getAlphabet(int i) {
        // upper_case + 1 since the modulo
        int upperAscii = 64;
        return String.valueOf(Character.toChars(upperAscii + i));
    }

    private static int getNumeric(String alpha) {
        // upper_case + 1 since the modulo
        int upperAscii = 64;
        return (int) alpha.toCharArray()[0] - upperAscii;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tile tile = (Tile) o;

        return tileIndex == tile.tileIndex;
    }

    @Override
    public int hashCode() {
        return tileIndex;
    }
}
