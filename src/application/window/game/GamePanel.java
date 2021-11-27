package application.window.game;

import application.Main;
import application.window.game.rendering.Renderer;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public GamePanel() {
        setSize(Main.window.getPreferredSize());
        this.setBackground(Color.green);

        GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);

        GLCanvas canvas = new Renderer(capabilities);
        canvas.setSize(new Dimension(Main.window.getWidth(), Main.window.getHeight()));
        FPSAnimator animator = new FPSAnimator(canvas, 60, true);

        add(canvas, BorderLayout.CENTER);
        animator.start();

        Thread t1 = new Thread(() -> {
            System.out.println("Game Window wurde gestartet");
            while (true) {
                Main.window.setTitle(("Memory | " + animator.getFPS()));
            }
        });t1.start();

    }
}