package edu.hitsz.booster;


public class PropBulletFactory implements PropFactory{
    @Override
    public AbstractBoosterPacks createOperation(int locationX, int locationY) {
        return new PropBullet(locationX, locationY,0,2);
    }
}
