package com.brickBreaker;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        /*Adding the game window and setting window attributes*/

        JFrame window = new JFrame();
        GameMovement gameMovement = new GameMovement();

        window.setBounds(550, 200, 700, 600);
        window.setTitle("Brick Breaker");
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(gameMovement);    // GameMovement object will run the game into the created JFrame

        //Won't work for jar file.
        ImageIcon imageIcon = new ImageIcon("src/18-01.png");
        window.setIconImage(imageIcon.getImage());
    }
}
