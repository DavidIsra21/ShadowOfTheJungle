package Traps;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.TrapConstants.*;

public class Traps {

    protected int x, y, trap_type;
    protected Rectangle2D.Float hitbox;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    public Traps(int x, int y, int trap_type) {
        this.x = x;
        this.y = y;
        this.trap_type = trap_type;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(trap_type))
                aniIndex = 0;

        }
    }


    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    public void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public int getTrap_type() {
        return trap_type;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public int getAniIndex() {
        return aniIndex;
    }

}
