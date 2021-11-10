package application.window;

import application.camera.CamThread;
import application.sound.SoundSystem;
import application.window.MainMenu.MainMenu;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import org.opencv.core.Core;

public class Renderer implements GLEventListener {

    public static CamThread camThread;
    public static SoundSystem soundSystem;
    public static MainMenu mainMenu;

    public static void main(String[] args) {

        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.out.println("loading Library was successful!");
        } catch (Exception e) {
            System.err.println("failed loading Library.");
        }

        mainMenu = new MainMenu();

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
