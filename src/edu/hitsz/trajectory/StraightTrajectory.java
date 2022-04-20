package edu.hitsz.trajectory;

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
    public List<AbstractBullet> selectTrajectory(int locationX, int locationY, int shootNum, int direction, int power) {
        List<AbstractBullet> res = new LinkedList<>();
        int y = locationY + direction * 10;
        int speedX = 0;
        int speedY;
        //如果是MobEnemy发射的speedY需要设置成敌机速度+direction*5
        if (direction == 1 && shootNum == 1) {
            speedY = 7 + direction * 5;
        } else {
            speedY = direction * 5;
        }
        AbstractBullet abstractBullet;
        //判断是否为英雄机
        boolean isHero = (direction == -1);
        for (int i = 0; i < shootNum; i++) {
            // 多个子弹横向分散发射
            if (isHero) {
                abstractBullet = new HeroBullet(locationX + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            } else {
                abstractBullet = new EnemyBullet(locationX + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            }
            res.add(abstractBullet);
        }
        return res;
    }
}
