package ui;

import game.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gijin on 2018-03-03.
 */
public class TileSquare extends JButton {

    private Tile tile;
    private boolean selected;
    private boolean hasShip;
    //private BoardFrame board;
    // just render based on status

    public TileSquare(Tile tile) {
        //this.setSize(100,100); // SET SMALLER WITH LOWER DPI
        //this.setPreferredSize(new Dimension(100,100)); // WHich one??
        this.tile = tile;
        this.setText(Tile.fromIndex(tile.getTileIndex()));
        setOpaque(true);
        this.setBackground(GameFrame.CELL_COLOUR); // change this to be customizable
        this.setForeground(GameFrame.BG_COLOUR);
        this.setBorderPainted(false);
        setOpaque(true);
        this.setFocusPainted(false);
        this.setContentAreaFilled(true);
    }

    public Tile getTile() {
        return tile;
    }

    // Strategy for sp?
    // then when the "confirm" button is hit, tile is hit
    public void setSelected(boolean selected) {
        if (tile.getStatus() == Tile.Status.NEUTRAL) {
            this.selected = selected;
        }
        repaint();
    }

    public void placeShip() {
        this.hasShip = true;
    }

    public void removeShip() {
        this.hasShip = true;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (tile.getStatus() == Tile.Status.HIT) {
            g.setColor(Color.red);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (tile.getStatus() == Tile.Status.MISS) {
            g.setColor(Color.blue);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (hasShip) { // TODO this is janky and bad
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else if (selected) {
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, getWidth(), getHeight()); // hmm
        }
    }

}
