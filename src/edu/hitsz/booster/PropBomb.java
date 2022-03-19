package edu.hitsz.booster;

import edu.hitsz.aircraft.AbstractAircraft;

public class PropBomb extends AbstractBoosterPacks{

    public PropBomb(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void bonus(AbstractAircraft aircraft) {
        System.out.println("BombSupply active!");
    }
}
