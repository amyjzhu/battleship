package ui;

import game.Board;
import game.Game;
import game.Ship;
import game.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gijin on 2018-03-03.
 */
public class BoardFrame extends JPanel {

    private static final int HGAP = 2;
    private static final int VGAP = 2;

    private Board board;
    private List<TileSquare> tiles;
    private Map<Tile, TileSquare> tilesToSquares;
    private TileSquare tileMarkedForAttack;
    private List<TileSquare> tilesSelectedForShip = new ArrayList<>();
    private boolean clickable;
    private boolean isSetup = true;
    private TileSelectionHandler mouseHandler;

    // TODO polymorphism?

    // represent a board
    public BoardFrame(Board thisBoard) {
        board = thisBoard;
        this.setLayout(new GridLayout(Game.BOARD_SIZE, Game.BOARD_SIZE, HGAP, VGAP));

        //tiles = Arrays.stream(board.getTiles()).map(TileSquare::new).collect(Collectors.toList());
        tiles = new ArrayList<>();
        tilesToSquares = new HashMap<>();
        mouseHandler = new TileSelectionHandler();
        Arrays.stream(board.getTiles()).forEach(tile -> {
            //tileSquare.addBoardFrame(this);
            TileSquare tileSquare = new TileSquare(tile);
            this.add(tileSquare);
            tilesToSquares.put(tile, tileSquare);
            tileSquare.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    attackHandler(tileSquare);
                }}); // want tiles to be listenning for attacks but only if it's the right turn // should also have an ATTACK button
            tileSquare.addMouseListener(mouseHandler);

        });

        addMouseListener(mouseHandler);
    }

    public void attackHandler(TileSquare tileSquare) {
        if (clickable) {
            tileSquare.setSelected(true);
            if (tileMarkedForAttack != null) {
                tileMarkedForAttack.setSelected(false);
            }
            tileMarkedForAttack = tileSquare;
        }
    }

    public void selectionHandler(TileSquare tileSquare) {
        // get the next size and highlight

    }

    public void disableSelection() {
        mouseHandler.disableSelection();
    }

    private class TileSelectionHandler implements MouseListener {

        private boolean selectionTime = true;
        private List<Ship.Type> types = new LinkedList(Arrays.asList(Ship.Type.values())); // TODO move selection list into this class
        private Tile.Direction direction = Tile.Direction.DOWN;

        public void disableSelection() {
            selectionTime = false;
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
            // vertical or horiztonal...
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
                    }
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

    // two different classes...




/*
    public void selectAShip(TileSquare tileFrame, Ship.Type shipType) {
        // vertical or horiztonal...
        List<Tile> selectedTiles = new ArrayList<>();
        Tile tile = tileFrame.getTile();
        int tileindex = tile.getTileIndex();
        for (int i = 0; i < shipType.getSize() ; i++) {
            if (tile.canGetAdjacentTile(Tile.Direction.DOWN)) { // toggle with rclikc
                tileFrame.setSelected(true);

// hover and click
                selectedTiles.add(tile);
            }
        } // can only enter when
    }*/

    public boolean isSetup() {
        return isSetup;
    }

    public void setSetup(boolean setup) {
        isSetup = setup;
    }

    public void setIsClickable(boolean option) {
        clickable = option;
    }

    public Board getBoard() {
        return board;
    }

    public TileSquare getTileMarkedForAttack() {
        return tileMarkedForAttack;
    }

    public void setTileMarkedForAttack(TileSquare ts) {
        this.tileMarkedForAttack = ts;
    }

    public void resetTileForAttack() {
        if (tileMarkedForAttack != null) {
            tileMarkedForAttack.setSelected(false);
        }
        tileMarkedForAttack = null;
    }

    public void executeHit() {
        board.hit(tileMarkedForAttack.getTile());
    }
}
