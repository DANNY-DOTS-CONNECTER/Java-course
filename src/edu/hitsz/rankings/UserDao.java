package edu.hitsz.rankings;

import java.io.IOException;
import java.util.List;

/**
 * UserDao接口
 * @author Zhoudanni
 */
public interface UserDao {

    /**
     * 返回一个拥有所有用户信息的List
     * @return 用户名单List<User>
     * @throws IOException IO流异常
     * @throws ClassNotFoundException 找不到该类异常
     */
    List<User> getAllUserList() throws IOException, ClassNotFoundException;

    /**
     * 添加用户
     * @param user 用户个人信息
     * @throws IOException IO流异常
     */
    void addUser(User user) throws IOException;

    /**
     * 删除选中的游戏记录
     * @param id 用户id
     * @throws IOException IO流异常
     * @throws ClassNotFoundException 找不到该类异常
     */
    void deleteRecord(long id) throws IOException, ClassNotFoundException;

    /**
     * 排序并打印榜单
     * @throws IOException IO流异常
     * @throws ClassNotFoundException 找不到该类异常
     */
    void printUserRanking() throws IOException, ClassNotFoundException;
}
