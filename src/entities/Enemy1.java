package entities;

import Main.Game;

import static utilz.Constants.EnemyConstants.*;

public class Enemy1 extends Enemy{


    public Enemy1(float x, float y) {
        super(x, y, ENEMY1_WIDTH, ENEMY1_HEIGHT, ENEMY1);
        initHitbox(x, y, (int)(28 * Game.SCALE), (int)(29 * Game.SCALE));
    }


}
