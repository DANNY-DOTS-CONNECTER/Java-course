package edu.hitsz.rankings;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Zhoudanni
 */
public class UserDaoList implements UserDao {

    File file = new File("src\\rankings.txt");

    @Override
    public List<User> getAllUserList() throws IOException, ClassNotFoundException {
        //input
        List<User> usersList = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        MyObjectInputStream ois = new MyObjectInputStream(fis);
        // 反序列化读取数据到列表中
        while (fis.available() > 0) {
            usersList.add((User) ois.readObject());
        }
        fis.close();
        ois.close();
        return usersList;
    }

    @Override
    public void addUser(User user) throws IOException {
        //output
        FileOutputStream fos = new FileOutputStream(file, true);
        MyObjectOutputStream oos = new MyObjectOutputStream(fos);
        try {
            oos.writeObject(user);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fos.close();
    }

    @Override
    public void deleteRecord(User user) {

    }

    @Override
    public void printUserRanking() throws IOException, ClassNotFoundException {
        List<User> users = getAllUserList();
        Collections.sort(users);
        System.out.println("******************************");
        System.out.println("           得分排行榜          ");
        System.out.println("******************************");
        for (int i = 0; i < users.size(); i++) {
            System.out.println("第" + (i + 1) + "名：" + users.get(i).toString());
        }
    }
}
