package edu.hitsz.application;

import edu.hitsz.swing.RankingPanel;
import edu.hitsz.swing.StartPage;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Object lock = new Object();

    //这里添加过throws InterruptedException
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        //关闭窗口时停止游戏
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //新建一个startPage对象，创建起始页面
        StartPage startPage = new StartPage();
        JPanel startMainPage = startPage.getMainPanel();
        frame.setContentPane(startMainPage);
        frame.setVisible(true);

        synchronized (Main.lock){
            (Main.lock).wait();
        }

        frame.remove(startMainPage);
        Game game = new Game();
        frame.setContentPane(game);
//        frame.add(game);
        //有一张ContentPane在上面，空板子，找不到纸
        frame.setVisible(true);
        game.action();

        synchronized (Main.lock){
            (Main.lock).wait();
        }

        //新建一个rankingPanel对象，创建排行榜界面，如果用户选择不添加新数据，就展示原始界面
        frame.remove(game);
        RankingPanel rankingPanel = new RankingPanel();
        JPanel rankingMainPanel = rankingPanel.getMainPanel();
        frame.setContentPane(rankingMainPanel);
        frame.setVisible(true);

        synchronized (Main.lock){
            (Main.lock).wait();
        }

        //用户选择添加新数据，刷新页面
        if(game.newDataAddedFlag){
            //重新new一下RankingPanel()
            rankingPanel = new RankingPanel();
            rankingMainPanel = rankingPanel.getMainPanel();
            frame.setContentPane(rankingMainPanel);
            frame.setVisible(true);
        }
    }
}
