package edu.hitsz.booster;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * 加血包类
 * @author Zhoudanni
 */
public class PropBlood extends AbstractBoosterPacks{

    public PropBlood(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void bonus(HeroAircraft aircraft) {
        int blood = aircraft.getHp();
        blood += 50;
        aircraft.setHp(blood);
    }
}
