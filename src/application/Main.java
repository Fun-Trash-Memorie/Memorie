package application;

import application.camera.CamThread;
import application.sound.SoundSystem;
import application.window.Renderer;
import application.window.Window;
import application.window.game.GameThread;
import application.window.game.MainMenuPanel;
import org.opencv.core.Core;

import java.awt.*;

public class Main {

    public static CamThread camThread;
    public static GameThread gameThread;
    public static SoundSystem soundSystem;
    public static MainMenuPanel mainMenuPanel;
    public static Window window;
    public static Renderer renderer;

    public static void main(String[] args) {



        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.out.println("loading Library was successful!");
        } catch (Exception e) {
            System.err.println("failed loading Library.");
        }

        window = new Window();
        mainMenuPanel = new MainMenuPanel();

        window.add(mainMenuPanel, BorderLayout.CENTER);

        window.setVisible(true);



        gameThread = new GameThread();
        gameThread.start();
    }
}