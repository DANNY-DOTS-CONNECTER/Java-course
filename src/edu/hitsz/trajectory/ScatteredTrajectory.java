package edu.hitsz.trajectory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 散射弹道
 *
 * @author Zhoudanni
 */
public class ScatteredTrajectory implements Strategy {

    @Override
    public List<AbstractBullet> selectTrajectory(AbstractAircraft aircraft) {
        List<AbstractBullet> res = new LinkedList<>();
        int y = aircraft.getLocationY() + aircraft.getDirection() * 5;
        int speedY = aircraft.getDirection() * 5;
        int locationX = aircraft.getLocationX();
        int shootNum = aircraft.getShootNum();
        int power = aircraft.getPower();
        AbstractBullet abstractBullet;
        for (int i = 0; i < aircraft.getShootNum(); i++) {
            // 多个子弹横向分散发射
            if (aircraft.getClass() == HeroAircraft.class) {
                abstractBullet = new HeroBullet(locationX + (i * 2 - shootNum + 1) * 10, y, i * 2 - shootNum + 1, speedY, power);
            } else {
                abstractBullet = new EnemyBullet(locationX + (i * 2 - shootNum + 1) * 10, y, i * 2 - shootNum + 1, speedY, power);
            }
            res.add(abstractBullet);
        }
        return res;
    }

}
