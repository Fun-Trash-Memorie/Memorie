package application;

import application.camera.Cam;
import application.sound.SoundSystem;
import application.window.Window;

import application.window.game.panels.*;
import org.opencv.core.Core;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static Cam cam;
    public static SoundSystem soundSystem;
    public static ArrayList<Thread> soundThreads = new ArrayList<>();
    public static int soundIndex = -1;
    public static MainMenuPanel mainMenuPanel;
    public static GameSelectPanel gameSelectPanel;
    public static LibraryPanel libraryPanel;
    public static SettingPanel settingPanel;
    public static GamePanel gamePanel;
    public static Window window;

    public static void main(String[] args) throws IOException {

        //OpenCV-Bibliothek laden
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            System.out.println("laden der Bibliotheken war erfolgreich!");
        } catch (Exception e) {
            System.err.println("!laden der Bibliotheken fehlgeschlagen!");
        }

        //Fenster und Panels initialisieren (Game-Panel für später)
        window = new Window();
        mainMenuPanel = new MainMenuPanel();
        gamePanel = new GamePanel();
        gameSelectPanel = new GameSelectPanel();
        libraryPanel = new LibraryPanel();
        settingPanel = new SettingPanel();

        //Hauptmenü zum Fenster zuweisen und anzeigen
        window.add(mainMenuPanel);
        window.setVisible(true);
    }
}