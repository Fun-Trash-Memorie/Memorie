package application.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SubWindow extends JFrame implements ConstructionHelper {

    public SubWindow(String Titel) {

        System.out.println("SubFenster wird kreiert...");
        setTitle(Titel);
        setSize(new Dimension(width/2, height/2));
        setLayout(null);
        setLocationRelativeTo(null);
        setFocusable(true);
        setResizable(false);

        System.out.println("SubFenster kreiert.");

    }
}
