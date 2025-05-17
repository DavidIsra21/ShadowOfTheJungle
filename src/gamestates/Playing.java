package gamestates;

import Main.Game;
import Traps.TrapManager;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PausedOverlay;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import static utilz.Constants.Enviroment.*;

public class Playing extends State implements Statemethods{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private TrapManager trapManager;
    private PausedOverlay pausedOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean paused = false;


    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int maxLvlOffsetX;

    private BufferedImage backgroundImg, lianasImg, bgLejanoImg;

    private boolean gameOver;
    private boolean lvlCompleted = false;

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        lianasImg = LoadSave.GetSpriteAtlas(LoadSave.BG_LIANAS);
        bgLejanoImg = LoadSave.GetSpriteAtlas(LoadSave.BG_LEJANO);

        calcLvlOffset();
        loadStartLevel();
    }

    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        trapManager.loadTraps(levelManager.getCurrentLevel());

    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        trapManager = new TrapManager(this);

        //player = new Player(200, 200, (int)(64*SCALE), (int)(40*SCALE));
        player = new Player(100, 200, (int)(124*game.SCALE), (int)(100*game.SCALE), this);
        //player = new Player(150, 200, (int)(192*SCALE), (int)(134*SCALE));

        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        pausedOverlay = new PausedOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
    }

    @Override
    public void update() {
        if(paused) {
            pausedOverlay.update();
        }else if(lvlCompleted) {
            levelCompletedOverlay.update();
        }else if(!gameOver) {
            levelManager.update();
            trapManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player, trapManager);
            checkCloseToBorder();
        }

    }

    private void checkCloseToBorder() {
        int playerX = (int) (player.getHitbox().x);
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if(xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if(xLvlOffset < 0)
            xLvlOffset = 0;

    }

    @Override
    public void draw(Graphics g) {
        for(int i = 0; i < 3; i++)
            g.drawImage(backgroundImg, i * Game.GAME_WIDTH - (int) (xLvlOffset * 0.3), 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        drawLianas(g);

        levelManager.draw(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        trapManager.draw(g, xLvlOffset);


        if(paused) {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pausedOverlay.draw(g);
        } else if(gameOver) {
            gameOverOverlay.draw(g);
        } else if(lvlCompleted) {
            levelCompletedOverlay.draw(g);
        }

    }

    private void drawLianas(Graphics g) {

        for(int i = 0; i < 3; i++)
            g.drawImage(bgLejanoImg, i * Game.GAME_WIDTH - (int) (xLvlOffset * 0.5), 22, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);


        for(int i = 0; i < 3; i++)
            g.drawImage(lianasImg, i * BG_LIANAS_WIDTH - (int) (xLvlOffset * 0.7), 0, BG_LIANAS_WIDTH, BG_LIANAS_HEIGHT, null);
        //220

    }

    public void resetAll() {
        //TODO: reset playing, enemy, lvl, etc
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkSpikesTouched(Player p) {
        trapManager.checkSpikesTouched(p);
    }

    public void checkSpearsTouched(Player p) {
        trapManager.checkSpearTouched(p);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        if (e.getButton() == MouseEvent.BUTTON1) {
//            player.setAttacking(true);
//        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver) {
            if (paused) {
                pausedOverlay.mousePressed(e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mousePressed(e);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver) {
            if (paused) {
                pausedOverlay.mouseReleased(e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver) {
            if (paused) {
                pausedOverlay.mouseMoved(e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mouseMoved(e);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed(e);
        else
            switch(e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
                case KeyEvent.VK_K:
                    player.setAttacking(true);
                    break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
            switch(e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void unpauseGame(){
        paused = false;
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public TrapManager getTrapManager() {
        return trapManager;
    }
}
