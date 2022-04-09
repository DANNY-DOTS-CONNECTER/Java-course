package edu.hitsz.booster;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 加炸弹包类
 * @author Zhoudanni
 */
public class PropBomb extends AbstractBoosterPacks{

    public PropBomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void bonus(HeroAircraft aircraft) {
        System.out.println("BombSupply active!");
    }
}
