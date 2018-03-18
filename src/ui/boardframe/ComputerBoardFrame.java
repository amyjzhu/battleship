package ui.boardframe;

import game.Board;
import game.Game;
import game.Tile;
import game.player.ComputerPlayer;
import ui.TileSquare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gijin on 2018-03-07.
 */
public class ComputerBoardFrame extends BoardFrame {

    private boolean clickable;

    public ComputerBoardFrame(Board board) {
        super(board);
        tiles.stream().forEach( tileSquare ->
        tileSquare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attackHandler(tileSquare);
            }}));
    }

    public void attackHandler(TileSquare tileSquare) {
        if (!Game.inSetup()) {
            tileSquare.setSelected(true);
            if (tileMarkedForAttack != null) {
                tileMarkedForAttack.setSelected(false);
            }
            tileMarkedForAttack = tileSquare;
        }
    }

    public void turn(BoardFrame boardFrame) {
        // make random guess
        System.out.println("Enemy turn!");
        clickable = false;
    /*    try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            // doesn't matter, do nothing
        }*/
        int guess = ComputerPlayer.makeGuess(boardFrame.getBoard());
        while (boardFrame.getBoard().getTileWithIndex(guess).getStatus() == Tile.Status.NEUTRAL) {
            // stall
        }
        System.out.println(boardFrame.getBoard().getAllTilesThatAreNotNeutral());
        Game.switchTurns();
        repaint();
        clickable = true;
    }
}
