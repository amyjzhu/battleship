package game.player;

import game.Board;

/**
 * Created by gijin on 2018-03-03.
 */
public abstract class Player {

    protected Board board;
    protected boolean isTurn;

    public Player() {
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void toggleTurn() {
        isTurn = !isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public boolean areAllShipsSunk() {
        return board.allShipsSunk();
    }

    public abstract void makeGuess(int guess);

    public abstract void getHit(int hit);

}
