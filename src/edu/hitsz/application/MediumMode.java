package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.BossEnemyFactory;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.MobEnemyFactory;
import edu.hitsz.booster.PropBullet;

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
        if (portion > 0.7 && levelCount % 70 == 1 && levelCount != 1) {
            difficulty++;
            System.out.print("提高难度！当前LevelCount 等于 " + difficulty);
            portion -= 0.02;
            System.out.println("，当前的精英敌机概率为" + (1 - portion) + "，敌机已提高属性" );
            //为了平衡游戏感，每次提升难度后都延长火力道具生效的时间
            PropBullet.increaseValidDuration();
            System.out.println("当前火力道具生效时长为：" + PropBullet.validDuration);
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
