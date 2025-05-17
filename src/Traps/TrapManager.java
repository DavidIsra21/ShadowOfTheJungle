package Traps;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.TrapConstants.*;

public class TrapManager {
    private Playing playing;
    private BufferedImage[] spikeImg;
    private ArrayList<Spike> spikes;

    public TrapManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }

    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes)
            if (s.getHitbox().intersects(p.getHitbox()))
                p.kill();
    }

    public void loadTraps(Level newLevel) {
        spikes = newLevel.getSpikes();
    }


    private void loadImgs() {
        BufferedImage SpikeSprite = LoadSave.GetSpriteAtlas(LoadSave.SPIKE);
        spikeImg = new BufferedImage[10];

        for (int i = 0; i < spikeImg.length; i++)
                spikeImg[i] = SpikeSprite.getSubimage(32 * i, 0, 32, 16);

    }

    public void update() {
        for (Spike s : spikes)
                s.update();

    }

    public void draw(Graphics g, int xLvlOffset) {
        drawTraps(g, xLvlOffset);
//        for (Spike s : spikes)
//            s.drawHitbox(g, xLvlOffset);
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for (Spike s : spikes)
            g.drawImage(spikeImg[s.getAniIndex()], (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);

    }

}
