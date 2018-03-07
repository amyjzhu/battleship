package game;

import game.player.ComputerPlayer;
import game.player.HumanPlayer;
import game.player.Player;

import java.util.Optional;

public class Game {

    public static final int BOARD_SIZE = 10;

    private static Player playerOne;
    private static Player playerTwo;
    private static int turnNumber;
    private static Phase phase;

    public enum Phase {
        SETUP,
        PLAY
    }

    public Game() {
        setup();
    }

    public static boolean inSetup() {
        return phase == Phase.SETUP;
    }

    public static boolean inPlay() {
        return phase == Phase.PLAY;
    }

    public static void setPhase(Phase phase) {
        Game.phase = phase;
    }

    public static Player getPlayerOne() {
        return playerOne;
    }

    public static Player getPlayerTwo() {
        return playerTwo;
    }

    public static void main(String[] args) {
        Board enemyBoard = new Board();
        enemyBoard.generateAllShips();
        System.out.println(enemyBoard);
    }

    public static void setup() {
        phase = Phase.SETUP;
        playerOne = new HumanPlayer();
        playerTwo = new ComputerPlayer();
        playerOne.setTurn(true);
        playerTwo.setTurn(false);
    }

    public static void arrangeBoard() {
        // get a list of pieces
        playerOne.setTurn(true);
    }

    public static void runRound() {

    }

    public static void switchTurns() {
        if (turnNumber > Ship.totalNumShipTiles()) {
            Optional<Player> potentialWinner = getWinner();
            potentialWinner.ifPresent(Game::endGame);
            return; // not needed probably
        }

        playerOne.toggleTurn();
        playerTwo.toggleTurn();
    }

    public static boolean isItPlayerOneTurn() {
        return playerOne.isTurn();
    }

    public static Optional<Player> getWinner() {
        if (playerOne.areAllShipsSunk()) {
            return Optional.of(playerTwo);
        } else if (playerTwo.areAllShipsSunk()) {
            return Optional.of(playerOne);
        } else {
            return Optional.empty();
        }
    }

    public static void endGame(Player player) {

    }

}
