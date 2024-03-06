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
    }
}
