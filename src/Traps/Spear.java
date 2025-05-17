package Traps;

import Main.Game;

public class Spear extends Traps {

    public Spear(int x, int y, int trap_type) {
        super(x, y, trap_type);

        initHitbox(32, 32);

        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * 0);
        hitbox.y += yDrawOffset;
    }

    public void update() {
        updateAnimationTick();
    }

}
