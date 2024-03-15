package main;

import entity.Entity;

import java.awt.*;

public class CollisionCheck {
    GamePanel gp;
    public CollisionCheck (GamePanel gp) {
        this.gp = gp;

    }
    public void checkTile(Entity entity) {

        int iEntLeftWorldX = entity.iWorldX + entity.hitBox.x;
        int iEntRightWorldX = entity.iWorldX + entity.hitBox.x + entity.hitBox.width;
        int iEntTopWorldY = entity.iWorldY + entity.hitBox.y;
        int iEntBotWorldY = entity.iWorldY + entity.hitBox.y + entity.hitBox.height;
        int iEntityHLeftCol = (iEntLeftWorldX - entity.iSenseRange) / gp.iTileSize;
        int iEntityHRightCol = (iEntRightWorldX + entity.iSenseRange) / gp.iTileSize;
        int iEntityHTopRow = iEntTopWorldY / gp.iTileSize;
        int iEntityHBottomRow = iEntBotWorldY / gp.iTileSize;
        int iEntityVLeftCol = iEntLeftWorldX / gp.iTileSize;
        int iEntityVRightCol = iEntRightWorldX / gp.iTileSize;
        int iEntityVTopRow = (iEntTopWorldY - entity.iSenseRange) / gp.iTileSize;
        int iEntityVBottomRow = (iEntBotWorldY + entity.iSenseRange) / gp.iTileSize;
        int iTileNum1, iTileNum2, iTileNum3, iTileNum4, iTileNum5, iTileNum6, iTileNum7, iTileNum8;

        iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityHLeftCol][iEntityHTopRow];
        iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityHRightCol][iEntityHTopRow];
        iTileNum3 = gp.tileManager.iiMapTileNumber[iEntityHLeftCol][iEntityHBottomRow];
        iTileNum4 = gp.tileManager.iiMapTileNumber[iEntityHRightCol][iEntityHBottomRow];
        iTileNum5 = gp.tileManager.iiMapTileNumber[iEntityVLeftCol][iEntityVTopRow];
        iTileNum6 = gp.tileManager.iiMapTileNumber[iEntityVRightCol][iEntityVTopRow];
        iTileNum7 = gp.tileManager.iiMapTileNumber[iEntityVLeftCol][iEntityVBottomRow];
        iTileNum8 = gp.tileManager.iiMapTileNumber[iEntityVRightCol][iEntityVBottomRow];

        entity.bCollisionLeft = gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum3].collision;
        entity.bCollisionRight = gp.tileManager.tile[iTileNum2].collision || gp.tileManager.tile[iTileNum4].collision;
        entity.bCollisionTop = gp.tileManager.tile[iTileNum5].collision || gp.tileManager.tile[iTileNum6].collision;
        entity.bCollisionBottom = gp.tileManager.tile[iTileNum7].collision || gp.tileManager.tile[iTileNum8].collision;
        entity.bCollisionDetected = entity.bCollisionLeft || entity.bCollisionRight || entity.bCollisionTop || entity.bCollisionBottom;

        int iStuckLeftCol = (iEntLeftWorldX + 1) / gp.iTileSize;
        int iStuckRightCol = (iEntRightWorldX - 1) / gp.iTileSize;
        int iStuckTopRow = (iEntTopWorldY + 1)/ gp.iTileSize;
        int iStuckBottomRow = (iEntBotWorldY - 1)/ gp.iTileSize;

        int iStuckTileNum1, iStuckTileNum2, iStuckTileNum3, iStuckTileNum4;

        iStuckTileNum1 = gp.tileManager.iiMapTileNumber[iStuckLeftCol][iStuckTopRow];
        iStuckTileNum2 = gp.tileManager.iiMapTileNumber[iStuckRightCol][iStuckTopRow];
        iStuckTileNum3 = gp.tileManager.iiMapTileNumber[iStuckLeftCol][iStuckBottomRow];
        iStuckTileNum4 = gp.tileManager.iiMapTileNumber[iStuckRightCol][iStuckBottomRow];

        entity.bStuckLeft = gp.tileManager.tile[iStuckTileNum1].collision && gp.tileManager.tile[iStuckTileNum3].collision;
        entity.bStuckRight = gp.tileManager.tile[iStuckTileNum2].collision && gp.tileManager.tile[iStuckTileNum4].collision;
        entity.bStuckTop = gp.tileManager.tile[iStuckTileNum1].collision && gp.tileManager.tile[iStuckTileNum2].collision;
        entity.bStuckBot = gp.tileManager.tile[iStuckTileNum3].collision && gp.tileManager.tile[iStuckTileNum4].collision;
    }

    public int checkObject(Entity entity, boolean player) {
         int index = 999;
         for (int i = 0; i < gp.obj.length; i++) {
             if (gp.obj[i] != null) {

                 entity.hitBox.x = entity.iWorldX + entity.hitBox.x;
                 entity.hitBox.y = entity.iWorldY + entity.hitBox.y;

                 gp.obj[i].hitBox.x = gp.obj[i].iWorldX + gp.obj[i].hitBox.x;
                 gp.obj[i].hitBox.y = gp.obj[i].iWorldY + gp.obj[i].hitBox.y;

                 entity.hitBoxLeftSense = new Rectangle(entity.hitBox.x - entity.iSpeed, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
                 entity.hitBoxRightSense = new Rectangle(entity.hitBox.x + entity.iSpeed, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
                 entity.hitBoxTopSense = new Rectangle(entity.hitBox.x, entity.hitBox.y - entity.iSpeed, entity.hitBox.width, entity.hitBox.height);
                 entity.hitBoxBotSense = new Rectangle(entity.hitBox.x, entity.hitBox.y + entity.iSpeed, entity.hitBox.width, entity.hitBox.height);

                 if (entity.hitBoxLeftSense.intersects(gp.obj[i].hitBox) || entity.hitBoxRightSense.intersects(gp.obj[i].hitBox) || entity.hitBoxTopSense.intersects(gp.obj[i].hitBox) || entity.hitBoxBotSense.intersects(gp.obj[i].hitBox)){
                     if (player) index = i;
                 }
                 entity.hitBox.x = entity.iHitBoxDefaultX;
                 entity.hitBox.y = entity.iHitBoxDefaultY;
                 gp.obj[i].hitBox.x = gp.obj[i].iHitBoxDefaultX;
                 gp.obj[i].hitBox.y = gp.obj[i].iHitBoxDefaultY;
             }
         }
         return index;
    }
    public int distance(int x1, int y1, int x2, int y2){
        return (int) Math.sqrt((double)(((y2/x2)^2)+(y1/x1)^2));
    }
}
