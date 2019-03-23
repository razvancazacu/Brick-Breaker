package com.brickBracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 *           Class that implements the game
 *  it extends a JPanel so it can be inserted into the JFrame
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 30;

    private Timer timer;
    private int delay = 6;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;

    public Gameplay() {

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }

    /*
     * Painting the graphics for the game
     */

    public void paint(Graphics graphics) {

        // background
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, 692, 592);

        // borders
        graphics.setColor(Color.yellow);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(681, 0, 3, 592);

        // paddle
        graphics.setColor(Color.green);
        graphics.fillRect(playerX, 550, 100, 8);

        // ball
        graphics.setColor(Color.red);
        graphics.fillOval(ballPosX, ballPosY, 20, 20);

        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();

        if (play == true) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYDir = -ballYDir;
            }
            ballPosX += ballXDir;
            ballPosY += ballYDir;
            if (ballPosX < 0) {
                ballXDir = -ballXDir;
            }
            if (ballPosY < 0) {
                ballYDir = -ballYDir;
            }
            if (ballPosX > 670) {
                ballXDir = -ballXDir;
            }

        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        /*Checking if right or left key is pressed for movement*/
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX > 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX > 600) {
                playerX = 600;
            } else {
                moveLeft();
            }
        }
    }

    private void moveLeft() {
        play = true;
        playerX -= 20;
    }

    private void moveRight() {
        play = true;
        playerX += 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

