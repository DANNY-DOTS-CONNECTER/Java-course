package edu.hitsz.booster;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.trajectory.ScatteredTrajectory;
import edu.hitsz.trajectory.StraightTrajectory;

import java.util.concurrent.TimeUnit;

/**
 * 改变子弹弹道类
 * @author Zhoudanni
 */
public class PropBullet extends AbstractBoosterPacks{

    private static Thread bulletThread;
    private boolean propBulletWorking = false;
    public static int validDuration = 10;

    public PropBullet(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void bonus(HeroAircraft heroAircraft) {

        //终止上一个正在进行的bulletThread线程
        if (propBulletWorking) {
            bulletThread.interrupt();
        }
        function(heroAircraft);
        propBulletWorking = true;
        Runnable r = () -> {
            try {
                TimeUnit.SECONDS.sleep(validDuration);
                heroAircraft.setShootNum(1);
                heroAircraft.setStrategy(new StraightTrajectory());
                propBulletWorking = false;
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        };
        bulletThread = new Thread(r);
        bulletThread.start();
    }

    public void function(HeroAircraft heroAircraft){
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
    }

    public static void increaseValidDuration(){
        validDuration += 1;
    }
}


