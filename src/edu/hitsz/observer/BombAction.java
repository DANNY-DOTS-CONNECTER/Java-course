package edu.hitsz.observer;

/**
 * 观察者接口
 * @author Zhoudanni
 */
public interface BombAction {
    /**
     * 每个观察者都需要实现该方法
     */
    void update();
}
