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
        //掉落道具的概率设置为0.6
        double portion = 0.6;
        if(randomNumber < portion){
            int propRandom = r.nextInt(10);
            switch (propRandom) {
                // 加血道具
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    propFactory = new PropBloodFactory();
                    return propFactory.createOperation(this.getLocationX(), this.getLocationY());
                // 火力道具
                case 6:
                case 7:
                case 8:
                    propFactory = new PropBulletFactory();
                    return propFactory.createOperation(this.getLocationX(), this.getLocationY());
                // 炸弹道具
                case 9:
                    propFactory = new PropBombFactory();
                    return propFactory.createOperation(this.getLocationX(), this.getLocationY());
                default:
                    throw new IllegalStateException("Unexpected value: " + propRandom);
            }
        }
        return null;
    }

    @Override
    public void update() {
        vanish();
    }

}
