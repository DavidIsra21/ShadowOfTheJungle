package entities;

import Main.Game;
import utilz.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.Directions.*;
import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CanMoveHere;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick;
    private int aniIndex;
    private final int aniSpeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private final float playerSpeed = 2.0f;
    private int[][] lvlData;
//    private float xDrawOffset = 21 * Game.SCALE;
//    private float yDrawOffset = 4 * Game.SCALE;
    private float xDrawOffset = 74 * Game.SCALE;
    private float yDrawOffset = 45 * Game.SCALE;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 33*Game.SCALE, 43*Game.SCALE);
    }

    public void update() {

        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {

        //g.drawImage(subImg, 0, 0, 568, 372, null);
        //Player: 244x186
        //g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, (int)(244*sizeMultiplier), (int)(186*sizeMultiplier), null);
        //g.drawImage(animations[playerAction][aniIndex], (int)xDelta, (int)yDelta, (int)(200*sizeMultiplier), (int)(325*sizeMultiplier), null);

        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        g.drawRect((int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height);

        drawHitbox(g);
    }

    private void updateAnimationTick() {

        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
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

        if(attacking) {
            playerAction = ATTACK_1;
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
        if(!left && !right && !up && !down)
            return;

        float xSpeed = 0, ySpeed = 0;

        if(left && !right)
            xSpeed = -playerSpeed;
        else if(right && !left)
            xSpeed = playerSpeed;

        if(up && !down)
            ySpeed = -playerSpeed;
        else if(down && !up)
            ySpeed = playerSpeed;

//        if(CanMoveHere(x+xSpeed, y+ySpeed, width, height, lvlData)) {
//            this.x += xSpeed;
//            this.y += ySpeed;
//            moving = true;
//        }

        if(CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            moving = true;
        }
    }

    private void loadAnimations() {

            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
            animations = new BufferedImage[14][6];
            for(int j = 0; j < animations.length; j++)
                for (int i = 0; i < animations[j].length; i++) {
                    //idleAni[i] = img.getSubimage(i*284, 9*186, 284, 186);
                    animations[j][i] = img.getSubimage(i * 244, j * 186, 244, 186);
                }
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
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


}
