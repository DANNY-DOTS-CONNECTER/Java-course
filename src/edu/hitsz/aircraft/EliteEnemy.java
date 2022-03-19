package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机类
 * @author zhoudanni
 */
public class EliteEnemy extends AbstractAircraft{

    /**
     * 子弹一次发射数量
     */
//    private int shootNum = 1;

    /**
     * 子弹伤害
     * 精英敌机设定为10
     */
    private final int power = 10;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     * 此处定义为向上发射（相对y轴）
     */
    private final int direction = 1;

    //构造方法
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /**
     * 重写shoot()方法，返回子弹
     */
    @Override
    public List<AbstractBullet> shoot() {
        List<AbstractBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 20;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;//此处数字越大，速度越快
        AbstractBullet abstractBullet;
        abstractBullet = new EnemyBullet(x , y, speedX, speedY, power);
        res.add(abstractBullet);
        return res;
    }

    /**
     * 重写forward方法控制精英敌机的移动
     */
    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

}
