
package game.player;

import game.Board;
import game.Game;

import java.util.Random;

/**
 * Created by gijin on 2018-03-03.
 */
public class ComputerPlayer extends Player {

    public ComputerPlayer() {
        super();
        board.generateAllShips();
    }

    @Override
    public void makeGuess(int guess) {

    }

    @Override
    public void getHit(int hit) {

    }

    @Override
    public void toggleTurn() {
        if (isTurn) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // it's okay
            }
            makeGuess();
        }
    }

    // TODO better AI
    // TODO edge case - every board filled?
    public void makeGuess() {
        makeGuess(board);
    }

    public static int makeGuess(Board board) {
        int boardSize = Game.BOARD_SIZE * Game.BOARD_SIZE;
        boolean success = false;
        int numFailures = 0;

        while (!success) {
            int guess = new Random().nextInt(boardSize);

            if (numFailures == boardSize) {
                return -1;
            }
            if (board.isTileAlreadyHit(guess)) {
                numFailures++;
            } else {
                board.hit(guess);
                return guess;
            }
        }

        return -1;
    }
}
