package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static  GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    public static void main(String[] args) {
        //Window and game thread
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(false);
        window.setResizable(true);
        window.setTitle("Raven's Hollow");
        window.setIconImage(null);
        window.setLocation(0,0);
        window.setMinimumSize(new Dimension(640, 480));


        //Adding GamePanel
        GamePanel gp = new GamePanel();
        window.add(gp);
        gp.startGameThread();
        gp.requestFocus();


        window.pack();
        window.setVisible(true);
        //Credits KaupenJoe, John Waskom, John Peterson, RyiSnow, Gh0stlyCy80rg, Rocket Smith, Josh Name
    }
}