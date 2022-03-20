package edu.hitsz.booster;

import edu.hitsz.aircraft.AbstractAircraft;

public class PropBomb extends AbstractBoosterPacks{

    public PropBomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void bonus(AbstractAircraft aircraft) {
        System.out.println("BombSupply active!");
    }
}
