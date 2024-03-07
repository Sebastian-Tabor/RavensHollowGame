package main;

import object.Feather;

public class ObjectSetter {
    GamePanel gp;

    public ObjectSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject() {

         gp.obj[0] = new Feather();
         gp.obj[0].iWorldX = 10 * gp.iTileSize;
         gp.obj[0].iWorldY = 10 * gp.iTileSize;
         gp.obj[0].bStorable = true;

        gp.obj[1] = new Feather();
        gp.obj[1].iWorldX = 11 * gp.iTileSize;
        gp.obj[1].iWorldY = 10 * gp.iTileSize;
        gp.obj[1].bStorable = true;

        gp.obj[2] = new Feather();
        gp.obj[2].iWorldX = 11 * gp.iTileSize;
        gp.obj[2].iWorldY = 12 * gp.iTileSize;
        gp.obj[2].bStorable = true;

        gp.obj[3] = new Feather();
        gp.obj[3].iWorldX = 10 * gp.iTileSize;
        gp.obj[3].iWorldY = 12 * gp.iTileSize;
        gp.obj[3].bStorable = true;

        gp.obj[4] = new Feather();
        gp.obj[4].iWorldX = 15 * gp.iTileSize;
        gp.obj[4].iWorldY = 13 * gp.iTileSize;
        gp.obj[4].bStorable = true;
    }
}
