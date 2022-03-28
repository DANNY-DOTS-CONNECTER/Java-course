package edu.hitsz.booster;

/**
 * 加血包工厂
 *
 * @author Zhoudanni
 */
public class PropBloodFactory implements PropFactory {
    @Override
    public AbstractBoosterPacks createOperation(int locationX, int locationY) {
        return new PropBlood(locationX, locationY, 0, 2);
    }
}
