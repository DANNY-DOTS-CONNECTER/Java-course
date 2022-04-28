package edu.hitsz.booster;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.trajectory.ScatteredTrajectory;

/**
 * 改变子弹弹道类
 * @author Zhoudanni
 */
public class PropBullet extends AbstractBoosterPacks{

    public PropBullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void bonus(HeroAircraft heroAircraft) {

        heroAircraft.setStrategy(new ScatteredTrajectory());
        //设置最大子弹数量为3
        int maxShootNum = 3;
            int curShootNum = heroAircraft.getShootNum();
        if(curShootNum < maxShootNum){
            heroAircraft.increaseShootNum();
            System.out.println("increase 1 bullet");
        }else{
            System.out.println("reached the maximum shoot number");
        }
//        heroAircraft.setShootNum(maxShootNum);
    }
}


