package edu.hitsz.swing;

import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import javax.swing.*;
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
    private JButton hardField;
    private JButton mediumField;
    private JPanel musicPanel;
    private JRadioButton musicOnField;
    private JRadioButton musicOffField;
    public static boolean musicFlag = true;

    public StartPage() {

        easyField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.level = 1;
                ImageManager.setBackgroundImage("src/images/bg2.jpg");
                synchronized (Main.lock){
                    (Main.lock).notify();
                }
            }
        });
        hardField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.level = 2;
                ImageManager.setBackgroundImage("src/images/bg3.jpg");
                synchronized (Main.lock){
                    (Main.lock).notify();
                }
            }
        });
        mediumField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.level = 3;
                ImageManager.setBackgroundImage("src/images/bg4.jpg");
                synchronized (Main.lock){
                    (Main.lock).notify();
                }
            }
        });

        //设置为一个按键组
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(musicOnField);
        btnGroup.add(musicOffField);

        musicOnField.setSelected(true);
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
}
