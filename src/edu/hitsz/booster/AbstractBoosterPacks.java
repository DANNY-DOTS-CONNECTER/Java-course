package edu.hitsz.booster;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.basic.AbstractFlyingObject;


public abstract class AbstractBoosterPacks extends AbstractFlyingObject {

    //抽象福利方法，每个子类继承该方法
    public abstract void bonus(AbstractAircraft aircraft);

    public AbstractBoosterPacks(int locationX, int locationY) {
        this.setLocation((double) locationX,(double) locationY);
    }
}
