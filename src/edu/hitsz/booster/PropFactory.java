package edu.hitsz.booster;


/**
 * 所有道具包工厂接口
 * @author Zhoudanni
 */
public interface PropFactory {
    /**
     * 返回某种道具
     * @param locationX 道具包x位置
     * @param locationY 道具包y位置
     * @return 返回某种类型的道具
     */
    AbstractBoosterPacks createOperation(int locationX, int locationY);
}
