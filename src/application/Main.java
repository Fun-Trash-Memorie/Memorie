package application;

import application.camera.CamThread;
import application.sound.SoundSystem;
import application.window.MainMenu.MainMenu;
import org.opencv.core.Core;

public class Main {

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
}
