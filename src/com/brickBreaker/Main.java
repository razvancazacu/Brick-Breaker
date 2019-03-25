package com.brickBreaker;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        /*Adding the game window and setting window attributes*/

        JFrame window = new JFrame();
        GameMovement gameMovement = new GameMovement();

        window.setBounds(1, 1, 1920, 1080);
        window.setTitle("Brick Breaker");
        window.setResizable(true);

        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(gameMovement);    // GameMovement object will run the game into the created JFrame

        //Won't work for jar file.
        ImageIcon imageIcon = new ImageIcon("src/18-01.png");
        window.setIconImage(imageIcon.getImage());
    }
}
