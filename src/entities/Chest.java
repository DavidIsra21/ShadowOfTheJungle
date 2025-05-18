package entities;

import Main.Game;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;

public class Chest extends Enemy{

    //AttackBox
    private int attackBoxOffsetX;

    public Chest(float x, float y) {
        super(x, y, CHEST_WIDTH, CHEST_HEIGHT, CHEST);
        initHitbox(31, 31);
        state = 1;
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        switch (state) {
            case CLOSE_CHEST:
                break;
            case OPEN_CHEST:

                break;
            case HIT_CHEST:
                newState(CLOSE_CHEST);
                break;
        }
    }


}
