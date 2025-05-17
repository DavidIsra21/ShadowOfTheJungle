package utilz;

import Main.Game;
import entities.Enemy1;

import static utilz.Constants.EnemyConstants.ENEMY1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player.png";
    public static final String LEVEL_ATLAS = "outside_tiles.png";
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
    public static final String COMPLETED_IMG = "lvl_complete.png";
    public static final String SPIKE = "Spike.png";


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

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource( "/Lvls");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for(int i = 0; i < filesSorted.length; i++)
            for(int j = 0; j < files.length; j++) {
                if(files[j].getName().equals((i + 1) + ".png")) {
                    filesSorted[j] = files[j];
                }
            }


        BufferedImage[] images = new BufferedImage[filesSorted.length];

        for(int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return images;

    }




}
