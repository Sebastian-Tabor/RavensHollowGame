package main;

import entity.Entity;

public class CollisionCheck {
    GamePanel gp;
    public CollisionCheck (GamePanel gp) {
        this.gp = gp;

    }
    public void checkTile (Entity entity) {
        int iEntityLeftWorldX = entity.iWorldX + entity.hitBox.x;
        int iEntityRightWorldX = entity.iWorldX + entity.hitBox.x + entity.hitBox.width;
        int iEntityTopWorldY = entity.iWorldY + entity.hitBox.y;
        int iEntityBottomWorldY = entity.iWorldY + entity.hitBox.y + entity.hitBox.height;

        int iEntityLeftCol = iEntityLeftWorldX/gp.iTileSize;
        int iEntityRightCol = iEntityRightWorldX/gp.iTileSize;
        int iEntityTopRow = iEntityTopWorldY/gp.iTileSize;
        int iEntityBottomRow = iEntityBottomWorldY/gp.iTileSize;
        int iTileNum1, iTileNum2, iTileNum3;

        switch (entity.direction){
            case "jump":
                iEntityTopRow = (iEntityTopWorldY - entity.iSpeed)/gp.iTileSize;
                iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityTopRow];
                iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityTopRow];
                if (gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision){
                    entity.bCollisionOn = true;
                }
                break;
            case "crouch", "idle":
                iEntityBottomRow = (iEntityBottomWorldY + entity.iSpeed)/gp.iTileSize;
                iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityBottomRow];
                iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityBottomRow];
                if (gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision){
                    entity.bCollisionOn = true;
                    entity.bCollisionFloor = true;
                }
                break;
            case "left":
                iEntityLeftCol = (iEntityLeftWorldX - entity.iSpeed)/gp.iTileSize;
                iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityTopRow];
                iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityBottomRow];
                if (gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision){
                    entity.bCollisionOn = true;
                }
                break;
            case "right":
                iEntityRightCol = (iEntityRightWorldX + entity.iSpeed)/gp.iTileSize;
                iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityTopRow];
                iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityBottomRow];
                if (gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision){
                    entity.bCollisionOn = true;
                }
                break;
            case "right jump":
                iEntityLeftCol = (iEntityLeftWorldX - entity.iSpeed)/gp.iTileSize;
                iEntityRightCol = (iEntityRightWorldX + entity.iSpeed)/gp.iTileSize;
                iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityTopRow];
                iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityBottomRow];
                iTileNum3 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityTopRow];
                if (gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision || gp.tileManager.tile[iTileNum3].collision){
                    entity.bCollisionOn = true;
                }
                break;
            case "left jump":
                iEntityLeftCol = (iEntityLeftWorldX - entity.iSpeed)/gp.iTileSize;
                iEntityRightCol = (iEntityRightWorldX + entity.iSpeed)/gp.iTileSize;
                iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityTopRow];
                iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityBottomRow];
                iTileNum3 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityTopRow];
                if (gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision || gp.tileManager.tile[iTileNum3].collision){
                    entity.bCollisionOn = true;
                }
                break;
        }
    }
}
