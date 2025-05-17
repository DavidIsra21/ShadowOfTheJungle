package entities;

import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;

public class Enemy2 extends Enemy{
    public Enemy2(float x, float y) {
        super(x, y, ENEMY2_WIDTH, ENEMY2_HEIGHT, ENEMY2);
        initHitbox(42, 38);
        state = 5;
    }

    public void update(Player player) {
        updateBehavior();
        updateAnimationTick();
        turnTowardsPlayer(player);
    }

    private void updateBehavior() {
            switch (state) {
                case IDLE_E2:
                    if(aniIndex == 5)
                        newState(ATTACK1_E2);
                case ATTACK1_E2:

                    if(aniIndex > 11)
                        newState(IDLE_E2);
                    break;
            }
    }

    public int flipX() {
        if(walkDir == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipW() {
        if(walkDir == RIGHT)
            return -1;
        else
            return 1;
    }
}

