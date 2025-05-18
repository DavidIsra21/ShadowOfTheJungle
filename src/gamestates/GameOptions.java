package gamestates;

import Main.Game;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.URMButtons.*;

public class GameOptions extends State implements Statemethods {

    private AudioOptions audioOptions;
    private BufferedImage backgroundImg;
    private UrmButton menuB;

    public GameOptions(Game game) {
        super(game);
        loadImgs();
        loadButtons();
        audioOptions = game.getAudioOptions();
    }

    private void loadButtons() {
        int menuX = (int)(387 * Game.SCALE);
        int menuY = (int)(325 * Game.SCALE);

        menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 0);
    }

    private void loadImgs() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_BG);

    }

    @Override
    public void update() {
        menuB.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        menuB.draw(g);
        audioOptions.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isIn(e,menuB)) {
            menuB.setMousePressed(true);
        }
        else
            audioOptions.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(isIn(e,menuB)) {
            if(menuB.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
            }
        } else
            audioOptions.mouseReleased(e);

        menuB.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);

        if(isIn(e,menuB)) {
            menuB.setMouseOver(true);
        }
        else
            audioOptions.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            Gamestate.state = Gamestate.MENU;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());

    }

}
