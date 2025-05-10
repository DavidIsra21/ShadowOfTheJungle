package entities;

import gamestates.Playing;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] enemy1Arr;
    private ArrayList<Enemy1> enemies1 = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();

        addEnemies();
    }

    private void addEnemies() {
        enemies1 = LoadSave.GetEnemies1();
        System.out.println("size of enemies1: " + enemies1.size());
    }

    public void update(int[][] lvlData) {
        for(Enemy1 e : enemies1) {
            e.update(lvlData);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawEnemy1(g, xLvlOffset);
    }

    private void drawEnemy1(Graphics g, int xLvlOffset) {
        for(Enemy1 e : enemies1) {
            System.out.println("enemyState: "+e.getEnemyState()+" enemyIndex: "+e.getAniIndex());
            e.drawHitbox(g, xLvlOffset);
            g.drawImage(enemy1Arr[e.getEnemyState()][e.getAniIndex()], (int)e.getHitbox().x - xLvlOffset - ENEMY1_DRAWOFFSET_X, (int)e.getHitbox().y - ENEMY1_DRAWOFFSET_Y, ENEMY1_WIDTH, ENEMY1_HEIGHT, null);
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
}
