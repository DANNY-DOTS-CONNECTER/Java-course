package edu.hitsz.trajectory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Zhoudanni
 */
public class HeroStraightTrajectory implements Strategy{

    @Override
    public List<AbstractBullet> selectTrajectory(int locationX, int locationY, int shootNum, int direction, int power) {
        List<AbstractBullet> res = new LinkedList<>();
        int x = locationX;
        int y = locationY + direction * 10;
        int speedX = 0;
        int speedY = direction * 5;
        AbstractBullet abstractBullet;
        for (int i = 0; i < shootNum; i++) {
            // 多个子弹横向分散发射
            abstractBullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            res.add(abstractBullet);
        }
        return res;
    }

}
