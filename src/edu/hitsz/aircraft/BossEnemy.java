package edu.hitsz.aircraft;

import edu.hitsz.booster.*;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.trajectory.Strategy;
import java.util.List;
import java.util.Random;

/**
 * BOSS敌机类
 *
 * @author Zhoudanni
 */
public class BossEnemy extends AbstractEnemy {
    private Strategy strategy;

    private int shootNum = 3;

    /**
     * 子弹伤害
     * Boss敌机设定为40
     */
    private final int power = 40;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     * 此处定义为向上发射（相对y轴）
     */
    private final int direction = 1;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, Strategy strategy) {
        super(locationX, locationY, speedX, speedY, hp);
        this.strategy = strategy;
    }


    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public List<AbstractBullet> shoot() {
        return strategy.selectTrajectory(locationX, locationY, shootNum, direction, power);
    }


    /**
     * BOSS敌机掉落道具
     *
     * @return 返回某种道具
     */
    public AbstractBoosterPacks createProp(){
        PropFactory propFactory;
        Random r = new Random();
        int propRandom = r.nextInt(3);
        switch (propRandom) {
            // 加血道具
            case 0:
                propFactory = new PropBloodFactory();
                return propFactory.createOperation(this.getLocationX(), this.getLocationY());
            // 火力道具
            case 1:
                propFactory = new PropBulletFactory();
                return propFactory.createOperation(this.getLocationX(), this.getLocationY());
            // 炸弹道具
            case 2:
                propFactory = new PropBombFactory();
                return propFactory.createOperation(this.getLocationX(), this.getLocationY());
            default:
                throw new IllegalStateException("Unexpected value: " + propRandom);
        }
    }

}
