package ui;

import game.Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by gijin on 2018-03-03.
 */
public class GameFrame extends JFrame {

    public static final Color BG_COLOUR = Color.BLACK;
    public static final Color CELL_COLOUR = new Color(233,185,254);
    public static final Color BUTTON_COLOUR = new Color(232, 220, 239);
    public static final Font BUTTON_FONT = Font.getFont("Comic Sans");

    BoardFrame enemyBoardFrame;
    BoardFrame yourBoardFrame;
    private Game game;

    public GameFrame(Game game) {
        JPanel topLevelPane = new JPanel();
        topLevelPane.setLayout(new BoxLayout(topLevelPane, BoxLayout.Y_AXIS));
        topLevelPane.setBackground(BG_COLOUR);
        topLevelPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        setTitle("Battleship");
        setSize(600, 1200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.game = game;
        enemyBoardFrame = new BoardFrame(Game.getPlayerTwo().getBoard());
        enemyBoardFrame.getBoard().generateAllShips();
        enemyBoardFrame.setIsClickable(false);
        topLevelPane.add(enemyBoardFrame);

        yourBoardFrame = new BoardFrame(Game.getPlayerOne().getBoard());
        enemyBoardFrame.setIsClickable(true);
        topLevelPane.add(yourBoardFrame);


        topLevelPane.add(makeAttackButton());

        this.add(topLevelPane);

        setVisible(true);
    }

    public JButton makeAttackButton() {
        JButton attack = new JButton("Launch attack!");

        attack.setBorderPainted(false);
        attack.setFocusPainted(false);
        attack.setContentAreaFilled(false);
        attack.setOpaque(true);
        attack.setPreferredSize(new Dimension(200,50));

        attack.setBackground(GameFrame.BUTTON_COLOUR);
        attack.setFont(GameFrame.BUTTON_FONT);

        attack.setBorder(new EmptyBorder(10,10,10,10));
        attack.setAlignmentX(Component.CENTER_ALIGNMENT);

        attack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.isItPlayerOneTurn()) {
                    TileSquare tile = enemyBoardFrame.getTileMarkedForAttack();
                    // this is stupid TODO and make sure to render based on tile
                    // TODO only let the button be highlighted during turn
                    if (tile != null) {
                        enemyBoardFrame.executeHit();
                        System.out.println(tile);
                    } else {
                        System.out.println("null tilesqr");
                    }
                    enemyBoardFrame.resetTileForAttack();
                }
            }
        });

        return attack;
    }

    public static void main(String[] args) {
        new GameFrame(new Game());
    }
}
