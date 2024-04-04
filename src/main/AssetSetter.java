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
        gp.npc[0].iWorldX = gp.iTileSize * 18;
        gp.npc[0].iWorldY = gp.iTileSize * 10;
    }
    public void setMonster() {
        gp.monster[0] = new Dummy(gp);
        gp.monster[0].sName = "Dummy";
        gp.monster[0].iWorldX = gp.iTileSize * 19;
        gp.monster[0].iWorldY = gp.iTileSize * 11;
    }
}
