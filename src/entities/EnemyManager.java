package entities;

import Main.Game;
import Traps.Projectile;
import Traps.TrapManager;
import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Numbers.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] enemy1Arr;
    private BufferedImage[][] enemy2Arr;
    private BufferedImage[][] chestArr;
    private BufferedImage[] number;
    private ArrayList<Enemy1> enemies1 = new ArrayList<>();
    private ArrayList<Enemy2> enemies2 = new ArrayList<>();
    private ArrayList<Chest> chest = new ArrayList<>();
    private int numEnemies;
    private boolean chestOpened = false;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        loadNumberImg();
    }

    private void loadNumberImg() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.NUMBERS);
        number = new BufferedImage[14];

        for (int i = 0; i < number.length; i++) {
            if (i < 7) {
                number[i] = img.getSubimage(8 * i, 0, 8, 8);
            } else {
                number[i] = img.getSubimage(8 * (i-7), 8, 8, 8);
            }
        }
    }

    public void loadEnemies(Level level) {
        enemies1 = level.getEnemies1();
        enemies2 = level.getEnemies2();
        chest = level.getChest();
        chestOpened = false;
        setNumEnemies(enemies1.size());
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
        for(Chest c : chest) {
            c.update(lvlData, player);
            if(!c.active && c.getAniIndex()==2)
                chestOpened = true;
        }

        if(!isAnyActive && chestOpened) {
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
        drawChest(g, xLvlOffset);
        drawNumbers(g, xLvlOffset);
    }

    private void drawChest(Graphics g, int xLvlOffset) {
        for(Chest c : chest)
            if(c.isActive()) {
                g.drawImage(chestArr[c.getState()][c.getAniIndex()],
                        (int)c.getHitbox().x - xLvlOffset - CHEST_DRAWOFFSET_X,
                        (int)c.getHitbox().y - CHEST_DRAWOFFSET_Y + (int)(2 * Game.SCALE),
                        CHEST_WIDTH + (int)(10 * Game.SCALE),
                        CHEST_HEIGHT + (int)(10 * Game.SCALE), null);
//                c.drawHitbox(g, xLvlOffset);
            } else{
                g.drawImage(chestArr[c.getState()][15],
                        (int)c.getHitbox().x - xLvlOffset - CHEST_DRAWOFFSET_X,
                        (int)c.getHitbox().y - CHEST_DRAWOFFSET_Y + (int)(2 * Game.SCALE),
                        CHEST_WIDTH + (int)(10 * Game.SCALE),
                        CHEST_HEIGHT + (int)(10 * Game.SCALE), null);
//                c.drawHitbox(g, xLvlOffset);

            }

    }

    private void drawNumbers(Graphics g, int xLvlOffset) {
        if(numEnemies >= 0) {
            if(numEnemies <= 4)
                g.drawImage(number[numEnemies+2], 20, 70, NUMBER_WIDTH, NUMBER_HEIGHT, null);
            else {
                g.drawImage(number[numEnemies-4], 20, 70, NUMBER_WIDTH, NUMBER_HEIGHT, null);
            }
        }
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
                    if(e.currentHealth <= 0) {
                        changeNumEnemies(-1);
                    }
                    return;
                }
            }
        for (Chest c : chest)
            if (c.isActive() && c.getState()!=OPEN_CHEST) {
                if(attackBox.intersects(c.getHitbox())) {
                    c.hurt(10);
                    if(c.currentHealth <= 0) {
//                        chestOpened = true;
                    }
                    return;
                }
            }
    }

    private void loadEnemyImgs() {
        enemy1Arr = new BufferedImage[6][14];
        enemy2Arr = new BufferedImage[6][14];
        chestArr = new BufferedImage[3][16];

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

        BufferedImage temp3 = LoadSave.GetSpriteAtlas(LoadSave.CHEST);
        for(int j = 0; j < chestArr.length; j++) {
            for (int i = 0; i < chestArr[j].length; i++) {
                chestArr[j][i] = temp3.getSubimage(i * CHEST_WIDTH_DEFAULT, j * CHEST_HEIGHT_DEFAULT, CHEST_WIDTH_DEFAULT, CHEST_HEIGHT_DEFAULT);
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
        for(Chest c : chest) {
            c.resetEnemy();
        }
        numEnemies = enemies1.size();
    }

    public void changeNumEnemies(int value) {
        numEnemies += value;
    }

    public int getNumEnemies() {
        return numEnemies;
    }

    public void setNumEnemies(int numEnemies) {
        this.numEnemies = numEnemies;
    }
}
