package application.window;

import java.awt.*;

public interface ConstructionHelper {

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    int width = (int)(gd.getDisplayMode().getWidth() / 1.5);
    int height = (int)(gd.getDisplayMode().getHeight() / 1.5);

    int margin = 5;
    int padding = 10;

    int btn_width = 400;
    int btn_height = 90;

    int hp_width = width;
    int hp_height = height / 6;

    int header_width = 340;
    int header_height = hp_height;

    int bottomFiller = 40;
    int rightFiller = 15;

    Font headFont = new Font("Comic Sans MS", Font.BOLD, (int)(header_width/4.2));
    Color headColor = new Color(200, 100, 0);

    Font buttonFont = new Font("Comic Sans MS", Font.PLAIN, btn_width/8);
    Color buttonColor = new Color(200, 200, 200);

    Color bg_color1 = new Color(48,48,48);
    Color bg_color2 = new Color(94,94,94);

    Color fg_color1 = new Color(120, 60, 0);
}