package com.brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*  Class that implements the game
 *  it extends a JPanel so it can be inserted into the JFrame */
public class GameMovement extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks;
    private int brickCol;
    private int brickRow;

    private Timer timer;
    private int delay = 6;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;

    private LevelGenerator map;

    /* Constructor for starting the game.
     * Register the GameMovement object to the KeyListener interface,
     * change the focus to this JPanel,
     * disable the traversal keys : tab, shift + tab, etc */
    public GameMovement() {
        brickRow = 3;
        brickCol = 4;
        map = new LevelGenerator(brickRow, brickCol);
        totalBricks = brickRow * brickCol;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }

    /* Painting the graphics for the game by
     * Creating a rectangle or oval form for
     * every object that is displayed.
     * At the end call dispose() to release the resources.
     *
     * For future : replace rectangles and ovals for .png pixel art. */
    public void paint(Graphics graphics) {

        // background
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, 692, 592);

        // borders
        graphics.setColor(Color.yellow);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(681, 0, 3, 592);

        // drawing the map
        map.draw((Graphics2D) graphics);

        // score
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Candara", Font.BOLD, 25));
        graphics.drawString("" + score, 590, 30);

        // paddle
        graphics.setColor(Color.green);
        graphics.fillRect(playerX, 550, 100, 8);

        // ball
        graphics.setColor(Color.red);
        graphics.fillOval(ballPosX, ballPosY, 20, 20);

        if (totalBricks <= 0) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Candara", Font.BOLD, 30));
            graphics.drawString("You Win, Score: "+ score, 240, 300);

            graphics.setFont(new Font("Candara", Font.BOLD, 30));
            graphics.drawString("Press to Restart", 250, 350);
        }
        if (ballPosY > 570) {
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Candara", Font.BOLD, 30));
            graphics.drawString("Game Over, Score: "+ score, 240, 300);

            graphics.setFont(new Font("Candara", Font.BOLD, 30));
            graphics.drawString("Press to Restart", 250, 350);

        }

        graphics.dispose();
    }

    @Override
    /* On action performed check if the ball reached window boundaries or pallet
     * Change it's movement based on that
     * recall the paint function */
    public void actionPerformed(ActionEvent e) {

        timer.start();

        if (play == true) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYDir = -ballYDir;
            }

            BallHitsBrick:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRectangle = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRectangle = rectangle;

                        if (ballRectangle.intersects(brickRectangle)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPosX + 19 <= brickRectangle.x || ballPosX + 1 >= brickRectangle.x + brickRectangle.width) {
                                ballXDir = -ballXDir;
                            } else {
                                ballYDir = -ballYDir;
                            }

                            break BallHitsBrick;
                        }
                    }
                }

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
    /*Pallet movement*/
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX > 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 0) {
                playerX = 0;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                playerX = 310;
                score = 0;
                map = new LevelGenerator(brickRow, brickCol);
                totalBricks = brickRow * brickCol;

                repaint();
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

