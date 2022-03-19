package edu.hitsz.booster;

import edu.hitsz.aircraft.AbstractAircraft;

public class PropBullet extends AbstractBoosterPacks{

    public PropBullet(int locationX, int locationY) {
        super(locationX, locationY);
    }

    @Override
    public void bonus(AbstractAircraft aircraft) {
        System.out.println("FireSupply active!");
    }
}
