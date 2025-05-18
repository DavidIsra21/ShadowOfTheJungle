package ui;

import Main.Game;
import gamestates.Gamestate;

import java.awt.*;
import java.awt.event.MouseEvent;

import static utilz.Constants.UI.PauseButtons.SOUND_SIZE;

public class AudioOptions {

    private SoundButton musicButton, sfxButton;

    public AudioOptions() {
        createSoundButton();
    }

    private void createSoundButton() {
        int soundX = (int)(430* Game.SCALE);
        int musicY = (int)(130*Game.SCALE);
        int sfxY = (int)(176*Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();

    }

    public void draw(Graphics g) {
        //Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

    }

    public void mousePressed(MouseEvent e) {
        if(isIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if(isIn(e, sfxButton))
            sfxButton.setMousePressed(true);

    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(e, musicButton)) {
            if(musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
            musicButton.setMousePressed(true);
        }
        else if(isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed())
                sfxButton.setMuted(!sfxButton.isMuted());
        }

        musicButton.resetBools();
        sfxButton.resetBools();

    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if(isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if(isIn(e, sfxButton))
            sfxButton.setMouseOver(true);

    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());

    }
}
