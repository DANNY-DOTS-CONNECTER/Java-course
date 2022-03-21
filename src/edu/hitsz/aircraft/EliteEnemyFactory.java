package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import static edu.hitsz.application.Main.WINDOW_WIDTH;

public class EliteEnemyFactory implements EnemyFactory{

    int X = Math.random() >= 0.5 ? 3 : -3;//产生横向速度方向的变量

    @Override
    public AbstractEnemy createOperation() {
        return new EliteEnemy((int) (Math.random() * (WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2), X,
                8, 60);
    }
}
