package edu.hitsz.aircraft;

/**
 * 敌机总工厂类
 *
 * @author Zhoudanni
 */
public interface EnemyFactory {
    /**
     * 敌机的总工厂方法
     *
     * @return 返回一个抽象飞机类型
     */
    AbstractEnemy createOperation();
}
