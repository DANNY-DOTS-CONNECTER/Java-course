package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.trajectory.StraightTrajectory;
import edu.hitsz.trajectory.Strategy;
import java.util.List;
import static edu.hitsz.application.Main.WINDOW_WIDTH;

/**
 * 英雄飞机，游戏玩家操控
 *
 * @author Zhoudanni
 */
public class HeroAircraft extends AbstractAircraft {

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     * 注意向上/向下是相对y轴位置
     */
    private final int direction = -1;

    private static HeroAircraft instance = null;

    private Strategy strategy;
    /**
     * 单例模式创建英雄机
     *
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp        初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, Strategy strategy) {
        super(locationX, locationY, speedX, speedY, hp);
        this.strategy = strategy;
    }

    public static synchronized HeroAircraft getInstance() {
        if (instance == null) {
            synchronized (HeroAircraft.class) {
                if (instance == null) {
                    instance = new HeroAircraft(WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                            0, 0, 1000, new StraightTrajectory() {
                    });
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

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
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
