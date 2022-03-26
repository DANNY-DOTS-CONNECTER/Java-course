package edu.hitsz.booster;

/**
 * 加炸弹包工厂类
 * @author Zhoudanni
 */
public class PropBombFactory implements PropFactory{
    @Override
    public AbstractBoosterPacks createOperation(int locationX, int locationY) {
        return new PropBomb(locationX, locationY,0,2);
    }
}
