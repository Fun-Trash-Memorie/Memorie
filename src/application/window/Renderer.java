package application.window;

import application.Main;
import application.window.game.GameThread;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import java.awt.*;


public class Renderer implements GLEventListener {
    final GLProfile profile;
    GLCapabilities capabilities;
    final GLCanvas glCanvas;

    public Renderer() {

        profile = GLProfile.get(GLProfile.GL2);
        capabilities = new GLCapabilities(profile);
        glCanvas = new GLCanvas(capabilities);
        glCanvas.addGLEventListener(this);
        glCanvas.setSize(new Dimension(Main.window.getWidth() /2, Main.window.getHeight() / 2));
        glCanvas.setBackground(Color.black);
        GameThread.gamePanel.add(glCanvas);
    }


    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
