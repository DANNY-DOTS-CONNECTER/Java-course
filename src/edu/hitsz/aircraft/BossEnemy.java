package edu.hitsz.aircraft;

import edu.hitsz.booster.*;
import edu.hitsz.trajectory.ScatteredTrajectory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * BOSS敌机类
 *
 * @author Zhoudanni
 */
public class BossEnemy extends AbstractEnemy {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.strategy = new ScatteredTrajectory();
        this.shootNum = 3;
        this.power = 40;
        this.direction = 1;
    }

    /**
     * BOSS敌机掉落道具
     *
     * @return 返回某种道具
     */
    public List<AbstractBoosterPacks> createProp() {
        PropFactory propFactory;
        List<AbstractBoosterPacks> arrayList = new ArrayList<>();
        Random r = new Random();
        int propNum = 3;
        for (int i = 0; i < propNum; i++) {
            int propRandom = r.nextInt(3);
            switch (propRandom) {
                // 加血道具
                case 0:
                    propFactory = new PropBloodFactory();
                    arrayList.add(propFactory.createOperation(this.getLocationX() + r.nextInt(50), this.getLocationY() - r.nextInt(50)));
                    break;
                // 火力道具
                case 1:
                    propFactory = new PropBulletFactory();
                    arrayList.add(propFactory.createOperation(this.getLocationX() + r.nextInt(50), this.getLocationY() + r.nextInt(50)));
                    break;
                // 炸弹道具
                case 2:
                    propFactory = new PropBombFactory();
                    arrayList.add(propFactory.createOperation(this.getLocationX() - r.nextInt(50), this.getLocationY() - r.nextInt(50)));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + propRandom);
            }
        }
        return arrayList;
    }
}
