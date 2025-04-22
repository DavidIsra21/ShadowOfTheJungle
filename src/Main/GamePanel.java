package Main;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimations();

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);

    }

    private void loadAnimations() {
        animations = new BufferedImage[14][6];

        for(int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++)
            {
                //idleAni[i] = img.getSubimage(i*284, 9*186, 284, 186);
                animations[j][i] = img.getSubimage(i*244, j*186, 244, 186);
            }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/res/player.png");

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
    }

    private void setPanelSize() {

        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);

    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    public void setMoving(boolean moving)
    {
        this.moving = moving;
    }

    private void updateAnimationTick() {

        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction))
                aniIndex = 0;
        }

    }

    private void setAnimation() {

        if(moving)
        {
            playerAction = RUNNING;
        }
        else {
            playerAction = IDLE;
        }
    }

    private void updatePos() {

        if(moving) {
            switch(playerDir) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;
            }
        }
    }

    public void updateGame() {
        updateAnimationTick();
        setAnimation();
        updatePos();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        float sizeMultiplier = (float) 1.2;



        //g.drawImage(subImg, 0, 0, 568, 372, null);
        //Player: 244x186
        g.drawImage(animations[playerAction][aniIndex], (int)xDelta, (int)yDelta, (int)(244*sizeMultiplier), (int)(186*sizeMultiplier), null);
        //g.drawImage(animations[playerAction][aniIndex], (int)xDelta, (int)yDelta, (int)(200*sizeMultiplier), (int)(325*sizeMultiplier), null);
        g.drawRect((int)xDelta, (int)yDelta, (int)(244*sizeMultiplier), (int)(186*sizeMultiplier));


    }




}
