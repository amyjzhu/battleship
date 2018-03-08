package ui.boardframe;

import game.Board;
import game.Ship;
import game.Tile;
import ui.TileSquare;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gijin on 2018-03-07.
 */
public class PlayerBoardFrame extends BoardFrame {

    TileSelectionHandler mouseHandler;
    private List<TileSquare> tilesSelectedForShip = new ArrayList<>();

    public PlayerBoardFrame(Board board) {
        super(board);
        mouseHandler = new TileSelectionHandler();
        tiles.stream().forEach(tileSquare ->
            tileSquare.addMouseListener(mouseHandler));

        addMouseListener(mouseHandler);
    }

    public void disableSelection() {
        mouseHandler.disableSelection();
    }

    public void turn() {
        // hm
        // needs to wait until your selection
        // call
    }

    private class TileSelectionHandler implements MouseListener {

        private boolean selectionTime = true;
        private List<Ship.Type> types = new LinkedList(Arrays.asList(Ship.Type.values())); // TODO move selection list into this class
        private Tile.Direction direction = Tile.Direction.DOWN;

        public void disableSelection() {
            selectionTime = false;
            isSetup = false;
        }

        private void swapDirection() {
            if (direction == Tile.Direction.DOWN) {
                direction = Tile.Direction.RIGHT;
            } else if (direction == Tile.Direction.RIGHT) {
                direction = Tile.Direction.DOWN;
            } // TODO cleaner way - maybe boolean ^=?
        }
        // only if

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                swapDirection();
                return;
            }

            if (selectionTime) {
                System.out.println("Clicked!!");
                Ship.Type type = types.get(0);
                verifyShip(type);
                types.remove(type);

                if (types.isEmpty()) {
                    disableSelection();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("mouseover!!");
            if (selectionTime) {

                Component component = e.getComponent();
                if (component instanceof TileSquare) { //make sure it's this board's tile square also!
                    selectAShip((TileSquare) component, types.get(0));
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("mouseout!!");
            if (selectionTime) {
                deselectShip();
            }
        }

        public void selectAShip(TileSquare tileFrame, Ship.Type shipType) {
            Tile tile = tileFrame.getTile();
            int tileindex = tile.getTileIndex();
            if (tile.hasNTilesInDirection(shipType.getSize() - 1, direction)) {
                for (int i = 0; i < shipType.getSize(); i++) {
                    TileSquare tileSquareToSelect = tilesToSquares.get(new Tile(tileindex));
                    tileSquareToSelect.setSelected(true);
                    tilesSelectedForShip.add(tileSquareToSelect);
                    System.out.println((shipType.getSize() - i) + "tiles left to select");
                    int newIndex = tile.getNextTileIndex(direction);
                    if (newIndex != -1) {
                        tileindex = newIndex;
                    } else {
                        break;
                    } // TODO not "selecting" right number of blocks? although all rendered
                } // can only enter when
                // can only click if number is matching
            }
            repaint();
        }

        public void deselectShip() {
            tilesSelectedForShip.forEach(t -> t.setSelected(false));
            tilesSelectedForShip.clear();
        }

        public void verifyShip(Ship.Type type) {
            if (tilesSelectedForShip.size() == type.getSize()) {
                List<Tile> tiles = tilesSelectedForShip.stream().map(TileSquare::getTile).collect(Collectors.toList());
                tilesSelectedForShip.stream().forEach(TileSquare::placeShip);
                List<Integer> tileIndicies = tiles.stream().map(Tile::getTileIndex).collect(Collectors.toList());
                Ship s = new Ship(type, tileIndicies);
                board.addShip(s);
                deselectShip();
                // rendering can come from board?
                repaint();
            }
        }
    }
}
