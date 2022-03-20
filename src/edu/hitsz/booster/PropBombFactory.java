package edu.hitsz.booster;

public class PropBombFactory implements PropFactory{
    @Override
    public AbstractBoosterPacks createOperation(int locationX, int locationY) {
        return new PropBomb(locationX, locationY,0,2);
    }
}
