package edu.hitsz.booster;

/**
 * 加子弹工厂类
 * @author Zhoudanni
 */
public class PropBulletFactory implements PropFactory{
    @Override
    public AbstractBoosterPacks createOperation(int locationX, int locationY) {
        return new PropBullet(locationX, locationY,0,2);
    }
}
