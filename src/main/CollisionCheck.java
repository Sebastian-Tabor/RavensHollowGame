package main;

import entity.SuperEntity;

import java.awt.*;

public class CollisionCheck {
    GamePanel gp;
    public CollisionCheck (GamePanel gp) {
        this.gp = gp;

    }
    public void checkTile(SuperEntity entity) {

        int iEntLeftWorldX = entity.iWorldX + entity.hitBox.x;
        int iEntRightWorldX = entity.iWorldX + entity.hitBox.x + entity.hitBox.width;
        int iEntTopWorldY = entity.iWorldY + entity.hitBox.y;
        int iEntBotWorldY = entity.iWorldY + entity.hitBox.y + entity.hitBox.height;
        int iEntityLeftCol;
        int iEntityRightCol;
        int iEntityTopRow = iEntTopWorldY / gp.iTileSize;
        int iEntityBottomRow = iEntBotWorldY / gp.iTileSize;
        int iTileNum1, iTileNum2, iTileNum3, iTileNum4, iTileNum5, iTileNum6;

        iEntityLeftCol = (iEntLeftWorldX) / gp.iTileSize;
        iEntityRightCol = (iEntRightWorldX) / gp.iTileSize;
        iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityBottomRow];
        iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityBottomRow];

        entity.bWouldBeStuck = gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision;

        iEntityLeftCol = (iEntLeftWorldX - entity.iSpeedOriginal) / gp.iTileSize;
        iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityTopRow];
        iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityBottomRow];

        entity.bCollisionLeft = gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision;

        iEntityRightCol = (iEntRightWorldX + entity.iSpeedOriginal) / gp.iTileSize;
        iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityTopRow];
        iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityBottomRow];

        entity.bCollisionRight = gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision;

        iEntityLeftCol = (iEntLeftWorldX) / gp.iTileSize;
        iEntityRightCol = (iEntRightWorldX) / gp.iTileSize;
        iEntityTopRow = (iEntTopWorldY - entity.iSpeedOriginal) / gp.iTileSize;
        iEntityBottomRow = (iEntBotWorldY + entity.iSpeedOriginal) / gp.iTileSize;
        iTileNum3 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityTopRow];
        iTileNum4 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityTopRow];
        iTileNum5 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityBottomRow];
        iTileNum6 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityBottomRow];

        entity.bCollisionTop = gp.tileManager.tile[iTileNum3].collision || gp.tileManager.tile[iTileNum4].collision;
        entity.bCollisionBottom = gp.tileManager.tile[iTileNum5].collision || gp.tileManager.tile[iTileNum6].collision;

    }

    public int checkObject(SuperEntity entity, boolean player) {
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
    public int checkEntity(SuperEntity entity, SuperEntity[] target ) {
        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                entity.hitBox.x = entity.iWorldX + entity.hitBox.x;
                entity.hitBox.y = entity.iWorldY + entity.hitBox.y;

                target[i].hitBox.x = target[i].iWorldX + target[i].hitBox.x;
                target[i].hitBox.y = target[i].iWorldY + target[i].hitBox.y;

                entity.hitBoxLeftSense = new Rectangle(entity.hitBox.x - entity.iSpeed, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
                entity.hitBoxRightSense = new Rectangle(entity.hitBox.x + entity.iSpeed, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
                entity.hitBoxTopSense = new Rectangle(entity.hitBox.x, entity.hitBox.y - entity.iSpeed, entity.hitBox.width, entity.hitBox.height);
                entity.hitBoxBotSense = new Rectangle(entity.hitBox.x, entity.hitBox.y + entity.iSpeed, entity.hitBox.width, entity.hitBox.height);

                if (entity.hitBoxLeftSense.intersects(target[i].hitBox) || entity.hitBoxRightSense.intersects(target[i].hitBox) || entity.hitBoxTopSense.intersects(target[i].hitBox) || entity.hitBoxBotSense.intersects(target[i].hitBox)){
                    if (target[i] != entity) {
                        entity.bCollisionDetected = true;
                        index = i;
                    }
                }

                entity.hitBox.x = entity.iHitBoxDefaultX;
                entity.hitBox.y = entity.iHitBoxDefaultY;
                target[i].hitBox.x = target[i].iHitBoxDefaultX;
                target[i].hitBox.y = target[i].iHitBoxDefaultY;
            }
        }
        return index;
    }
    public void checkPlayer(SuperEntity entity) {

        entity.hitBox.x = entity.iWorldX + entity.hitBox.x;
        entity.hitBox.y = entity.iWorldY + entity.hitBox.y;
        gp.player.hitBox.x = gp.player.iWorldX + gp.player.hitBox.x;
        gp.player.hitBox.y = gp.player.iWorldY + gp.player.hitBox.y;
        entity.hitBoxLeftSense = new Rectangle(entity.hitBox.x - entity.iSpeed, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
        entity.hitBoxRightSense = new Rectangle(entity.hitBox.x + entity.iSpeed, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
        entity.hitBoxTopSense = new Rectangle(entity.hitBox.x, entity.hitBox.y - entity.iSpeed, entity.hitBox.width, entity.hitBox.height);
        entity.hitBoxBotSense = new Rectangle(entity.hitBox.x, entity.hitBox.y + entity.iSpeed, entity.hitBox.width, entity.hitBox.height);

        if (entity.hitBoxLeftSense.intersects(gp.player.hitBox) || entity.hitBoxRightSense.intersects(gp.player.hitBox) || entity.hitBoxTopSense.intersects(gp.player.hitBox) || entity.hitBoxBotSense.intersects(gp.player.hitBox)){
            entity.bCollisionDetected = true;
        }
        entity.hitBox.x = entity.iHitBoxDefaultX;
        entity.hitBox.y = entity.iHitBoxDefaultY;
        gp.player.hitBox.x = gp.player.iHitBoxDefaultX;
        gp.player.hitBox.y = gp.player.iHitBoxDefaultY;
    }
    public int checkIf_Hit_(SuperEntity entity, SuperEntity[] target) {
        int index = 999;

        entity.attackBox.y = entity.iWorldY;
        entity.attackBox.width = 2*entity.hitBox.width;
        entity.attackBox.height = entity.hitBox.height;

        if (entity.facing.equals("left")) {
            entity.attackBox.x = entity.iWorldX - entity.hitBox.width;
        } else {
            entity.attackBox.x = entity.iWorldX;
        }

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                target[i].iHitBoxDefaultX = target[i].hitBox.x;
                target[i].iHitBoxDefaultY = target[i].hitBox.y;

                target[i].hitBox.x = target[i].iWorldX + target[i].hitBox.x;
                target[i].hitBox.y = target[i].iWorldY + target[i].hitBox.y;

                if (entity.attackBox.intersects(target[i].hitBox)){
                    index = i;
                }

                target[i].hitBox.x = target[i].iHitBoxDefaultX;
                target[i].hitBox.y = target[i].iHitBoxDefaultY;

            }
        }
        return index;
    }
}
