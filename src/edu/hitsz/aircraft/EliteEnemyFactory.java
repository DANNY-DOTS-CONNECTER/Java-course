package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import static edu.hitsz.application.Main.WINDOW_WIDTH;

/**
 * 精英敌机工厂类
 *
 * @author Zhoudanni
 */
public class EliteEnemyFactory implements EnemyFactory {

    @Override
    public AbstractEnemy createOperation() {
        int speedX = 0;
        double num = Math.random();
        double por1 = 0.7;
        double por2 = 0.5;
        if (num >= por1) {
            speedX = 3;
        } else if (num > por2) {
            speedX = -3;
        }

        return new EliteEnemy((int) (Math.random() * (WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2), speedX,
                7, 60);
    }

}
