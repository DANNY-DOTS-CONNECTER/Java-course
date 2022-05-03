package edu.hitsz.booster;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.observer.BombAction;

import java.util.ArrayList;
import java.util.List;

/**
 * 加炸弹包类
 * @author Zhoudanni
 */
public class PropBomb extends AbstractBoosterPacks{

    private List<BombAction> listenerList = new ArrayList<>();

    public PropBomb(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void addListener(BombAction listener){
        listenerList.add(listener);
    }

    public void removeListener(BombAction listener){
        listenerList.remove(listener);
    }

    public void notifyAllListener(){
        for(BombAction listener : listenerList){
            listener.update();
        }
    }

    @Override
    public void bonus(HeroAircraft aircraft) {
        System.out.println("BombSupply active!");
        notifyAllListener();
    }
}
