package ui.boardframe;

import game.Board;
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
            }})); // want tiles to be listenning for attacks but only if it's the right turn // should also have an ATTACK button
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

    public void setIsClickable(boolean option) {
        clickable = option;
    }

    public void turn() {
        // make random guess
        clickable = false;
        ComputerPlayer.makeGuess(board);
        clickable = true;
    }
}
