package application.window.game.panels;

import application.Main;
import application.window.game.ConstructionHelper;
import application.window.game.rendering.Renderer;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements ConstructionHelper {

    public GamePanel() {

        setBounds(0, 0, width, height);
        setBackground(bg_color1);
        setLayout(null);

        GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);

        GLCanvas canvas = new Renderer(capabilities);
        canvas.setBounds(0, 0, width, height);
        FPSAnimator animator = new FPSAnimator(canvas, 60, true);

        add(canvas);


        animator.start();

        /*
        Thread t1 = new Thread(() -> {
            while (true) {
                Main.window.setTitle(("Memory | " + animator.getFPS()));
            }
        });t1.start();

         */



        setVisible(true);
    }
}