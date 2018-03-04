package test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.Ship;
import game.Tile;

import static junit.framework.TestCase.assertTrue;

public class ShipTest {


    @Test
    public void testCreateRandomShip() {
        List<Tile> list = new ArrayList<>();
        for (int i = 0; i < 100 ; i++) {
            Tile tile = new Tile(i);
            list.add(tile);
        }

        Ship ship = Ship.createRandomShip(Ship.Type.SUBMARINE, list);
        int previous = ship.getLocations().get(0);
        for (int i : ship.getLocations()) {
            int difference = i - previous;
            assertTrue(difference == 0 || difference == 1 || difference == Game.BOARD_SIZE);
            previous = i;
        }
    }
}
