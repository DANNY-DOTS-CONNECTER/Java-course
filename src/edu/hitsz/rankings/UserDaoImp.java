package edu.hitsz.rankings;

import edu.hitsz.application.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Zhoudanni
 */
public class UserDaoImp implements UserDao {

    String level1 = "src\\rankings.txt";
    String level2 = "src\\rankings2.txt";
    String level3 = "src\\rankings3.txt";
    File file;

    {
        switch (Game.level) {
            case 1:
                file = new File(level1);
                break;
            case 2:
                file = new File(level2);
                break;
            case 3:
                file = new File(level3);
                break;
            default:
        }
    }

    @Override
    public List<User> getAllUserList() throws IOException, ClassNotFoundException {
        List<User> usersList = new ArrayList<>();
        //input
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
    public void deleteRecord(long userID) throws IOException, ClassNotFoundException {
        List<User> users = getAllUserList();
        users.removeIf(userData -> userData.getId() == userID);
        //重写整个list到rankings
        if (users.size() == 0) {
            FileWriter writer = new FileWriter(file);
            writer.write("");
            writer.flush();
            writer.close();
        } else {
            FileOutputStream fos = new FileOutputStream(file, false);
            MyObjectOutputStream oos = new MyObjectOutputStream(fos);
            try {
                for (User userData : users) {
                    oos.writeObject(userData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            oos.close();
            fos.close();
        }
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
