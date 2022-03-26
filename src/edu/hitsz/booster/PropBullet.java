package edu.hitsz.booster;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * 加子弹数量包类
 * @author Zhoudanni
 */
public class PropBullet extends AbstractBoosterPacks{

    public PropBullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void bonus(AbstractAircraft aircraft) {
        System.out.println("FireSupply active!");
    }
}
