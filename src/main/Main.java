package main;

import entity.Player;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //Window and game thread
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Raven's Hollow");
        window.setIconImage(null);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.startGameThread();

        //Credits KaupenJoe, John Waskom, John Peterson, RyiSnow
    }
}