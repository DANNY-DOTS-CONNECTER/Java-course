package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.trajectory.Strategy;

import java.util.List;

/**
 * 所有种类飞机的抽象父类
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    /**
     * 子弹一次发射数量,采用默认修饰符
     */
    int shootNum;

    /**
     * 子弹伤害,采用默认修饰符
     */
    int power;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1),采用默认修饰符
     * 注意向上/向下是相对y轴位置
     */
    int direction;

    Strategy strategy;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getShootNum() {
        return shootNum;
    }

    public int getPower() {
        return power;
    }

    public int getDirection() {
        return direction;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public List<AbstractBullet> shoot(AbstractAircraft aircraft) {
        return strategy.selectTrajectory(aircraft);
    }
}


