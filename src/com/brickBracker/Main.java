package com.brickBracker;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        /*Adding the game window and setting window attributes*/

        JFrame window = new JFrame();
        Gameplay gameplay = new Gameplay();

        window.setBounds(10, 10, 700, 600);
        window.setTitle("Brick Breaker");
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(gameplay);    // Gameplay object will run the game into the created JFrame

    }
}
