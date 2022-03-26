package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;

import java.util.List;

/**
 * BOSS敌机类
 * @author Zhoudanni
 */
public class BossEnemy extends AbstractEnemy{

    //暂时没有使用该类
    /**
     * 子弹伤害
     * Boss敌机设定为40
     */
    private final int power = 40;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     * 此处定义为向上发射（相对y轴）
     */
    private final int direction = 1;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<AbstractBullet> shoot() {
        return null;
    }

}
