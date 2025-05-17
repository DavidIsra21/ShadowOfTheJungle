package levels;

import Main.Game;
import entities.EnemyManager;
import gamestates.Gamestate;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Main.Game.TILES_SIZE;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;

    public LevelManager(Game game) {
        this.game = game;
        //levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel() {
        lvlIndex++;
        if(lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("No more levels! Game completed!");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
        game.getPlaying().getTrapManager().loadTraps(newLevel);
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for(BufferedImage img : allLevels) {
            levels.add(new Level(img));
        }
    }

    private void importOutsideSprites() {
        //4x5 tiles = 20
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[20];
        //levelSprite = new BufferedImage[48];
        for (int j = 0; j < 5; j++) { //5 //tutorial es 4
            for (int i = 0; i < 4; i++) { //4 //tutorial es 12
                int index = j * 4 + i; //4
                //levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
                levelSprite[index] = img.getSubimage(i * 128, j * 128, 128, 128);

            }
        }
    }

    public void draw(Graphics g, int LvlOffset) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                //g.drawImage(levelSprite[index], TILES_SIZE*i, TILES_SIZE*j, TILES_SIZE, TILES_SIZE, null);
                g.drawImage(levelSprite[index], TILES_SIZE*i - LvlOffset, TILES_SIZE*j, TILES_SIZE, TILES_SIZE, null);

            }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }


}
