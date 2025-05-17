package entities;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] enemy1Arr;
    private ArrayList<Enemy1> enemies1 = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();

    }

    public void loadEnemies(Level level) {
        enemies1 = level.getEnemies();
    }

    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for(Enemy1 e : enemies1) {
            if(e.isActive()) {
                e.update(lvlData, player);
                isAnyActive = true;
            }
        }
        if(!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawEnemy1(g, xLvlOffset);
    }

    private void drawEnemy1(Graphics g, int xLvlOffset) {
        for(Enemy1 e : enemies1)
            if(e.isActive()) {
                g.drawImage(enemy1Arr[e.getState()][e.getAniIndex()],
                        (int)e.getHitbox().x - xLvlOffset - ENEMY1_DRAWOFFSET_X + e.flipX(),
                        (int)e.getHitbox().y - ENEMY1_DRAWOFFSET_Y,
                        ENEMY1_WIDTH * e.flipW(),
                        ENEMY1_HEIGHT, null);
                //e.drawHitbox(g, xLvlOffset);
                //e.drawAttackBox(g, xLvlOffset);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Enemy1 e : enemies1)
            if (e.isActive() && (e.getState()!=DEAD)) {
                if(attackBox.intersects(e.getHitbox())) {
                    e.hurt(10);
                    return;
                }
            }
    }

    private void loadEnemyImgs() {
        enemy1Arr = new BufferedImage[6][14];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ENEMY1_SPRITE);
        for(int j = 0; j < enemy1Arr.length; j++) {
            for (int i = 0; i < enemy1Arr[j].length; i++) {
                enemy1Arr[j][i] = temp.getSubimage(i * ENEMY1_WIDTH_DEFAULT, j * ENEMY1_HEIGHT_DEFAULT, ENEMY1_WIDTH_DEFAULT, ENEMY1_HEIGHT_DEFAULT);
            }
        }

    }

    public void resetAllEnemies() {
        for(Enemy1 e : enemies1) {
            e.resetEnemy();
        }
    }
}
