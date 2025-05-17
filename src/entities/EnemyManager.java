package entities;

import Traps.Projectile;
import Traps.TrapManager;
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
    private BufferedImage[][] enemy2Arr;
    private ArrayList<Enemy1> enemies1 = new ArrayList<>();
    private ArrayList<Enemy2> enemies2 = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();

    }

    public void loadEnemies(Level level) {
        enemies1 = level.getEnemies1();
        enemies2 = level.getEnemies2();
    }

    public void update(int[][] lvlData, Player player, TrapManager trapManager) {
        boolean isAnyActive = false;
        for(Enemy1 e : enemies1) {
            if(e.isActive()) {
                e.update(lvlData, player);
                isAnyActive = true;
            }
        }
        for(Enemy2 e2 : enemies2) {
            if (e2.getState() == ATTACK1_E2 && e2.getAniIndex() == 9 && e2.getAniTick() == 0)
                shootProjectile(e2, trapManager);
            e2.update(player);

        }
        if(!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    private void shootProjectile(Enemy2 e2, TrapManager trapManager) {
        int dir = e2.flipW();
        ArrayList<Projectile> projectiles = trapManager.getProjectiles();

        projectiles.add(new Projectile((int)(e2.getHitbox().x), (int)(e2.getHitbox().y), dir));

        trapManager.setProjectiles(projectiles);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawEnemy1(g, xLvlOffset);
        drawEnemy2(g, xLvlOffset);
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

    private void drawEnemy2(Graphics g, int xLvlOffset) {
        for(Enemy2 e2 : enemies2) {
            g.drawImage(enemy2Arr[e2.getState()][e2.getAniIndex()],
                    (int) e2.getHitbox().x - xLvlOffset - ENEMY2_DRAWOFFSET_X + e2.flipX(),
                    (int) e2.getHitbox().y - ENEMY2_DRAWOFFSET_Y,
                    ENEMY2_WIDTH * e2.flipW(),
                    ENEMY2_HEIGHT, null);
            //e2.drawHitbox(g, xLvlOffset);
            //e2.drawAttackBox(g, xLvlOffset);
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
        enemy2Arr = new BufferedImage[6][14];

        BufferedImage temp1 = LoadSave.GetSpriteAtlas(LoadSave.ENEMY1_SPRITE);
        for(int j = 0; j < enemy1Arr.length; j++) {
            for (int i = 0; i < enemy1Arr[j].length; i++) {
                enemy1Arr[j][i] = temp1.getSubimage(i * ENEMY1_WIDTH_DEFAULT, j * ENEMY1_HEIGHT_DEFAULT, ENEMY1_WIDTH_DEFAULT, ENEMY1_HEIGHT_DEFAULT);
            }
        }

        BufferedImage temp2 = LoadSave.GetSpriteAtlas(LoadSave.Enemy2);
        for(int j = 0; j < enemy2Arr.length; j++) {
            for (int i = 0; i < enemy2Arr[j].length; i++) {
                enemy2Arr[j][i] = temp2.getSubimage(i * ENEMY2_WIDTH_DEFAULT, j * ENEMY2_HEIGHT_DEFAULT, ENEMY2_WIDTH_DEFAULT, ENEMY2_HEIGHT_DEFAULT);
            }
        }

    }

    public void resetAllEnemies() {
        for(Enemy1 e : enemies1) {
            e.resetEnemy();
        }
        for(Enemy2 e2 : enemies2) {
            e2.resetEnemy();
        }
    }
}
