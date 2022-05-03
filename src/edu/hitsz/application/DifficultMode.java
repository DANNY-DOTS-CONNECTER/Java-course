package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.BossEnemyFactory;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.MobEnemyFactory;

/**
 * 困难模式
 *
 * @author Zhoudanni
 */
public class DifficultMode extends Game {

    /**
     * 召唤boss敌机次数
     */
    int cnt = 0;

    int difficulty = 0;

    int levelCount = (super.currentTime / cycleDuration);

    public DifficultMode() {
        super();
        this.enemyMaxNumber = 15;
        this.bossScoreThreshold = 550;
        this.cycleDuration = 320;
        this.portion = 0.85;
    }

    @Override
    protected void createEnemyAircraft() {

        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (Math.random() >= portion) {
                enemyFactory = new EliteEnemyFactory();
                AbstractEnemy enemy = enemyFactory.createOperation();
                enemy.setHp(60);
                enemy.setPower(30 + difficulty);
                enemyAircrafts.add(enemy);
            } else {
                enemyFactory = new MobEnemyFactory();
                AbstractEnemy mobEnemy = enemyFactory.createOperation();
                mobEnemy.setSpeedY(12);
                enemyAircrafts.add(mobEnemy);
            }
        }

        levelCount = (super.currentTime / cycleDuration);
        if (portion > 0.65 && levelCount % 50 == 1 && levelCount != 1) {
            difficulty++;
            System.out.println("当前LevelCount 等于 " + difficulty);
            portion -= 0.03;
            System.out.println("当前的精英敌机概率为" + (1 - portion) + "，当前敌机子弹更强大了！");
        }
    }

    @Override
    protected void createBossEnemy() {

        if (!bossFlag && score > bossScoreThreshold) {
            BossEnemyFactory bossEnemyFactory = new BossEnemyFactory();
            AbstractEnemy boss = bossEnemyFactory.createOperation();
            //每次召唤提升Boss机血量
            boss.setHp(450 + cnt * 10);
            System.out.println("当前Boss敌机的血量为：" + boss.getHp());
            boss.setShootNum(3 + cnt);
            System.out.println("boss敌机子弹数量为" + (3 + cnt));
            enemyAircrafts.add(boss);
            bossFlag = true;
            bossScoreThreshold += 600;
            ++cnt;

            //bossBgm
            MusicThread bossBgm = new MusicThread("src/videos/bgm_boss.wav");
            musicThreads[1] = bossBgm;
            bossBgm.start();
        }
    }

}
