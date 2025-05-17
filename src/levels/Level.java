package levels;

import Main.Game;
import Traps.Spike;
import entities.Enemy1;
import utilz.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetEnemies1;
import static utilz.HelpMethods.GetPlayerPointSpawn;



public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Enemy1> enemies;
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
        enemies = GetEnemies1(img);
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

    public ArrayList<Enemy1> getEnemies() {
        return enemies;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
}
