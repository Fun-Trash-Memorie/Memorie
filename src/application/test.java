package application;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class test {

    static BufferedImage border;
    static BufferedImage screenshot;
    static Rectangle bestMatch = null;

    public static void main(String[]args) throws IOException, AWTException {

        BufferedImage image = ImageIO.read(new File("img/Kart1.png"));
        BufferedImage image2 = ImageIO.read(new File("img/Kart2.png"));

        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(new JLabel(new ImageIcon(image)));
        p.add(new JLabel(new ImageIcon(image2)));
        p.setPreferredSize(new Dimension(image.getWidth()*2 + 10, image.getHeight() + 10));


        JFrame f = new JFrame();

        f.add(p);
        f.pack();
        f.setVisible(true);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //BufferedImage screenshot = new BufferedImage(f.getContentPane().getWidth(), f.getContentPane().getHeight(), BufferedImage.TYPE_INT_RGB);

        Robot robot = new Robot();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            screenshot = robot.createScreenCapture(new Rectangle((int)f.getLocation().getX() + f.getInsets().left, (int)f.getLocation().getY() + f.getInsets().top, f.getWidth() - f.getInsets().left - f.getInsets().right, f.getHeight() - f.getInsets().top - f.getInsets().bottom));
            System.out.println(screenshot);
            try {
                ImageIO.write(screenshot, "jpg", new File("xx.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (picMatcher(image2, screenshot)) {

                try {
                    border = resizeImage(ImageIO.read(new File("src-img/Filter_Overlay.png")), bestMatch.width, bestMatch.height);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                JButton b = new JButton();

                b.setOpaque(false);
                b.setContentAreaFilled(false);
                Border border = BorderFactory.createLineBorder(Color.red);
                b.setBorder(border);
                b.setBounds(bestMatch);

                Component c = f.getComponent(0);
                f.remove(p);

                f.add(b);
                f.add(p);

                //f.pack();
                f.setVisible(false);
                f.repaint();
                f.setVisible(true);

            }

        }).start();

    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        return resizedImage;
    }

    public static boolean picMatcher(BufferedImage filter, BufferedImage screenshot) {

        int w_filter = filter.getWidth();
        int h_filter = filter.getHeight();

        int w_screenshot = screenshot.getWidth();
        int h_screenshot = screenshot.getHeight();

        int size_filter = w_filter * h_filter;
        int size_screenshot = w_screenshot * h_screenshot;
        int matches = 0;

        int max = 0;


        int x = 0;
        int y = 0;



        System.out.println("ScreenshotSize: " + size_screenshot);
        System.out.println("Size: " + size_filter);

        for (int moveX = 0; moveX < w_screenshot; moveX++) {
            for (int moveY = 0; moveY < h_screenshot; moveY++) {

                matches = 0;

                for (x = moveX; x < w_filter; x++) {
                    for (y = moveY; y < h_filter; y++) {

                        if (screenshot.getRGB(x,y) == filter.getRGB(x-moveX,y-moveY)) {
                            matches++;
                        }

                    }
                }

                if (matches > max) {
                    max = matches;

                    bestMatch = new Rectangle(moveX, moveY, x, y);

                }


                System.out.println("x: " + moveX + " y: " + moveY + " matches: " + matches);

            }
        }

        System.out.println("Max Matches: " + max + "/" + size_filter);

        return max > size_filter / 2;
    }
}
