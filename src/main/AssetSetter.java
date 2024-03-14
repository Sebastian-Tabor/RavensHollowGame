package main;

import entity.Hare;
import object.Feather;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject() {

         gp.obj[0] = new Feather(gp);
         gp.obj[0].iWorldX = 10 * gp.iTileSize;
         gp.obj[0].iWorldY = 10 * gp.iTileSize;
         gp.obj[0].bStorable = true;

        gp.obj[1] = new Feather(gp);
        gp.obj[1].iWorldX = 11 * gp.iTileSize;
        gp.obj[1].iWorldY = 10 * gp.iTileSize;
        gp.obj[1].bStorable = true;

        gp.obj[2] = new Feather(gp);
        gp.obj[2].iWorldX = 11 * gp.iTileSize;
        gp.obj[2].iWorldY = 12 * gp.iTileSize;
        gp.obj[2].bStorable = true;

        gp.obj[3] = new Feather(gp);
        gp.obj[3].iWorldX = 10 * gp.iTileSize;
        gp.obj[3].iWorldY = 12 * gp.iTileSize;
        gp.obj[3].bStorable = true;

        gp.obj[4] = new Feather(gp);
        gp.obj[4].iWorldX = 15 * gp.iTileSize;
        gp.obj[4].iWorldY = 13 * gp.iTileSize;
        gp.obj[4].bStorable = true;
    }
    public void setNPC() {
        gp.npc[0] = new Hare(gp);
        gp.npc[0].iWorldX = gp.iTileSize * 18;
        gp.npc[0].iWorldY = gp.iTileSize * 11;
    }
    public void setProjectile() {

    }
}
