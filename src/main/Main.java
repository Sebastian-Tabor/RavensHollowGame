package main;


import javax.swing.*;
import java.awt.*;

public class Main {
    public static JFrame window = new JFrame();
    public static void main(String[] args) {
        //WINDOW
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.setResizable(false);
        window.setTitle("Raven's Hollow");
        window.setIconImage(null);
        window.setLocation(0,0);
        window.setMinimumSize(new Dimension(640, 480));

        //GAMEPANEL SETUP
        GamePanel gp = new GamePanel();
        window.add(gp);
        gp.requestFocus();
        gp.setupGame();
        gp.startGameThread();

        window.pack();
        window.setVisible(true);

        //Credits KaupenJoe, John Waskom, John Peterson, RyiSnow, Gh0stlyCy80rg, Rocket Smith, Josh Name, and most of all, Sarah Warren <3
    }
    public static void setFullscreen(GamePanel gp){
        window.setUndecorated(true);
        window.setResizable(false);
        gp.bFullscreen = true;
    }
    public static void setWindowed(GamePanel gp){
        window.setUndecorated(false);
        window.setResizable(true);
        gp.bFullscreen = false;
    }
}