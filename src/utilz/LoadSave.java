package utilz;

import Main.Game;
import entities.Enemy1;

import static utilz.Constants.EnemyConstants.ENEMY1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player.png";
    public static final String LEVEL_ATLAS = "outside_tiles.png";
    //public static final String LEVEL_ATLAS = "outside_sprites.png";
//    public static final String LEVEL_ONE_DATA = "level_one_data3.png";
    public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlasBlue2.png";
    public static final String MENU_TITLE = "titulo.png";
    public static final String MENU_BACKGROUND = "menu_backgroundBlue.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "fondoMenu.jpg";
    public static final String PLAYING_BG_IMG = "bg_fondo2.png";
    public static final String BG_LIANAS = "lianas.png";
    public static final String BG_LEJANO = "bg_marco2.png";
    public static final String ENEMY1_SPRITE = "Enemy_basic.png";
    public static final String STATUS_BAR = "empty_bar.png";
    public static final String HEALTH_BAR = "health_bar.png";
    public static final String ENERGY_BAR = "energy_bar.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream( "/" + fileName );
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static ArrayList<Enemy1> GetEnemies1() {
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Enemy1> list = new ArrayList<>();
        for(int j = 0; j < img.getHeight(); j++) {
            for(int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if(value == ENEMY1)
                    list.add(new Enemy1(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        }
        return list;
    }

    public static int[][] GetLevelData() {
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for(int j = 0; j < img.getHeight(); j++) {
            for(int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if(value >= 20)
                    value = 0;
//                if(value >= 48)
//                    value = 0;
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }
}
