package application.game.panels;

import application.window.ConstructionHelper;
import application.game.rendering.Renderer;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

public class GamePanel extends JPanel implements ConstructionHelper {

    public GamePanel() {

        // Spielbildschirm wird eingestellt
        setBounds(0, 0, width, height);
        setBackground(bg_color1);
        setLayout(null);

        // Einstellungen zum Rendern werden geladen
        GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // Spielszene wird geladen und dem Spielbildschirm hinzugefügt
        GLCanvas canvas = new Renderer(capabilities);
        canvas.setBounds(0, 0, width, height);
        add(canvas);

        // Animator wird gestartet um das Bild vom Rendering zu aktualisieren; Festgesetzt auf 60 FPS (Bilder pro Sekunde)
        FPSAnimator animator = new FPSAnimator(canvas, 60, true);
        animator.start();

        setVisible(true);
    }
}