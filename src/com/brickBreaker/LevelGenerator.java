package com.brickBreaker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class LevelGenerator {
    protected int[][] map;
    protected int brickWidth;
    protected int brickHeight;
    protected int mapCol;
    protected int mapRow;

    private BufferedImage brickImg;

    public LevelGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }
        mapCol = col;
        mapRow = row;
        try {
            brickImg = ImageIO.read(new File("src/brick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void draw(Graphics2D graphics2D, int brickWidth, int brickHeight) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                  /*graphics2D.setColor(Color.blue);
                    graphics2D.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);*/
                    graphics2D.drawImage(brickImg, j * brickWidth / (mapCol) + 30, i * brickHeight / (mapRow*6) + 50, brickWidth*4/100 + brickWidth / (mapCol*2) , brickHeight / (mapRow*6)  - 10, null);


                    //graphics2D.setStroke(new BasicStroke(3));
                    //graphics2D.setColor(Color.black);
                    //graphics2D.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    protected void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}
