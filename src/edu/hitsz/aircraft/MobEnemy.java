package edu.hitsz.aircraft;

/**
 * 普通敌机类
 * 不可射击
 *
 * @author Zhoudanni
 */
public class MobEnemy extends AbstractEnemy {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 0;
    }

}
