package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity implements ActionsAI {
    public GamePanel gp;
//ANIMATIONS
    public String facing;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public int barCounter = 0;
    public int deathCounter = 0;
    public boolean dying = false;
    public boolean alive = true;
    public boolean showBar = false;
//COLLISION
    public int immunityCounter = 0;
    public boolean immune = false;
    public int hitBoxDefaultX, hitBoxDefaultY;
    public Rectangle hitBox, hitBoxLeftSense, hitBoxRightSense, hitBoxTopSense, hitBoxBotSense;
    public boolean collisionLeft, collisionRight, collisionTop, collisionBottom, collisionDetected, wouldBeStuck = false;
//ATTACKING
    public boolean canAttack = true;
    public Entity source;
    public int collisionDmg;
    public int attackCounter, frameNumber = 0;
    public Rectangle attackBox = new Rectangle();
    public boolean rangedAttacking, meleeAttacking, attacking = false;
//MOVEMENT
    public int speed;
    public int speedOriginal;
    public int gravity;
    public int velocityX;
    public int velocityY;
    public int worldX, worldY;
    public int startWorldX, startWorldY;
    public int movementCounter = 0;
    public int jumpCooldown;
    public int recoveryTime;
    public boolean canMove = true;
    public boolean canJump = true;
    public boolean falling = false;
    public String moveState;
    public String direction;
    public BufferedImage attack1, attack2, attack3, attack4;
    public BufferedImage dying1, dying2, dying3, dying4;
    public BufferedImage jump1, jump2, crouch1, crouch2, right1, right2, idle1, idle2;
//STAT TRACKERS/IDENTIFIERS
    public String name;
    public Color color;
    public int type;
    public int health;
    public int healthMax;
    public int ultimate;
    public int ultimateMax;
    public int armor;
    public int value;

//CONSTRUCTOR
    public Entity(GamePanel gp) {
        this.gp = gp;
        hitBox = new Rectangle((int)(0.125*gp.tileSize), (int)(0.03125*gp.tileSize), (int)(0.75*gp.tileSize), (int)(0.9375*gp.tileSize));
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        gravity = -gp.tileSize /10;
        direction = "right";
        moveState = "idle";
        facing = "right";
    }
//SET ACTION ERROR MESSAGE
    @Override
    public void setAction() {
        throw new RuntimeException("setAction not defined in subclass");
    }
//UPDATE
    public void update() {
    //RANDOMIZED MOVEMENT COUNTER
        movementCounter++;
        if (movementCounter == 120) {
            movementCounter = 0;
            setAction();
        }
    //SETTING COLLISION VECTORS
        collisionDetected = false;
        gp.cCheck.checkTile(this);
        gp.cCheck.checkEntity(this, gp.npc);
        gp.cCheck.checkEntity(this, gp.monster);
        gp.cCheck.checkPlayer(this);
    //MOVEMENT ACTIONS
        switch (direction) {
            case "left":
                velocityX = -speed;
                facing = direction;
                moveLeft();
                break;
            case "right":
                velocityX = speed;
                facing = direction;
                moveRight();
                break;
        }
        switch (moveState) {
            case "jump":
                jump();
                break;
            case "crouch":
                speed = speed /2;
                break;
        }
        if (!moveState.equals("crouch")) {
            speed = speedOriginal;
        }
        //ANTISTUCK
        if (wouldBeStuck) --worldY;
        //IMMUNITY COUNTER
        if (immunityCounter > 0) {
            --immunityCounter;
        }
        if (immunityCounter <= 0) {
            immune = false;
            immunityCounter = 0;
        }
        if (showBar) {
            ++barCounter;
        }
        if (barCounter == 300) {
            barCounter = 0;
            showBar = false;
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
            worldY -= velocityY;
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
        //DEATH
        if (dying) {
            dyingAnimation();
        }
    }
//DRAW
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int iScreenX = worldX - gp.player.worldX + gp.player.iScreenPosX;
        int iScreenY = worldY - gp.player.worldY + gp.player.iScreenPosY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.iScreenPosX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.iScreenPosX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.iScreenPosY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.iScreenPosY) {
            if (attacking) {
                image = switch (frameNumber) {
                    case 1 -> attack1;
                    case 2 -> attack2;
                    case 3 -> attack3;
                    case 4 -> attack4;
                    default -> image;
                };
            } else if (dying) {
                image = switch (frameNumber) {
                    case 1 -> dying1;
                    case 2 -> dying2;
                    case 3 -> dying3;
                    case 4 -> dying4;
                    default -> image;
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
            if (showBar) {
                g2.setColor(Color.darkGray);
                g2.fillRect(iScreenX-1, iScreenY-16, gp.tileSize +2, 12);

                int dScale = gp.tileSize / healthMax;
                int healthPercent = dScale* health;

                if (type == 1) {
                    g2.setColor(new Color(0, 155 ,20));
                }
                else {
                    g2.setColor(new Color(155, 0 ,20));
                }
                g2.fillRect(iScreenX, iScreenY - 15, healthPercent, 10);
            }
            if (facing.equals("left")) {
                g2.drawImage(image, iScreenX + gp.tileSize, iScreenY, -gp.tileSize, gp.tileSize, null);
            } else {
                g2.drawImage(image, iScreenX, iScreenY, gp.tileSize, gp.tileSize, null);
            }
        }
    }
//MOVEMENT METHODS
    public void moveLeft() {
        if (canMove) {
            velocityX = -speed;
            facing = direction;
            if (!collisionLeft) {
                worldX += velocityX;
            }
        }
    }
    public void moveRight() {
        if (canMove) {
            velocityX = speed;
            facing = direction;
            if (!collisionRight) {
                worldX += velocityX;
            }
        }
    }
    public void jump() {
        if (canJump && !collisionTop && collisionBottom && canMove) {
            jumpCooldown = recoveryTime;
            worldY -= speed;
            velocityY = speed + 10;
            canJump = false;
        }
    }
//DAMAGE
    public void damagePlayer(int amount) {
        if (!gp.player.immune){
           gp.player.health -= amount;
           gp.player.immunityCounter = 60;
           gp.player.immune = true;
           gp.ui.addPopupText(amount, gp.player.worldX + gp.player.hitBox.width/2, gp.player.worldY, 0);
        }
        if (gp.player.health <= 0) {
            gp.player.dying = true;
        }
    }
    public void healPlayer(int amount){
        gp.player.health += amount;
        gp.ui.addPopupText(amount, gp.player.worldX + gp.player.hitBox.width/2, gp.player.worldY, 2);
    }
    public void damageMonster(int target, Entity source) {
        if (target != 999) {
            if (!gp.monster[target].immune){
                int damage = source.collisionDmg - gp.monster[target].armor;
                if (damage <= 0 ) {
                    damage = 0;
                    gp.ui.addPopupText(damage, (gp.monster[target].worldX + gp.monster[target].hitBox.width/2), gp.monster[target].worldY, 1);
                }
                if (damage > 0) {
                    gp.monster[target].health -= damage;
                    gp.ui.addPopupText(damage, (gp.monster[target].worldX + gp.monster[target].hitBox.width/2), gp.monster[target].worldY, 0);
                    gp.player.ultimate += gp.monster[target].value;
                    gp.monster[target].immunityCounter = 30;
                    gp.monster[target].immune = true;
                    gp.monster[target].showBar = true;
                }
            }
            if (gp.monster[target].health <= 0) {
                gp.monster[target].dying = true;
            }
        }
    }
    public void damageNPC(int target, Entity source) {
        if (target != 999) {
            if (!gp.npc[target].immune && source != gp.player) {
                gp.npc[target].health -= source.collisionDmg;
                gp.npc[target].immunityCounter = 60;
                gp.npc[target].immune = true;
                gp.npc[target].showBar = true;
            }
            if (gp.npc[target].health <= 0) {
                gp.npc[target].dying = true;
            }
        }
    }
    public void dyingAnimation(){
        canMove = false;
        immune = true;
        collisionDmg = 0;
        deathCounter++;
        if (deathCounter == 15) {
            ++frameNumber;
            deathCounter = 0;
        }
        if (frameNumber == 4) {
            alive = false;
            dying = false;
        }
    }
}
