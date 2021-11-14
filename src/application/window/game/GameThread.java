package application.window.game;

import application.Main;

public class GameThread extends Thread {

    public static GamePanel gamePanel;

    public void run() {
        System.out.println("GameThread startet");
        Thread t1 = new Thread(() -> {
            while (true) {
                setDaemon(true);
                if (Main.mainMenuPanel.isPvePressed()) {
                    System.out.println("PvE wurde ausgewählt.");

                    Main.mainMenuPanel.setVisible(false);
                    Main.mainMenuPanel.setPvePressed(false);

                    Main.window.repaint();


                    gamePanel = new GamePanel();
                    Main.window.add(gamePanel);

                }
            }
        });
        t1.start();
    }
}