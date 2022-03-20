package edu.hitsz.booster;

public class PropBloodFactory implements PropFactory{
    @Override
    public AbstractBoosterPacks createOperation(int locationX, int locationY) {
        return new PropBlood(locationX, locationY,0,2);
    }
}
