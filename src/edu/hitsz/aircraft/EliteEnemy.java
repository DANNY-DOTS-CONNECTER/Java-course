package edu.hitsz.aircraft;

import edu.hitsz.booster.*;
import edu.hitsz.trajectory.StraightTrajectory;

import java.util.Random;

/**
 * 精英敌机类
 *
 * @author Zhoudanni
 */
public class EliteEnemy extends AbstractEnemy {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 1;
        this.direction = 1;
        this.power = 10;
        this.strategy = new StraightTrajectory();
    }

    private PropFactory propFactory;

    /**
     * 精英敌机掉落道具
     *
     * @return 返回某种道具
     */
    public AbstractBoosterPacks createProp(){
        Random r = new Random();
        double randomNumber = r.nextDouble();
        //掉落道具的概率设置为0.8
        double portion = 0.8;
        if(randomNumber < portion){
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
        return null;
    }
}
