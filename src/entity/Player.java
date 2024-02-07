package entity;
import main.GamePanel;
import main.KeyBinds;
import java.awt.*;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyBinds keyBinds;

    public Player(GamePanel gamePanel, KeyBinds keyBinds) {
        this.gamePanel = gamePanel;
        this.keyBinds = keyBinds;
    }
    public void setDefaultValues(){
        x = 100;
        y = 540;
        speed = 10;
    }
    public void update() {
        int iOldCharPosY = y;
        if (KeyBinds.bLeftPressed) {x -= speed;}
        if (KeyBinds.bRightPressed) {x += speed;}
        //if (keyBinds.bDownPressed == true) {iPlayerPosX += Player.getIvCharacterSpeed();}
        if (KeyBinds.bUpPressed) {y -= 2*speed ;}
        if (KeyBinds.bSpacePressed) {y -= 2*speed  ;}
        if (y > 540){y = 100;}
        if (y-iOldCharPosY >= 6*speed  ){y += speed   ;}
        if (y < 540){y += speed;}}
    public void draw(Graphics2D g2) {
        g2.setColor(Color.darkGray);
        g2.fillRect(x, y, GamePanel.iTileSize, GamePanel.iTileSize);
    }

}
