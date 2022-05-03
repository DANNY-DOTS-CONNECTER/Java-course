package edu.hitsz.bullet;

import edu.hitsz.observer.BombAction;

/**
 * @author Zhoudanni
 * 敌人子弹
 */
public class EnemyBullet extends AbstractBullet {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void update() {
        vanish();
    }
}
