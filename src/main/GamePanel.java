package main;

import entity.Character;
import entity.Player;
import main.KeyBinds;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //Frame setting vars
    public final int iOriginalTileSize = 120;
    public final int iScale = 1;
    public final int iTileSize = iOriginalTileSize * iScale;
    public final int iMaxScreenColumns = 1920;
    public final int iMaxScreenRows = 1080;
    public final int iScreenWidth = iMaxScreenColumns * iTileSize;
    public final int iScreenHeight = iMaxScreenRows * iTileSize;
    KeyBinds keyBinds = new KeyBinds();
    Thread gameThread;
    Player player = new Player(this, keyBinds);
    //FPS
    int iFPS = 60;
    //Keybind objects
    public GamePanel() {
        this.setPreferredSize(new Dimension(iScreenWidth,iScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyBinds);
        this.setFocusable(true);
    }
    public void startGameThread(){gameThread = new Thread(this);gameThread.start();}

    @Override
    public void run() {
        double dDrawIntervalNS = 1000000000/((double)iFPS);
        double dNextDrawTimeNS = System.nanoTime() + dDrawIntervalNS;
        while(gameThread != null){
            long runTime = System.nanoTime();
            //Update
            update();
            //Draw
            repaint();
            try {double dRemainingTimeMS = (dNextDrawTimeNS - System.nanoTime())/1000000; Thread.sleep((long) dRemainingTimeMS); if(dRemainingTimeMS < 0){dRemainingTimeMS = 0;} dNextDrawTimeNS += dDrawIntervalNS;}
            catch (InterruptedException e) {throw new RuntimeException(e);}
        }
    }
    public void update() {
        player.update();
    }
    public void paintComponent(Graphics g){
        System.out.println("Drawing");
        super.paintComponent(g);
        //repainting Player character on each loop
        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }

}
