package ui;

import game.Board;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
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
    private TileSquare tileMarkedForAttack;
    private boolean clickable;

    // TODO polymorphism?

    // represent a board
    public BoardFrame(Board thisBoard) {
        board = thisBoard;
        this.setLayout(new GridLayout(Game.BOARD_SIZE, Game.BOARD_SIZE, HGAP, VGAP));

        tiles = Arrays.stream(board.getTiles()).map(TileSquare::new).collect(Collectors.toList());
        tiles.forEach(tileSquare -> {
            //tileSquare.addBoardFrame(this);
            this.add(tileSquare);
            tileSquare.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (clickable) {
                        tileSquare.setSelected(true);
                        if (tileMarkedForAttack != null) {
                            tileMarkedForAttack.setSelected(false);
                        }
                        tileMarkedForAttack = tileSquare;
                    }
                }}); // want tiles to be listenning for attacks but only if it's the right turn // should also have an ATTACK button
        });
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
