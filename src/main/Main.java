package main;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main {
    public static JFrame window = new JFrame();

    public static void main(String[] args) {
        GamePanel gp = new GamePanel();
        //WINDOW
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setResizable(false);
        window.setTitle("Raven's Hollow");
        window.setIconImage(null);
        window.setLocation(0,0);
        window.setMinimumSize(new Dimension(480, 320));
        //window.setPreferredSize(new Dimension(1280, 720));

    //GAMEPANEL SETUP
        window.add(gp);
        gp.requestFocus();
        gp.setupGame();
        gp.startGameThread();

        window.pack();
        window.setVisible(true);

        //Credits KaupenJoe, John Waskom, John Peterson, RyiSnow, Gh0stlyCy80rg, Rocket Smith, Josh Name, and most of all, Sarah Warren <3
    }
}