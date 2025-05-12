package entities;

import Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

public class Enemy1 extends Enemy{

    //AttackBox
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public Enemy1(float x, float y) {
        super(x, y, ENEMY1_WIDTH, ENEMY1_HEIGHT, ENEMY1);
        initHitbox(x, y, (int)(28 * Game.SCALE), (int)(29 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int)(88 * Game.SCALE),(int)(29 * Game.SCALE));
        attackBoxOffsetX = (int)(Game.SCALE * 30);
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if(inAir)
            updateInAir(lvlData);
        else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:

                    if(canSeePlayer(lvlData, player))
                        turnTowardsPlayer(player);
                    if(isPlayerCloseForAttack(player))
                        newState(ATTACK);

                    move(lvlData);
                case ATTACK:
                    if(aniIndex == 0 || aniIndex < 5 || aniIndex > 7)
                        attackChecked = false;

                    if(aniIndex >= 5 && aniIndex <= 7 && !attackChecked && enemyState == ATTACK)
                        checkPlayerHit(attackBox, player);

                    break;
                case HIT:
                    break;
            }
        }
    }



    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int)(attackBox.x - xLvlOffset), (int)attackBox.y, (int)(attackBox.width), (int)attackBox.height);
//        if(walkDir == RIGHT)
//            g.drawRect((int)(attackBox.x - xLvlOffset + width), (int)attackBox.y, (int)(attackBox.width * -1), (int)attackBox.height);
//        else
//            g.drawRect((int)(attackBox.x - xLvlOffset), (int)attackBox.y, (int)(attackBox.width * flipW()), (int)attackBox.height);

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
