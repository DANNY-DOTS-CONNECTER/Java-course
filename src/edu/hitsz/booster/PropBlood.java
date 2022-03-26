package edu.hitsz.booster;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * 加血包类
 * @author Zhoudanni
 */
public class PropBlood extends AbstractBoosterPacks{

    public PropBlood(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void bonus(AbstractAircraft aircraft) {
        int blood = aircraft.getHp();
        blood += 10;
        aircraft.setHp(blood);
    }
}
