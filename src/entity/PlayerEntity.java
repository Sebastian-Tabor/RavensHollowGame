package entity;
import main.GamePanel;
import main.KeyBinds;
import main.MouseBinds;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class PlayerEntity extends Entity implements HasImages {
    final KeyBinds keyBinds;
    public int iScreenPosX;
    public int iScreenPosY;
    public int iStartPosX = gp.tileSize * 17;;
    public int iStartPosY = gp.tileSize * 11;;
    public boolean bMouseIsLeft;

//PLAYER OBJECT
    public PlayerEntity(GamePanel gp, KeyBinds keyBinds) {
        super (gp);
        this.keyBinds = keyBinds;
    //PLAYER POS ON SCREEN
        iScreenPosX = (gp.screenWidth/2 - (gp.tileSize /2));
        iScreenPosY = (gp.screenHeight/2 - (gp.tileSize /2));
    //PLAYER POS METHOD IMPLEMENTATION
        setDefaultValues();
    //PLAYER IMAGE METHOD IMPLEMENTATION
        getImage();
    }

//SETTING PLAYER VALUES
    public void setDefaultValues() {
        worldX = iStartPosX;
        worldY = iStartPosY;
        type = 0;
        speedOriginal = (int)(0.109375*gp.tileSize);
        if (speedOriginal == 0){
            speedOriginal = 1;
        }
        speed = speedOriginal;
        recoveryTime = 10;
        health = 10;
        healthMax = 10;
        ultimate = 0;
        ultimateMax = 20;
        armor = 0;
        collisionDmg = 2;
        moveState = "idle";
        direction = "idle";
        facing = "right";
    }
//IMAGE SETUP METHOD
    @Override
    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;
        try {
            scaledImage = ImageIO.read(new File( imageName + ".png"));
            scaledImage = uTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return scaledImage;
    }
//PLAYER IMAGES
    @Override
    public void getImage() {

            right1 = setup("./res/player/right1");
            right2 = setup("./res/player/right2");
            jump1 = setup("./res/player/jump1");
            jump2 = setup("./res/player/jump2");
            crouch1 = setup("./res/player/crouch1");
            crouch2 = setup("./res/player/crouch2");
            idle1 = setup("./res/player/idle1");
            idle2 = setup("./res/player/idle2");
            attack1 = setup("./res/player/attack1");
            attack2 = setup("./res/player/attack2");
            attack3 = setup("./res/player/attack3");
            attack4 = setup("./res/player/attack4");
            dying1 = setup("./res/player/dying1");
            dying2 = setup("./res/player/dying2");
            dying3 = setup("./res/player/dying3");
            dying4 = setup("./res/player/dying4");
    }
//UPDATE
    public void update() {
    //Resizing updates
    //KEYBIND MOVEMENT
        if (KeyBinds.bDownPressed || KeyBinds.bUpPressed || KeyBinds.bRightPressed || KeyBinds.bLeftPressed) {
            if (KeyBinds.bLeftPressed) {
                direction = "left";
                moveState = "moving";
            }
            if (KeyBinds.bRightPressed) {
                direction = "right";
                moveState = "moving";
            }
            if (KeyBinds.bUpPressed) {
                moveState = "jump";
            }
            if (KeyBinds.bDownPressed) {
                moveState = "crouch";
            }
        } else {
            moveState = "idle";
            direction = "idle";
        }
        if (MouseBinds.bMouseClicked) {
            if (bMouseIsLeft) {
                facing = "left";
            } else {
                facing = "right";
            }
        }
        bMouseIsLeft = gp.mouseBinds.getMouseLocation().x < (gp.screenWidth / 2);
    //MOVEMENT ACTIONS
        switch (direction) {
            case "left":
                moveLeft();
                break;
            case "right":
                moveRight();
                break;
            case "idle":
                break;
        }
        switch (moveState) {
            case "jump":
                jump();
                break;
            case "crouch":
                speed = speedOriginal /2;
                break;
            case "idle":
                velocityX = 0;
                break;
            case "moving":
                break;
        }
        if (!moveState.equals("crouch")) {
            speed = speedOriginal;
        }
    //ATTACKING
        attacking = meleeAttacking || rangedAttacking;
        if (MouseBinds.bMouse1Clicked && canAttack) {
            canAttack = false;
            rangedAttacking = true;
            if (bMouseIsLeft){
                facing = "left";
            } else {
                facing = "right";
            }
        }
        if (MouseBinds.bMouse2Clicked && canAttack) {
            canAttack = false;
            meleeAttacking = true;
            if (bMouseIsLeft){
                facing = "left";
            } else {
                facing = "right";
            }
        }
        if (attacking) {
            attackCounter++;
        }
        if (attackCounter == 8) {
            attackCounter = 0;
            ++frameNumber;
        }
        if (meleeAttacking) {
            if (frameNumber == 1 || frameNumber == 2) {
                int iAttackIndex = gp.cCheck.checkIfEntityMeleedTarget(this, gp.monster);
                if (iAttackIndex != 999) {
                    damageMonster(iAttackIndex, this);
                }
            }
        } else if (rangedAttacking) {
            if (frameNumber == 1 && attackCounter == 0) {
                gp.projectile.add(new ArrowProjectile(gp, gp.mouseBinds.getMouseLocation(), gp.player));
            }
        }
        if (health > healthMax) health = healthMax;
        if (ultimate > ultimateMax) ultimate = ultimateMax;

        if (frameNumber == 4) {
            frameNumber = 0;
            meleeAttacking = false;
            rangedAttacking = false;
            attacking = false;
            canAttack = true;

        }
    //COLLISION CHECK
        gp.cCheck.checkTile(this);
        if (wouldBeStuck) --worldY;

        int iObjectIndex = gp.cCheck.checkObject(this, true);
        pickupObject(iObjectIndex);
        int iNPCIndex = gp.cCheck.checkEntity(this, gp.npc);
        interactNPC(iNPCIndex);
        int iMonsterIndex = gp.cCheck.checkEntity(this, gp.monster);
        collisionMonster(iMonsterIndex);

        //Leave this here VV pls
        collisionDetected = false;
        //Do not touch this ^^
        if (immunityCounter > 0) {
            --immunityCounter;
        }
        if (immunityCounter <= 0) {
            immune = false;
            immunityCounter = 0;
        }
        //JUMP CONDITIONS
        if (collisionBottom) {
            velocityY = 0;
            jumpCooldown--;
            if (jumpCooldown < 0) {
                jumpCooldown = 0;
                canJump = true;
            }
        }
        else {
            //JUMPING/FALLING MOVEMENT
            this.worldY -= velocityY;
            velocityY--;
        }
        //MAX GRAVITY
        if (velocityY < gravity) velocityY = gravity;
        //TO HIT HEAD ON CEILING (DO NOT SET 0 OR YOU WILL STICK)
        if (collisionTop) velocityY = -5;
        //SET FALLING
        falling = velocityY < 0;

        //SPRITE COUNTER
        spriteCounter++;
        if (spriteCounter > 12) {
            if (spriteNumber == 2){
                spriteNumber = 1;}
            else if (spriteNumber == 1) {
                spriteNumber = 2;}
        spriteCounter = 0;
        }
        if (dying) {
            dyingAnimation();
        }
    }
//DRAW METHOD
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (attacking) {
            image = switch (frameNumber) {
                case 0 -> attack1;
                case 1 -> attack2;
                case 2 -> attack3;
                case 3, 4 -> attack4;
                default -> throw new IllegalStateException("Unexpected value: " + frameNumber);
            };
        } else if (dying) {
            image = switch (frameNumber) {
                case 0 -> dying1;
                case 1 -> dying2;
                case 2 -> dying3;
                case 3, 4 -> dying4;
                default -> throw new IllegalStateException("Unexpected value: " + frameNumber);
            };
        } else switch (moveState) {
            case "moving":
                if (spriteNumber == 1) {
                    image = right1;
                }
                if (spriteNumber == 2) {
                    image = right2;
                }
                break;
            case "jump":
                if (spriteNumber == 1) {
                    image = jump1;
                }
                if (spriteNumber == 2) {
                    image = jump2;
                }
                break;
            case "crouch":
                if (spriteNumber == 1) {
                    image = crouch1;
                }
                if (spriteNumber == 2) {
                    image = crouch2;
                }
                break;
            case "idle":
                if (spriteNumber == 1) {
                    image = idle1;
                }
                if (spriteNumber == 2) {
                    image = idle2;
                }
                break;
        }
        assert image != null;
        if (facing.equals("left")) {
            g2.drawImage(image, iScreenPosX + gp.tileSize, iScreenPosY, -image.getWidth(), image.getHeight(), null);
        } else {
            g2.drawImage(image, iScreenPosX, iScreenPosY, image.getWidth(), image.getHeight(), null);
        }
    }
    //OBJECT METHODS
    public void pickupObject(int index) {
        if(index != 999){
            String sObjectName = gp.obj[index].sName;
            switch (sObjectName) {
                case "Feather":
                    gp.obj[index] = null;
                    gp.playSoundEffect(0);
                    speed += 1;
                    break;
                case "Bone":
                    gp.obj[index] = null;
                    //sound effect
                    break;
                case "Arrow":
                    gp.obj[index] = null;
                    //sound effect
                    health--;
                    break;
            }
        }

    }
    public void interactNPC(int index) {
        if (index != 999) {
            String name = gp.npc[index].name;
            switch (name) {
                case "Carver":
                    //Do nothing now
                    break;
                case "Bone":
                    break;
            }
        }
    }

    public void collisionMonster(int index) {
        if(index != 999 && !dying && alive){
            damagePlayer(gp.monster[index].collisionDmg);
        }
    }
    public void dyingAnimation(){
        canMove = false;
        immune = true;
        deathCounter++;
        if (deathCounter == 11) {
            ++frameNumber;
            deathCounter = 0;
        }
        if (frameNumber == 4) {
            alive = false;
            dying = false;
        }
    }

}