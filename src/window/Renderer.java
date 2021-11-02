package window;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import javax.swing.*;
import java.awt.*;

public class Renderer implements GLEventListener {


    public static void main(String[] args) {

        JFrame startFrame = new JFrame("Memory");

            JPanel p = new JPanel();
                p.setPreferredSize(new Dimension(800, 494));
                p.setBackground(Color.DARK_GRAY);
                JPanel headPanel = new JPanel();
                    headPanel.setPreferredSize(new Dimension(800, 200));
                    headPanel.setBackground(Color.LIGHT_GRAY);
                    JLabel header = new JLabel();
                        header.setText("Memory!");
                        Font headFont = new Font("Comic Sans MS", Font.BOLD, 100);
                        header.setFont(headFont);
                        header.setForeground(Color.blue);
                    headPanel.add(header);
                p.add(headPanel, BorderLayout.NORTH);
                JPanel buttonPanel = new JPanel();
                    buttonPanel.setPreferredSize(new Dimension(800, 294));
                    buttonPanel.setBackground(Color.DARK_GRAY);
                    JButton b_PvE = new JButton("PvE");
                        b_PvE.setPreferredSize(new Dimension(390, 200));
                        b_PvE.setBackground(Color.LIGHT_GRAY);
                        Font buttonFont = new Font("Comic Sans MS", Font.PLAIN, 75);
                        b_PvE.setFont(buttonFont);
                    JButton b_PvP = new JButton("PvP");
                        b_PvP.setPreferredSize(new Dimension(390, 200));
                        b_PvP.setBackground(Color.LIGHT_GRAY);
                        b_PvP.setFont(buttonFont);
                    buttonPanel.add(b_PvE, BorderLayout.EAST);
                    buttonPanel.add(b_PvP, BorderLayout.WEST);
                p.add(buttonPanel, BorderLayout.CENTER);
            startFrame.setResizable(false);
            startFrame.add(p);
            startFrame.pack();
            startFrame.setLocationRelativeTo(null);

            startFrame.setVisible(true);

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
}
