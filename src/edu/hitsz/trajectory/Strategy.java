package edu.hitsz.trajectory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;

import java.util.List;

/**
 * 策略接口
 * @author Zhoudanni
 */
public interface Strategy {
    /**
     * 选择弹道方法
     * @param aircraft 选择一种飞机
     * @return 返回子弹list
     */
    List<AbstractBullet> selectTrajectory(AbstractAircraft aircraft);
}
