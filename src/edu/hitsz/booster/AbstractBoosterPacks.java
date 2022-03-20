package edu.hitsz.booster;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;


public abstract class AbstractBoosterPacks extends AbstractFlyingObject {

    //抽象福利方法，每个子类继承该方法
    public abstract void bonus(AbstractAircraft aircraft);

    public AbstractBoosterPacks(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }
}
