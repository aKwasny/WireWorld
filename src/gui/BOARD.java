package gui;

import work.Community;
import work.other.preparedforms.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by andrzej on 04.06.16.
 */
public class BOARD extends JPanel implements MouseListener, MouseMotionListener {

    private Community community;
    private Element elem;
    private boolean isClicked;
    private int squareSize = 10;
    private final int panelHeight = 600;
    private final int panelWidth = 600;
    private boolean ifDrawGrid = true;

    public BOARD(Community community) {
        this.community = community;
        calculateSquareSize();
    }

    private void calculateSquareSize () {
        int communityBiggerDimension = community.getHeight() > community.getWidth() ? community.getHeight() : community.getWidth();
        squareSize = panelHeight / communityBiggerDimension;
    }

    public int getPanelHeight () {
        return panelHeight;
    }

    public int getPanelWidth () {
        return panelWidth;
    }

    public void setCommunity(Community community) {
        this.community = community;
        calculateSquareSize();
    }

    public void setElement (Element element){
        elem = element;
    }

    public void isGridDrawn(boolean bool) {
        ifDrawGrid = bool;
    }

    @Override
    public void paintComponent (Graphics graphics) {
        super.paintComponent(graphics);
        for (int i = 0; i < community.getHeight(); i++) {
            for (int j = 0; j < community.getWidth(); j++) {
                graphics.setColor(community.getCell(i,j).getColor());
                graphics.fillRect(j * squareSize, i * squareSize, squareSize, squareSize);
            }
        }
        if (ifDrawGrid) {
            graphics.setColor(Color.darkGray);
            for (int i = 0; i < community.getWidth(); i++) {
                graphics.fillRect(i * squareSize, 0, 1, community.getHeight() * squareSize);
            }
            for (int i = 0; i < community.getHeight(); i++) {
                graphics.fillRect(0, i * squareSize, community.getWidth() * squareSize, 1);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        elem.setElementOnBoard((int) y / squareSize, (int) x / squareSize, community);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isClicked = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isClicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        isClicked = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (isClicked){
            int x = e.getX();
            int y = e.getY();
            elem.setElementOnBoard((int) y / squareSize, (int) x / squareSize, community);
        }
        repaint();
    }
}
