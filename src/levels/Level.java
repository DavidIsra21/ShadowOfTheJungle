package levels;

import Main.Game;
import Traps.Spike;
import entities.Enemy1;
import entities.Enemy2;
import utilz.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.HelpMethods.*;


public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Enemy1> enemies1;
    private ArrayList<Enemy2> enemies2;
    private ArrayList<Spike> spikes;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;


    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        createSpikes();
        calcLvlOffsets();
        calcPlayerSpawn(img);
    }

    private void createSpikes() {
        spikes = HelpMethods.GetSpikes(img);
    }

    private void calcPlayerSpawn(BufferedImage img) {
        playerSpawn = GetPlayerPointSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        enemies1 = GetEnemies1(img);
        enemies2 = GetEnemies2(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public ArrayList<Enemy1> getEnemies1() {
        return enemies1;
    }

    public ArrayList<Enemy2> getEnemies2() {
        return enemies2;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
}
