package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.observer.BombAction;

/**
 * 所有敌机的抽象父类（MOB,ELITE,BOSS）
 * @author Zhoudanni
 */
public abstract class AbstractEnemy extends AbstractAircraft implements BombAction {

    public AbstractEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /**
     * 重写forward方法控制敌机的移动
     */
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public void update() {
    }
}
