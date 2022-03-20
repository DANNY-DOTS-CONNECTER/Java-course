package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

import static edu.hitsz.application.Main.WINDOW_WIDTH;

public class MobEnemyFactory implements EnemyFactory{
    @Override
    public AbstractAircraft createOperation() {
        return new MobEnemy((int) (Math.random() * (WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                0,
                10,
                30);
    }
}
