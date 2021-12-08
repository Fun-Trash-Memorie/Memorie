package application.window.game.panels;

import application.Main;
import application.window.game.ConstructionHelper;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameSelectPanel extends JPanel implements ConstructionHelper {

    private final JButton b_PvE, b_PvP, b_back;

    public GameSelectPanel() {

        setBounds(0, 0, width, height);
        setBackground(bg_color1);
        setLayout(null);

        b_PvE = new JButton("PvE");
        b_PvE.setBounds(padding, padding, btn_width, btn_height);
        b_PvE.setBackground(buttonColor);
        b_PvE.setFont(buttonFont);
        b_PvE.addActionListener(e -> {
            System.out.println("PvE wurde ausgewählt.");

            Main.gameSelectPanel.setVisible(false);
            Main.window.remove(Main.gameSelectPanel);
            Main.window.repaint();
            Main.gamePanel.setVisible(true);
            Main.window.add(Main.gamePanel);
        });
        b_PvE.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_PvE.setText(">PvE<");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_PvE.setText("PvE");            }
        });


        b_PvP = new JButton("PvP");
        b_PvP.setBounds(width - btn_width- padding, padding, btn_width, btn_height);
        b_PvP.setBackground(buttonColor);
        b_PvP.setFont(buttonFont);
        b_PvP.addActionListener(e -> {
            System.out.println("PvP wurde ausgewählt.");

            Main.gameSelectPanel.setVisible(false);
            Main.window.remove(Main.gameSelectPanel);
            Main.window.repaint();
            Main.gamePanel.setVisible(true);
            Main.window.add(Main.gamePanel);
        });
        b_PvP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_PvP.setText(">PvP<");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_PvP.setText("PvP");            }
        });

        b_back = new JButton("Zurück");
        b_back.setBounds(padding, height-btn_height-bottomFiller-padding, (int)(btn_width/1.5), btn_height);
        b_back.setBackground(buttonColor);
        b_back.setFont(buttonFont);
        b_back.addActionListener(e -> {
            System.out.println("Zurück wurde ausgewählt.");

            Main.gameSelectPanel.setVisible(false);
            Main.window.remove(Main.gameSelectPanel);
            Main.window.repaint();
            Main.mainMenuPanel.setVisible(true);
            Main.window.add(Main.mainMenuPanel);
        });
        b_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b_back.setText(">Zurück<");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                b_back.setText("Zurück");            }
        });

        add(b_PvE);
        add(b_PvP);
        add(b_back);

        setVisible(true);
    }
}
