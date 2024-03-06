package main;

import entity.Player;
import object.Object;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
//Frame setting vars
    public final int iOriginalTileSize = 64;
    public final int iScale = 1;
    public final int iTileSize = iOriginalTileSize * iScale;
    public final int iMaxScreenColumns = 30;
    public final int iMaxScreenRows = 22;
    public final int iScreenWidth = iMaxScreenColumns * iTileSize;
    public final int iScreenHeight = iMaxScreenRows * iTileSize;

    //Map size
    public final int iMaxMapCol = 90;
    public final int iMaxMapRow = 22;
    public final int iMapHeight = iTileSize * iMaxMapCol;
    public final int iMapWidth = iTileSize * iMaxMapRow;
    TileManager tileManager = new TileManager(this);
    KeyBinds keyBinds = new KeyBinds();
    Thread gameThread;
//CLASS OBJECT CREATION
    public CollisionCheck cCheck = new CollisionCheck(this);
    public ObjectSetter oSetter = new ObjectSetter(this);
    public Player player = new Player(this, keyBinds);
    public Object[] obj = new Object[10];

    //FPS
    int iFPS = 60;

    //Keybind objects
    public GamePanel() {
        this.setPreferredSize(new Dimension(iScreenWidth,iScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyBinds);
        this.setFocusable(true);
        this.setAlignmentX(0);
        this.setAlignmentY(0);
    }
    public void setupGame() {
        oSetter.setObject();
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
            try {
                double dRemainingTimeMS = (dNextDrawTimeNS - System.nanoTime())/1000000;
                Thread.sleep((long) dRemainingTimeMS);
                if(dRemainingTimeMS < 0) dRemainingTimeMS = 0;

                dNextDrawTimeNS += dDrawIntervalNS;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update() {
        player.update();
        for (Object object : obj) {
            if (object != null) {
                object.update();
            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        for (Object object : obj) {
            if (object != null) {
                object.draw(g2, this);
            }
        }
        player.draw(g2);
        g2.dispose();
    }
}
