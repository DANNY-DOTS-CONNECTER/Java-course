package edu.hitsz.trajectory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 直射弹道
 *
 * @author Zhoudanni
 */
public class StraightTrajectory implements Strategy {

    @Override
    public List<AbstractBullet> selectTrajectory(AbstractAircraft aircraft) {
        List<AbstractBullet> res = new LinkedList<>();
        int y = aircraft.getLocationY() + aircraft.getDirection() * 5;
        int speedX = 0;
        int speedY;
        int locationX = aircraft.getLocationX();
        int shootNum = aircraft.getShootNum();
        int power = aircraft.getPower();
        if (aircraft.getClass() == EliteEnemy.class) {
            speedY = aircraft.getSpeedY() + aircraft.getDirection() * 5;
        } else {
            speedY = aircraft.getDirection() * 5;
        }
        AbstractBullet abstractBullet;
        for (int i = 0; i < aircraft.getShootNum(); i++) {
            // 多个子弹横向分散发射
            if (aircraft.getClass() == HeroAircraft.class) {
                abstractBullet = new HeroBullet(locationX + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            } else {
                abstractBullet = new EnemyBullet(locationX + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            }
            res.add(abstractBullet);
        }
        return res;
    }

}
