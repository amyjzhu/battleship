package ui.boardframe;

import game.Board;
import game.Game;
import game.Tile;
import ui.TileSquare;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by gijin on 2018-03-03.
 */
public class BoardFrame extends JPanel {

    private static final int HGAP = 2;
    private static final int VGAP = 2;

    protected Board board;
    protected List<TileSquare> tiles;
    protected Map<Tile, TileSquare> tilesToSquares;
    protected TileSquare tileMarkedForAttack;
    protected boolean isSetup = true;

    // TODO polymorphism?

    // represent a board
    public BoardFrame(Board thisBoard) {
        board = thisBoard;
        this.setLayout(new GridLayout(Game.BOARD_SIZE, Game.BOARD_SIZE, HGAP, VGAP));

        //tiles = Arrays.stream(board.getTiles()).map(TileSquare::new).collect(Collectors.toList());
        tiles = new ArrayList<>();
        tilesToSquares = new HashMap<>();
        Arrays.stream(board.getTiles()).forEach(tile -> {
            //tileSquare.addBoardFrame(this);
            TileSquare tileSquare = new TileSquare(tile);
            this.add(tileSquare);
            tiles.add(tileSquare); // TODO need?
            tilesToSquares.put(tile, tileSquare);
        });
    }


    public void selectionHandler(TileSquare tileSquare) {
        // get the next size and highlight

    }

    public void doEnemyTurn() {

        // set it to be your turn
    }

    public boolean isSetup() {
        return isSetup;
    }

    public void setSetup(boolean setup) {
        isSetup = setup;
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

    // do turn function that's bounced arond until end of game
}
