package main;

import entity.Entity;

import java.awt.*;

public class CollisionCheck {
    GamePanel gp;
    public CollisionCheck (GamePanel gp) {
        this.gp = gp;

    }
    public void checkTile(Entity entity) {

        int iEntLeftWorldX = entity.worldX + entity.hitBox.x;
        int iEntRightWorldX = entity.worldX + entity.hitBox.x + entity.hitBox.width;
        int iEntTopWorldY = entity.worldY + entity.hitBox.y;
        int iEntBotWorldY = entity.worldY + entity.hitBox.y + entity.hitBox.height;
        int iEntityLeftCol;
        int iEntityRightCol;
        int iEntityTopRow = iEntTopWorldY / gp.tileSize;
        int iEntityBottomRow = iEntBotWorldY / gp.tileSize;
        int iTileNum1, iTileNum2, iTileNum3, iTileNum4, iTileNum5, iTileNum6;

        iEntityLeftCol = (iEntLeftWorldX) / gp.tileSize;
        iEntityRightCol = (iEntRightWorldX) / gp.tileSize;
        iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityBottomRow];
        iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityBottomRow];

        entity.wouldBeStuck = gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision;

        iEntityLeftCol = (iEntLeftWorldX - entity.speedOriginal) / gp.tileSize;
        iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityTopRow];
        iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityBottomRow];

        entity.collisionLeft = gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision;

        iEntityRightCol = (iEntRightWorldX + entity.speedOriginal) / gp.tileSize;
        iTileNum1 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityTopRow];
        iTileNum2 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityBottomRow];

        entity.collisionRight = gp.tileManager.tile[iTileNum1].collision || gp.tileManager.tile[iTileNum2].collision;

        iEntityLeftCol = (iEntLeftWorldX) / gp.tileSize;
        iEntityRightCol = (iEntRightWorldX) / gp.tileSize;
        iEntityTopRow = (iEntTopWorldY - entity.speedOriginal) / gp.tileSize;
        iEntityBottomRow = (iEntBotWorldY + entity.speedOriginal) / gp.tileSize;
        iTileNum3 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityTopRow];
        iTileNum4 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityTopRow];
        iTileNum5 = gp.tileManager.iiMapTileNumber[iEntityLeftCol][iEntityBottomRow];
        iTileNum6 = gp.tileManager.iiMapTileNumber[iEntityRightCol][iEntityBottomRow];

        entity.collisionTop = gp.tileManager.tile[iTileNum3].collision || gp.tileManager.tile[iTileNum4].collision;
        entity.collisionBottom = gp.tileManager.tile[iTileNum5].collision || gp.tileManager.tile[iTileNum6].collision;
        entity.collisionDetected = entity.collisionLeft || entity.collisionRight || entity.collisionTop || entity.collisionBottom;
    }

    public int checkObject(Entity entity, boolean player) {
         int index = 999;
         for (int i = 0; i < gp.obj.length; i++) {
             if (gp.obj[i] != null) {

                 entity.hitBox.x = entity.worldX + entity.hitBox.x;
                 entity.hitBox.y = entity.worldY + entity.hitBox.y;

                 gp.obj[i].hitBox.x = gp.obj[i].iWorldX + gp.obj[i].hitBox.x;
                 gp.obj[i].hitBox.y = gp.obj[i].iWorldY + gp.obj[i].hitBox.y;

                 entity.hitBoxLeftSense = new Rectangle(entity.hitBox.x - entity.speed, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
                 entity.hitBoxRightSense = new Rectangle(entity.hitBox.x + entity.speed, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
                 entity.hitBoxTopSense = new Rectangle(entity.hitBox.x, entity.hitBox.y - entity.speed, entity.hitBox.width, entity.hitBox.height);
                 entity.hitBoxBotSense = new Rectangle(entity.hitBox.x, entity.hitBox.y + entity.speed, entity.hitBox.width, entity.hitBox.height);

                 if (entity.hitBoxLeftSense.intersects(gp.obj[i].hitBox) || entity.hitBoxRightSense.intersects(gp.obj[i].hitBox) || entity.hitBoxTopSense.intersects(gp.obj[i].hitBox) || entity.hitBoxBotSense.intersects(gp.obj[i].hitBox)){
                     if (player) index = i;
                 }
                 entity.hitBox.x = entity.hitBoxDefaultX;
                 entity.hitBox.y = entity.hitBoxDefaultY;
                 gp.obj[i].hitBox.x = gp.obj[i].iHitBoxDefaultX;
                 gp.obj[i].hitBox.y = gp.obj[i].iHitBoxDefaultY;
             }
         }
         return index;
    }
    public int checkEntity(Entity entity, Entity[] target ) {
        int index = 999;
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                entity.hitBox.x = entity.worldX + entity.hitBox.x;
                entity.hitBox.y = entity.worldY + entity.hitBox.y;

                target[i].hitBox.x = target[i].worldX + target[i].hitBox.x;
                target[i].hitBox.y = target[i].worldY + target[i].hitBox.y;

                entity.hitBoxLeftSense = new Rectangle(entity.hitBox.x - entity.velocityX, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
                entity.hitBoxRightSense = new Rectangle(entity.hitBox.x + entity.velocityX, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
                entity.hitBoxTopSense = new Rectangle(entity.hitBox.x, entity.hitBox.y - entity.velocityY, entity.hitBox.width, entity.hitBox.height);
                entity.hitBoxBotSense = new Rectangle(entity.hitBox.x, entity.hitBox.y + entity.velocityY, entity.hitBox.width, entity.hitBox.height);

                if (entity.hitBoxLeftSense.intersects(target[i].hitBox) || entity.hitBoxRightSense.intersects(target[i].hitBox) || entity.hitBoxTopSense.intersects(target[i].hitBox) || entity.hitBoxBotSense.intersects(target[i].hitBox)){
                    if (target[i] != entity) {
                        entity.collisionEntity = true;
                        index = i;
                        System.out.println(index);
                    }
                    System.out.println(entity.name);
                    System.out.println(entity.collisionEntity);
                }

                entity.hitBox.x = entity.hitBoxDefaultX;
                entity.hitBox.y = entity.hitBoxDefaultY;
                target[i].hitBox.x = target[i].hitBoxDefaultX;
                target[i].hitBox.y = target[i].hitBoxDefaultY;
            }
        }
        return index;
    }
    public boolean checkPlayer(Entity entity) {
        boolean hit = false;

        entity.hitBox.x = entity.worldX + entity.hitBox.x;
        entity.hitBox.y = entity.worldY + entity.hitBox.y;
        gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
        gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;
        entity.hitBoxLeftSense = new Rectangle(entity.hitBox.x - entity.velocityX, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
        entity.hitBoxRightSense = new Rectangle(entity.hitBox.x + entity.velocityX, entity.hitBox.y, entity.hitBox.width, entity.hitBox.height);
        entity.hitBoxTopSense = new Rectangle(entity.hitBox.x, entity.hitBox.y - entity.velocityY, entity.hitBox.width, entity.hitBox.height);
        entity.hitBoxBotSense = new Rectangle(entity.hitBox.x, entity.hitBox.y + entity.velocityY, entity.hitBox.width, entity.hitBox.height);

        if (entity.hitBoxLeftSense.intersects(gp.player.hitBox) || entity.hitBoxRightSense.intersects(gp.player.hitBox) || entity.hitBoxTopSense.intersects(gp.player.hitBox) || entity.hitBoxBotSense.intersects(gp.player.hitBox)){
            hit = true;
            entity.collisionDetected = true;
        }
        entity.hitBox.x = entity.hitBoxDefaultX;
        entity.hitBox.y = entity.hitBoxDefaultY;
        gp.player.hitBox.x = gp.player.hitBoxDefaultX;
        gp.player.hitBox.y = gp.player.hitBoxDefaultY;
        return hit;
    }
    public int checkIfEntityHitTarget(Entity entity, Entity[] target) {
        int index = 999;

        entity.attackBox.y = entity.worldY;
        entity.attackBox.width = 2*entity.hitBox.width;
        entity.attackBox.height = entity.hitBox.height;

        if (entity.facing.equals("left")) {
            entity.attackBox.x = entity.worldX - entity.hitBox.width;
        } else {
            entity.attackBox.x = entity.worldX;
        }

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                target[i].hitBoxDefaultX = target[i].hitBox.x;
                target[i].hitBoxDefaultY = target[i].hitBox.y;

                target[i].hitBox.x = target[i].worldX + target[i].hitBox.x;
                target[i].hitBox.y = target[i].worldY + target[i].hitBox.y;

                if (entity.attackBox.intersects(target[i].hitBox)){
                    index = i;
                }

                target[i].hitBox.x = target[i].hitBoxDefaultX;
                target[i].hitBox.y = target[i].hitBoxDefaultY;

            }
        }
        return index;
    }
}
