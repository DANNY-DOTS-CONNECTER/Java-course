package edu.hitsz.trajectory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;

import java.util.List;

/**
 * @author Zhoudanni
 */
public interface Strategy {
    /**
     * 选择弹道方法
     * @param locationX x坐标
     * @param locationY y坐标
     * @param shootNum 发射子弹数量
     * @param direction 方向
     * @param power 火力
     * @return 子弹
     */
    List<AbstractBullet> selectTrajectory(int locationX, int locationY, int shootNum, int direction, int power);
}
