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
        int iEntityHLeftCol = (iEntityLeftWorldX - entity.iSenseRange) / gp.iTileSize;
        int iEntityHRightCol = (iEntityRightWorldX + entity.iSenseRange) / gp.iTileSize;
        int iEntityHTopRow = iEntityTopWorldY / gp.iTileSize;
        int iEntityHBottomRow = iEntityBottomWorldY / gp.iTileSize;
        int iEntityVLeftCol = iEntityLeftWorldX / gp.iTileSize;
        int iEntityVRightCol = iEntityRightWorldX / gp.iTileSize;
        int iEntityVTopRow = (iEntityTopWorldY - entity.iSenseRange) / gp.iTileSize;
        int iEntityVBottomRow = (iEntityBottomWorldY + entity.iSenseRange) / gp.iTileSize;
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
        entity.bStuckTopLeft = gp.tileManager.tile[iTileNum1].collision && gp.tileManager.tile[iTileNum5].collision;
        entity.bStuckTopRight = gp.tileManager.tile[iTileNum2].collision && gp.tileManager.tile[iTileNum6].collision;
        entity.bStuckBotLeft = gp.tileManager.tile[iTileNum3].collision && gp.tileManager.tile[iTileNum7].collision;
        entity.bStuckBotRight = gp.tileManager.tile[iTileNum4].collision && gp.tileManager.tile[iTileNum8].collision;
    }
}
