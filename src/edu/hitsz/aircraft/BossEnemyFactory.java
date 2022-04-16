package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.trajectory.ScatteredTrajectory;

import static edu.hitsz.application.Main.WINDOW_WIDTH;

/**
 * BOSS敌机工厂类
 *
 * @author Zhoudanni
 */
public class BossEnemyFactory implements EnemyFactory {

    /**
     * 产生横向速度方向的变量
     */
    int speedX = Math.random() >= 0.5 ? 1 : -1;

    @Override
    public AbstractEnemy createOperation() {
        return new BossEnemy((int) (Math.random() * (WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                speedX,
                0,
                200, new ScatteredTrajectory());
    }
}
