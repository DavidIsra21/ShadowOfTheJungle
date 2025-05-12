package entities;

import Main.Game;
import gamestates.Playing;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick;
    private int aniIndex;
    private int aniSpeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int[][] lvlData;
    //    private float xDrawOffset = 21 * Game.SCALE;
//    private float yDrawOffset = 4 * Game.SCALE;
    private float xDrawOffset = 68 * Game.SCALE;
    private float yDrawOffset = 56 * Game.SCALE;


    //jumping/gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f*Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    //StatusBarUI
    private BufferedImage statusBarImg, healthBarImg, energyBarImg;

    private int statusBarWidth = (int) (98 * Game.SCALE);
    private int statusBarHeight = (int) (35 * Game.SCALE);
    private int statusBarX = (int) (40 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (98 * Game.SCALE);
    private int healthBarHeight = (int) (35 * Game.SCALE);
    private int healthBarXStart = (int) (0 * Game.SCALE);
    private int healthBarYStart = (int) (0 * Game.SCALE);

    private int maxHealth = 18;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    private int healthWidthMaxPx = 98;
    private int healthWidthCurrentPx = healthWidthMaxPx;

    //AttackBox
    private Rectangle2D.Float attackBox;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;
    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initHitbox(x, y, (int)(19*Game.SCALE), (int) (27*Game.SCALE));
//        initHitbox(x, y, (int)(25*Game.SCALE), (int) (32*Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (36*Game.SCALE), (int)(22*Game.SCALE));

    }

    public void update() {
        updateHealthBar();

        if(currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }

        updateAttackBox();

        updatePos();
        if(attacking)
            checkAttack();
        updateAnimationTick();
        setAnimation();
    }

    private void checkAttack() {
        if(attackChecked || aniIndex != 2)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }

    private void updateAttackBox() {
        if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 4);
        }else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 20);
        }
        attackBox.y = hitbox.y + (Game.SCALE*4);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float)maxHealth) * healthBarWidth);
        if(currentHealth > 0)
            healthWidthCurrentPx = (int) ((currentHealth / (float)maxHealth) * healthWidthMaxPx);
        else
            healthWidthCurrentPx = 1;
    }

    public void render(Graphics g, int LvlOffset) {

        //g.drawImage(subImg, 0, 0, 568, 372, null);
        //Player: 244x186
        //g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, (int)(244*sizeMultiplier), (int)(186*sizeMultiplier), null);
        //g.drawImage(animations[playerAction][aniIndex], (int)xDelta, (int)yDelta, (int)(200*sizeMultiplier), (int)(325*sizeMultiplier), null);

        g.drawImage(animations[playerAction][aniIndex],
                (int) (hitbox.x - xDrawOffset) - LvlOffset + flipX,
                (int) (hitbox.y - yDrawOffset),
                (int)(width + (30*Game.SCALE)) * flipW,
                (int)(height + (30*Game.SCALE)), null);
        //g.drawRect((int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height);

        //drawHitbox(g, LvlOffset);
        //drawAttackBox(g, LvlOffset);

        drawUI(g);
    }

    private void drawAttackBox(Graphics g, int lvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int)attackBox.x - lvlOffset, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    private void drawUI(Graphics g) {
        BufferedImage temp = statusBarImg.getSubimage(0,0,98,19);
        g.drawImage(temp, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);

        drawHealth(g);
//        g.setColor(Color.red);
//        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void drawHealth(Graphics g) {
        BufferedImage health = healthBarImg.getSubimage(0,38,32,32);
        BufferedImage temp = healthBarImg.getSubimage(0, 0, healthWidthCurrentPx, 19);
        g.drawImage(health, 10, statusBarY, (int)(27*Game.SCALE), (int)(27*Game.SCALE), null);
        g.drawImage(temp, statusBarX, statusBarY, healthWidth, healthBarHeight, null);
    }

    private void updateAnimationTick() {

        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }

        }

    }

    private void setAnimation() {
        int startAni = playerAction;

        if(moving)
        {
            playerAction = RUNNING;
        }
        else {
            playerAction = IDLE;
        }

        if(inAir) {
            if(airSpeed < 0) {
                playerAction = JUMP;
            }

            else {
                playerAction = FALLING;
            }

        }

        if(attacking) {

            playerAction = ATTACK_1;
            if(startAni != ATTACK_1) {
                aniIndex = 2;
                aniTick = 0;
                return;
            }
        }

        if(startAni != playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if(jump)
            jump();
//        if(!left && !right && !inAir)
//            return;
//
        if(!inAir)
            if((!left && !right) || (right && left))
                return;

        float xSpeed = 0;

        if(left) {
            xSpeed -= playerSpeed;
            flipX = (int)(width + (30*Game.SCALE));
            flipW = -1;
        }
        if(right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir)
            if (!IsEntityOnFloor(hitbox, lvlData))
                inAir = true;

        if (inAir) {
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        }else {
            updateXPos(xSpeed);
        }

        moving = true;

    }

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHealt(int value) {
        currentHealth += value;

        if(currentHealth <= 0) {
            currentHealth = 0;
            //GAME OVER
            //gameOver();
        }else if( currentHealth >= maxHealth) {
            currentHealth = maxHealth;
            healthWidthCurrentPx = healthWidthMaxPx;
        }
    }

    private void loadAnimations () {

        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[14][6];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 244, j * 186, 244, 186);
            }

        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
        healthBarImg = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_BAR);
        energyBarImg = LoadSave.GetSpriteAtlas(LoadSave.ENERGY_BAR);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }


    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;
        healthWidthCurrentPx = healthWidthMaxPx;

        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }
}
