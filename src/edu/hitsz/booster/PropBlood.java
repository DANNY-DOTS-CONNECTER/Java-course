package edu.hitsz.booster;

import edu.hitsz.aircraft.AbstractAircraft;

public class PropBlood extends AbstractBoosterPacks{

    public PropBlood(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void bonus(AbstractAircraft aircraft) {
        int blood = aircraft.getHp();
        blood += 10;
        aircraft.setHp(blood);
    }
}
