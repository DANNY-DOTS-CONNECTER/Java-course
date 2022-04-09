package edu.hitsz.booster;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.trajectory.HeroScatteredTrajectory;

/**
 * 加子弹数量包类
 * @author Zhoudanni
 */
public class PropBullet extends AbstractBoosterPacks{

    public PropBullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }


    @Override
    public void bonus(HeroAircraft aircraft) {
        //TODO 英雄机碰到道具改变弹道
        aircraft.setStrategy(new HeroScatteredTrajectory());
        System.out.println("FireSupply active!");
    }
}
