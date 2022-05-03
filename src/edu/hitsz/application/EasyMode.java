package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.EliteEnemyFactory;
import edu.hitsz.aircraft.MobEnemyFactory;

/**
 * 简单模式
 * @author Zhoudanni
 */
public class EasyMode extends Game{

    public EasyMode() {
        super();
        this.enemyMaxNumber = 3;
        this.cycleDuration = 500;
        this.portion = 0.9;
    }

    @Override
    protected void createEnemyAircraft() {
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (Math.random() >= portion) {
                enemyFactory = new EliteEnemyFactory();
                AbstractEnemy enemy = enemyFactory.createOperation();
                enemy.setHp(30);
                enemy.setSpeedY(5);
                enemyAircrafts.add(enemy);
            } else {
                //产生普通敌机
                enemyFactory = new MobEnemyFactory();
                AbstractEnemy mobEnemy = enemyFactory.createOperation();
                mobEnemy.setSpeedY(6);
                enemyAircrafts.add(mobEnemy);
            }
        }
    }

    @Override
    protected void createBossEnemy() {

    }
}
