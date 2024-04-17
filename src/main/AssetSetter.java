package main;

import entity.DummyEntity;
import entity.HareEntity;
import object.FeatherObject;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject() {

         gp.obj[0] = new FeatherObject(gp);
         gp.obj[0].iWorldX = 18 * gp.iTileSize;
         gp.obj[0].iWorldY = 10 * gp.iTileSize;
         gp.obj[0].bStorable = true;

    }
    public void setNPC() {
        gp.npc[0] = new HareEntity(gp);
        gp.npc[0].name = "Carver";
        gp.npc[0].startWorldX = gp.iTileSize * 18;
        gp.npc[0].startWorldY = gp.iTileSize * 10;
        gp.npc[0].worldX = gp.npc[0].startWorldX;
        gp.npc[0].worldY = gp.npc[0].startWorldY;
        gp.npc[0].alive = true;
    }
    public void setMonster() {
        gp.monster[0] = new DummyEntity(gp);
        gp.monster[0].name = "Dummy";
        gp.monster[0].startWorldX = gp.iTileSize * 19;
        gp.monster[0].startWorldY = gp.iTileSize * 11;
        gp.monster[0].worldX = gp.monster[0].startWorldX;
        gp.monster[0].worldY = gp.monster[0].startWorldY;
        gp.monster[0].value = 5;
        gp.monster[0].alive = true;
    }
    public void resetMonsterPos(){
        for (int i = 0; i < gp.monster.length; i++) {
            if (gp.monster[i] != null) {
                gp.monster[i].worldX = gp.monster[i].startWorldX;
                gp.monster[i].worldY = gp.monster[i].startWorldY;
                gp.monster[i].health = gp.monster[i].healthMax;
            }
        }
    }
    public void resetNPCPos(){
        for (int i = 0; i < gp.npc.length; i++) {
            if (gp.npc[i] != null) {
                gp.npc[i].worldX = gp.npc[i].startWorldX;
                gp.npc[i].worldY = gp.npc[i].startWorldY;
                gp.npc[i].health = gp.npc[i].healthMax;
            }
        }
    }
    public void resetPlayerPos(){
        gp.player.worldX = gp.player.iStartPosX;
        gp.player.worldY = gp.player.iStartPosY;
    }
}
