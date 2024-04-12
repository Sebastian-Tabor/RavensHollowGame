package main;

import entity.Dummy;
import entity.Hare;
import object.Feather;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject() {

         gp.obj[0] = new Feather(gp);
         gp.obj[0].iWorldX = 18 * gp.iTileSize;
         gp.obj[0].iWorldY = 10 * gp.iTileSize;
         gp.obj[0].bStorable = true;

    }
    public void setNPC() {
        gp.npc[0] = new Hare(gp);
        gp.npc[0].sName = "Carver";
        gp.npc[0].iStartWorldX = gp.iTileSize * 18;
        gp.npc[0].iStartWorldY = gp.iTileSize * 10;
        gp.npc[0].iWorldX = gp.npc[0].iStartWorldX;
        gp.npc[0].iWorldY = gp.npc[0].iStartWorldY;
        gp.npc[0].bAlive = true;
    }
    public void setMonster() {
        gp.monster[0] = new Dummy(gp);
        gp.monster[0].sName = "Dummy";
        gp.monster[0].iStartWorldX = gp.iTileSize * 19;
        gp.monster[0].iStartWorldY = gp.iTileSize * 11;
        gp.monster[0].iWorldX = gp.monster[0].iStartWorldX;
        gp.monster[0].iWorldY = gp.monster[0].iStartWorldY;
        gp.monster[0].bAlive = true;
    }
    public void resetMonsterPos(){
        for (int i = 0; i < gp.monster.length; i++) {
            if (gp.monster[i] != null) {
                gp.monster[i].iWorldX = gp.monster[i].iStartWorldX;
                gp.monster[i].iWorldY = gp.monster[i].iStartWorldY;
                gp.monster[i].iHealth = gp.monster[i].iHealthMax;
            }
        }
    }
    public void resetNPCPos(){
        for (int i = 0; i < gp.npc.length; i++) {
            if (gp.npc[i] != null) {
                gp.npc[i].iWorldX = gp.npc[i].iStartWorldX;
                gp.npc[i].iWorldY = gp.npc[i].iStartWorldY;
                gp.npc[i].iHealth = gp.npc[i].iHealthMax;
            }
        }
    }
    public void resetPlayerPos(){
        gp.player.iWorldX = gp.player.iStartPosX;
        gp.player.iWorldY = gp.player.iStartPosY;
    }
}
