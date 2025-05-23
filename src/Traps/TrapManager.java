package Traps;

import Main.Game;
import entities.Player;
import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.TrapConstants.*;
import static utilz.Constants.Projectiles.*;
import static utilz.HelpMethods.IsProjectileHittingLevel;

public class TrapManager {
    private Playing playing;
    private BufferedImage[] spikeImg;
    private BufferedImage[] spearImg;
    private BufferedImage[] chessImg;
    private BufferedImage e2_BallImg;
    private ArrayList<Spike> spikes;
    private ArrayList<Spear> spears;
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public TrapManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }

    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes)
            if (s.getHitbox().intersects(p.getHitbox()))
                p.kill();
    }

    public void checkSpearTouched(Player p) {
        for (Spear sp : spears)
            if (sp.getHitbox().intersects(p.getHitbox()))
                p.kill();
    }

    public void loadTraps(Level newLevel) {
        spikes = newLevel.getSpikes();
        spears = newLevel.getSpears();
        projectiles.clear();
    }


    private void loadImgs() {
        BufferedImage SpikeSprite = LoadSave.GetSpriteAtlas(LoadSave.SPIKE);
        BufferedImage SpearSprite = LoadSave.GetSpriteAtlas(LoadSave.SPEAR);
        spikeImg = new BufferedImage[10];
        spearImg = new BufferedImage[8];

        for (int i = 0; i < spikeImg.length; i++)
            spikeImg[i] = SpikeSprite.getSubimage(32 * i, 0, 32, 16);

        for (int i = 0; i < spearImg.length; i++)
            spearImg[i] = SpearSprite.getSubimage(32 * i, 0, 32, 32);


        e2_BallImg = LoadSave.GetSpriteAtlas(LoadSave.E2_BALL);
    }

    public void update(int[][] lvlData, Player player) {
        for (Spike s : spikes)
                s.update();

        for (Spear sp : spears)
            sp.update();

        updateProjectiles(lvlData, player);
    }

    private void updateProjectiles(int[][] lvlData, Player player) {
        for (Projectile p : projectiles) {
            if (p.isActive())
                p.updatePos();
            if (p.getHitbox().intersects(player.getHitbox())) {
                if(p.isActive())
                    player.changeHealt(-1);
                p.setActive(false);
            } else if (IsProjectileHittingLevel(p, lvlData))
                p.setActive(false);
        }
    }



    public void draw(Graphics g, int xLvlOffset) {
        drawTraps(g, xLvlOffset);
//        for (Spike s : spikes)
//            s.drawHitbox(g, xLvlOffset);

//        for (Spear sp : spears)
//            sp.drawHitbox(g, xLvlOffset);

        drawProjectiles(g, xLvlOffset);
    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
        int drawOffsetX = (int) (-18 * Game.SCALE);;
        int drawOffsetY = (int) (-18 * Game.SCALE);

        for (Projectile p : projectiles) {
            if (p.isActive()) {

                g.drawImage(e2_BallImg, (int) (p.getHitbox().x - xLvlOffset) + drawOffsetX, (int) (p.getHitbox().y) + drawOffsetY, E2_BALL_WIDTH, E2_BALL_HEIGHT, null);
//                p.drawHitbox(g, xLvlOffset);
            }
        }
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for (Spike s : spikes)
            g.drawImage(spikeImg[s.getAniIndex()], (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);

        for (Spear sp : spears)
            g.drawImage(spearImg[sp.getAniIndex()], (int) (sp.getHitbox().x - xLvlOffset), (int) (sp.getHitbox().y - sp.getyDrawOffset()), SPEAR_WIDTH, SPEAR_HEIGHT, null);

    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

}
