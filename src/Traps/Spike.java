package Traps;

import Main.Game;
import utilz.Constants;

public class Spike extends Traps{

    public Spike(int x, int y, int trap_type) {
        super(x, y, trap_type);

        initHitbox(32, 16);

        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * 14);
        hitbox.y += yDrawOffset;
    }

    public void update() {
        updateAnimationTick();
    }



}
