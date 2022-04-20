package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.trajectory.StraightTrajectory;

import java.util.List;

import static edu.hitsz.application.Main.WINDOW_WIDTH;

/**
 * 英雄飞机，游戏玩家操控
 *
 * @author Zhoudanni
 */
public class HeroAircraft extends AbstractAircraft {

    private static HeroAircraft instance = null;

    /**
     * 单例模式创建英雄机
     *
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp        初始生命值
     *
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.strategy = new StraightTrajectory();
        this.power = 30;
        this.direction = -1;
        this.shootNum = 1;
    }

    public static synchronized HeroAircraft getInstance() {
        if (instance == null) {
            synchronized (HeroAircraft.class) {
                if (instance == null) {
                    instance = new HeroAircraft(WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                            0, 0, 1000);
                }
            }
        }
        return instance;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public int getShootNum() {
        return shootNum;
    }

    public void increaseShootNum(){
        shootNum += 1;
    }

    /**
     * 通过射击产生子弹
     *
     * @return 射击出的子弹List
     */
    @Override
    public List<AbstractBullet> shoot() {
        return strategy.selectTrajectory(locationX, locationY, shootNum, direction, power);
    }
}
