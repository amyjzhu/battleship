package test;

import org.junit.Assert;
import org.junit.Test;

import game.Tile;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TileTest {

    @Test
    public void testConvertFromIndexToBoardAndViceVersa() {
        assertIndexAndStringAreSame(0, "A1");
        assertIndexAndStringAreSame(4, "E1");
        assertIndexAndStringAreSame(19, "J2");
        assertIndexAndStringAreSame(20, "A3");
        assertIndexAndStringAreSame(45, "F5");
        assertIndexAndStringAreSame(99, "J10");
    }

    @Test
    public void testCanGetBesideTile() {
        assertFalse(getCanFindAdjacentTile(0, Tile.Direction.UP));
        assertTrue(getCanFindAdjacentTile(0, Tile.Direction.RIGHT));
        assertTrue(getCanFindAdjacentTile(10, Tile.Direction.UP));
        assertTrue(getCanFindAdjacentTile(4, Tile.Direction.LEFT));
        assertFalse(getCanFindAdjacentTile(4, Tile.Direction.UP));
        assertFalse(getCanFindAdjacentTile(90, Tile.Direction.DOWN));
        assertFalse(getCanFindAdjacentTile(99, Tile.Direction.RIGHT));
        assertTrue(getCanFindAdjacentTile(99, Tile.Direction.LEFT));
        assertTrue(getCanFindAdjacentTile(4, Tile.Direction.DOWN));
    }

    @Test
    public void testGetBesideTile() {
        assertEquals(54, new Tile(53).getNextTileIndex(Tile.Direction.RIGHT));
        assertEquals(63, new Tile(53).getNextTileIndex(Tile.Direction.DOWN));
        assertEquals(43, new Tile(53).getNextTileIndex(Tile.Direction.UP));
        assertEquals(52, new Tile(53).getNextTileIndex(Tile.Direction.LEFT));

        assertEquals(100, new Tile(99).getNextTileIndex(Tile.Direction.RIGHT));
        assertEquals(109, new Tile(99).getNextTileIndex(Tile.Direction.DOWN));
        assertEquals(99, new Tile(89).getNextTileIndex(Tile.Direction.DOWN));
        assertEquals(-10, new Tile(0).getNextTileIndex(Tile.Direction.UP));
        assertEquals(-1, new Tile(0).getNextTileIndex(Tile.Direction.LEFT));

    }


    private boolean getCanFindAdjacentTile(int index, Tile.Direction direction) {
        Tile tile = new Tile(index);
        return tile.canGetAdjacentTile(direction);
    }

    private void assertIndexAndStringAreSame(int index, String pos) {
        String gridResult = Tile.fromIndex(index);
        assertEquals(pos, gridResult);

        int indexResult = Tile.fromGrid(pos);
        assertEquals(index, indexResult);
    }
}
