package edu.hitsz.swing;

import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Zhoudanni
 */
public class StartPage {
    private JPanel mainPanel;
    private JPanel Panel1;
    private JPanel Panel3;
    private JPanel Panel2;
    private JButton easyField;
    private JButton mediumField;
    private JButton hardField;
    private JPanel musicPanel;
    private JRadioButton musicOnField;
    private JRadioButton musicOffField;
    private JLabel pictureLabel;
    private JPanel Panel4;
    public static boolean musicFlag = false;

    public StartPage() {

        easyField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.level = 1;
                ImageManager.setBackgroundImage("src/images/bg7.jpg");
                synchronized (Main.lock){
                    (Main.lock).notify();
                }
            }
        });
        mediumField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.level = 2;
                ImageManager.setBackgroundImage("src/images/bg8.jpg");
                synchronized (Main.lock){
                    (Main.lock).notify();
                }
            }
        });
        hardField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.level = 3;
                ImageManager.setBackgroundImage("src/images/background.png");
                synchronized (Main.lock){
                    (Main.lock).notify();
                }
            }
        });

        //设置为一个按键组
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(musicOnField);
        btnGroup.add(musicOffField);

        musicOffField.setSelected(true);
        musicOnField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicFlag = true;
            }
        });

        musicOffField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicFlag = false;
            }
        });

        pictureLabel.setIcon(new ImageIcon("src/images/start.png"));

    }//end constructor

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartPage");
        frame.setContentPane(new StartPage().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


//    public Game getGame(){
//        return game;
//    }
}
