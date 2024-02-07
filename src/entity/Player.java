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
        if (keyBinds.bLeftPressed) {x -= speed;}
        if (keyBinds.bRightPressed) {x += speed;}
        if (keyBinds.bUpPressed) {y -= 2*speed ;}
        if (keyBinds.bSpacePressed) {y -= 2*speed  ;}
        if (y > 540){y = 100;}
        if (y-iOldCharPosY >= 6*speed  ){y += speed   ;}
        if (y < 540){y += speed;}}
    public void draw(Graphics2D g2) {
        g2.setColor(Color.darkGray);
        g2.fillRect(x, y, gamePanel.iTileSize, gamePanel.iTileSize);
    }

}
