package gamestates;

import Main.Game;
import ui.MenuButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements Statemethods{
    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg, fondoMenuImg, titleImg;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
        fondoMenuImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        titleImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_TITLE);
    }

    private void loadBackground() {
        BufferedImage tempImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
//        menuWidth = (int)(64*Game.SCALE);
//        menuHeight = (int)(64*Game.SCALE);
        backgroundImg = tempImg.getSubimage(1*64, 1*64, 64, 64);
        menuWidth = (int)(backgroundImg.getWidth() * Game.SCALE * 6);
        menuHeight = (int)(backgroundImg.getHeight() * Game.SCALE * 6);
        menuX = Game.GAME_WIDTH / 2 - menuWidth/2;
        menuY = (int) ( 55 * Game.SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (180 * Game.SCALE), 8, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (250 * Game.SCALE), 7, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (320 * Game.SCALE), 9, Gamestate.QUIT);

    }

    @Override
    public void update() {
        for (MenuButton mb : buttons) {
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(fondoMenuImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
        g.drawImage(titleImg, menuX + (int)(menuWidth/6), 0, (int)(256*Game.SCALE), (int)(170*Game.SCALE), null);
        for (MenuButton mb : buttons) {
            mb.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
                break;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed())
                    mb.applyGameState();
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons) {
            mb.setMouseOver(false);
        }

        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Gamestate.state = Gamestate.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
