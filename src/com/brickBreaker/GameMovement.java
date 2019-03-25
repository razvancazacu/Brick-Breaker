package com.brickBreaker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*  Class that implements the game
 *  it extends a JPanel so it can be inserted into the JFrame */
public class GameMovement extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks;
    private int brickCol;
    private int brickRow;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -2;
    private int ballYDir = -3;

    private LevelGenerator map;

    private BufferedImage paddleImg;
    private BufferedImage ballImg;
    private BufferedImage bgImg;

    /* Constructor for starting the game.
     * Register the GameMovement object to the KeyListener interface,
     * change the focus to this JPanel,
     * disable the traversal keys : tab, shift + tab, etc */
    public GameMovement() {
        brickRow = 4;
        brickCol = 8;
        map = new LevelGenerator(brickRow, brickCol);
        totalBricks = brickRow * brickCol;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

        try {
            paddleImg = ImageIO.read(new File("src/paddle.png"));
            ballImg = ImageIO.read(new File("src/ball.png"));
            bgImg = ImageIO.read(new File("src/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        graphics.fillRect(0, 0, super.getWidth(), super.getHeight());
        graphics.drawImage(bgImg, 0, 0, super.getWidth(), super.getHeight(), null);


//        // borders
//        graphics.setColor(Color.yellow);
//        graphics.fillRect(0, 0, 3, 592);
//        graphics.fillRect(0, 0, 692, 3);
//        graphics.fillRect(681, 0, 3, 592);

        // drawing the map
        map.draw((Graphics2D) graphics, getWidth(), getHeight());

        // score
        graphics.setColor(Color.white);
        graphics.setFont(new Font("Arial", Font.BOLD, 25));
        graphics.drawString("" + score, 15, 30);

        // paddle
       /* graphics.setColor(Color.green);
        graphics.fillRect(playerX, 550, 100, 8);*/
        graphics.drawImage(paddleImg, playerX, super.getHeight() - super.getWidth() / 40, super.getWidth() / 6, super.getWidth() / 40, null);


        // ball
        /*graphics.setColor(Color.red);
        graphics.fillOval(ballPosX, ballPosY, 20, 20);*/
        graphics.drawImage(ballImg, ballPosX, ballPosY, super.getWidth() / 40, super.getWidth() / 40, null);


        if (totalBricks <= 0) {
            restartGame(graphics);
            graphics.drawString("You Win Score: " + score, 210, 300);

            graphics.setFont(new Font("Arial", Font.BOLD, 30));
            graphics.drawString("Press Enter to Restart", 220, 350);
        }
        if (ballPosY > super.getHeight()) {
            restartGame(graphics);
            graphics.drawString("Game Over - Score: " + score, 10, 300);

            graphics.setFont(new Font("Arial", Font.BOLD, 30));
            graphics.drawString("Press Enter to Restart", 10, 350);

        }

        graphics.dispose();
    }

    private void restartGame(Graphics graphics) {
        play = false;
        ballXDir = 0;
        ballYDir = 0;
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 30));
    }

    @Override
    /* On action performed check if the ball reached window boundaries or pallet
     * Change it's movement based on that
     * recall the paint function */
    public void actionPerformed(ActionEvent e) {

        timer.start();

        if (play) {
            if (new Rectangle(ballPosX, ballPosY, super.getWidth() / 40, super.getWidth() / 40)
                    .intersects(new Rectangle(playerX, super.getHeight() - super.getWidth() / 44, super.getWidth() / 6, super.getWidth() / 40))) {
                ballYDir = -ballYDir;
            }

            BallHitsBrick:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * getWidth() / (map.mapCol) + 30;
                        int brickY = i * getHeight() / (map.mapRow*6) + 50;
                        int brickWidth = getWidth()*4/100 + getWidth() / (map.mapCol*2) ;
                        int brickHeight = getHeight() / (map.mapRow*6)  - 10;

                        Rectangle rectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRectangle = new Rectangle(ballPosX, ballPosY, super.getWidth() / 40, super.getWidth() / 40);
                        Rectangle brickRectangle = rectangle;

                        if (ballRectangle.intersects(brickRectangle)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballPosX + super.getWidth() / 40 <= brickRectangle.x || ballPosX + 1 >= brickRectangle.x + brickRectangle.width) {
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
            if (ballPosX > getWidth()) {
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
            if (playerX <= super.getWidth() - super.getWidth() / 6) {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX >= 0) {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                restartGame();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            restartGame();
        }
    }

    private void restartGame() {
        play=true;
        ballPosX = 120;
        ballPosY = 350;
        ballXDir = -1;
        ballYDir = -3;
        playerX = getWidth()/2;
        score = 0;
        map = new LevelGenerator(brickRow, brickCol);
        totalBricks = brickRow * brickCol;

        repaint();
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

