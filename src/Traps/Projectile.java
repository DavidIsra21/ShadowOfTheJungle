package Traps;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import static utilz.Constants.Projectiles.*;

public class Projectile {
    private Rectangle2D.Float hitbox;
    private boolean active = true;
    private int dir;

    public Projectile(int x, int y, int dir) {
        this.dir = dir; //-1 Right y 1 left
        int xOffset = (int) (5 * Game.SCALE);
        int yOffset = (int) (5 * Game.SCALE);
        if(dir == -1) {
            xOffset = (int) (44 * Game.SCALE);;
        }
        else
            xOffset = (int) (5 * Game.SCALE);

        hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, 18, 18);

    }

    public void drawHitbox(Graphics g, int xLvlOffset) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public void updatePos() {
        hitbox.y += SPEED;
    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }
}
