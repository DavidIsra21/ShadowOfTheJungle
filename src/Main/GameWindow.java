package Main;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GameWindow {
    private final JFrame jframe;

    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();

//
//        jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        jframe.add(gamePanel);
//        jframe.setLocationRelativeTo(null);
//        jframe.setResizable(false);
//        jframe.pack();
//        jframe.setVisible(true);

        jframe.setTitle("Shadow Of The Jungle");
        jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

        });
    }
}
