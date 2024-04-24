package main;

import java.awt.*;
public class Transitions {

    GamePanel gp;
    public int transitionState;

    public Transitions(GamePanel gp) {
        this.gp = gp;
    }
    public void draw(Graphics2D g2){

        if (gp.transitionIn) {
            g2.setColor(new Color(0,0,0, gp.transitionCounter));
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
            gp.transitionCounter += 5;
            if (gp.transitionCounter == 255) {
                gp.transitionIn = false;
                gp.transitionOut = true;
                gp.transitionCounter = 0;
                transition(transitionState);
            }
        }
        if (gp.transitionOut) {
            g2.setColor(new Color(0,0,0, 255-gp.transitionCounter));
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);
            gp.transitionCounter += 5;
            if (gp.transitionCounter == 255) {
                gp.transitionOut = false;
                gp.transitionCounter = 0;
            }
        }
    }
    public void transition(int gameState){
        if (!gp.transitionIn && gp.transitionOut) {
            gp.gameState = gameState;
            gp.stopMusic();
            gp.playMusic(gameState);

        }
    }
}
