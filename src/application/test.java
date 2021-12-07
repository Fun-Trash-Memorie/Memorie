package application;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class test {
    public static void main(String[]args) throws IOException {

        BufferedImage img = ImageIO.read(new File("img/2021-12-06-03-42-25.jpg"));

        int Range = 5, xStart = img.getWidth() / 8, xEnd = img.getWidth() - xStart, yStart = img.getHeight() / 8, yEnd = img.getHeight() - yStart;

        for (int x = xStart - Range / 2; x < xEnd + Range / 2; x++) {
            for (int y = yStart - Range / 2; y < yStart + Range / 2; y++) {
                System.out.println(y + " " + x);
            }
        }
    }
}
