package application.camera;

import application.window.Renderer;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cam extends JFrame {

    private JLabel camLabel;
    private JPanel buttonPanel;
    private JButton captureButton;

    private VideoCapture cap;
    private Mat image;

    private boolean clicked = false, closed = false;

    public Cam() {

        setLayout(new BorderLayout());

        camLabel = new JLabel();
        camLabel.setPreferredSize(new Dimension(640, 680));
        add(camLabel, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.setPreferredSize(new Dimension(640, 180));
        add(buttonPanel, BorderLayout.SOUTH);

        captureButton = new JButton("Klick.");
        captureButton.setPreferredSize(new Dimension(400, 52));
        //captureButton.setBounds(76, 414, 247, 152);
        buttonPanel.add(captureButton, BorderLayout.CENTER);

        captureButton.addActionListener(e -> {
            clicked = true;
        });

        setSize(new Dimension(640, 580));
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);

            }
        });

        }

    public void startCam(){

        cap = new VideoCapture(0);
        image = new Mat();
        byte[] imageData;

        ImageIcon icon;

        while(true) {
            cap.read(image);
            HighGui.imshow("WebcamTest", image);
            HighGui.waitKey(1);
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buf);

            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            camLabel.setIcon(icon);

            if(clicked) {
                String name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
                Imgcodecs.imwrite("img/" + name + ".jpg", image);
                clicked = false;
            }
        }
    }

}
