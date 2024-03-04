package main;

import entity.Entity;

public class CollisionCheck {
    GamePanel gp;
    public CollisionCheck (GamePanel gp) {
        this.gp = gp;

    }
    public void checkCollision(Entity entity) {
        int iEntityLeftWorldX = entity.iWorldX + entity.hitBox.x;
        int iEntityRightWorldX = entity.iWorldX + entity.hitBox.x + entity.hitBox.width;
        int iEntityTopWorldY = entity.iWorldY + entity.hitBox.y;
        int iEntityBottomWorldY = entity.iWorldY + entity.hitBox.y + entity.hitBox.height;
        int iEntityLeftCol = (iEntityLeftWorldX - entity.iSenseRange)/gp.iTileSize;
        int iEntityRightCol = (iEntityRightWorldX + entity.iSenseRange)/gp.iTileSize;
        int iEntityTopRow = (iEntityTopWorldY - entity.iSenseRange)/gp.iTileSize;
        int iEntityBottomRow = (iEntityBottomWorldY + entity.iSenseRange)/gp.iTileSize;
        int iTileNum1, iTileNum2, iTileNum3, iTileNum4;


        iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityTopRow];
        iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityTopRow];
        iTileNum3 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityBottomRow];
        iTileNum4 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityBottomRow];

        entity.bCollisionLeft = gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum3].collision;
        entity.bCollisionRight = gp.tileManager.tile[iTileNum2].collision || gp.tileManager.tile[iTileNum4].collision;
        entity.bCollisionTop = gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision;
        entity.bCollisionBottom = gp.tileManager.tile[iTileNum2].collision || gp.tileManager.tile[iTileNum4].collision;
        entity.bCollisionFloor = gp.tileManager.tile[iTileNum3].collision && gp.tileManager.tile[iTileNum4].collision;
    }
}
