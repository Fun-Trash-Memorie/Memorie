package application;

import application.camera.Cam;
import application.sound.SoundSystem;
import application.window.Window;
import application.window.game.GamePanel;
import application.window.game.MainMenuPanel;

import org.opencv.core.Core;

import java.awt.*;

public class Main {

    public static Cam cam;
    public static SoundSystem soundSystem;
    public static MainMenuPanel mainMenuPanel;
    public static GamePanel gamePanel;
    public static Window window;

    public static void main(String[] args) {

        //OpenCV Library laden
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.out.println("loading Library was successful!");
        } catch (Exception e) {
            System.err.println("failed loading Library.");
        }

        //Fenster und Panels initialisieren (gamePanel für später)
        window = new Window();
        mainMenuPanel = new MainMenuPanel();
        gamePanel = new GamePanel();

        //Hauptmenü zum Fenster zuweisen und anzeigen
        window.add(mainMenuPanel, BorderLayout.CENTER);
        window.setVisible(true);
    }
}