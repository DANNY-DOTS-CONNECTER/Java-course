package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.rankings.UserDaoImp;

import java.util.LinkedList;

/**
 * 中等难度模式
 * @author Zhoudanni
 */
public class MediumMode extends Game{

    int difficulty = 0;

    int levelCount;

    /**
     * 中等模式构造方法，调用父类的无参Game构造方法
     */
    public MediumMode() {
        super();
        this.enemyMaxNumber = 13;
        this.cycleDuration = 400;
        this.bossScoreThreshold = 500;
        this.portion = 0.9;
    }

    @Override
    protected void createEnemyAircraft() {
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (Math.random() >= portion) {
                enemyFactory = new EliteEnemyFactory();
                AbstractEnemy enemy = enemyFactory.createOperation();
                enemy.setHp(90);
                enemy.setPower(20 + levelCount);
                enemyAircrafts.add(enemy);
            } else {
                enemyFactory = new MobEnemyFactory();
                AbstractEnemy mobEnemy = enemyFactory.createOperation();
                mobEnemy.setSpeedY(10);
                enemyAircrafts.add(mobEnemy);
            }
        }

        levelCount = (super.currentTime / cycleDuration);
        if (portion > 0.7 && levelCount % 70 == 1) {
            difficulty++;
            System.out.print("提高难度！当前LevelCount 等于 " + difficulty);
            portion -= 0.02;
            System.out.println("，当前的精英敌机概率为" + (1 - portion));
        }
    }

    @Override
    protected void createBossEnemy() {
        if (!bossFlag && score > bossScoreThreshold) {
            BossEnemyFactory bossEnemyFactory = new BossEnemyFactory();
            AbstractEnemy boss = bossEnemyFactory.createOperation();
            boss.setHp(400);
            enemyAircrafts.add(boss);
            bossFlag = true;
            bossScoreThreshold += 500;

            //bossBgm
            MusicThread bossBgm = new MusicThread("src/videos/bgm_boss.wav");
            musicThreads[1] = bossBgm;
            bossBgm.start();
        }
    }

}
