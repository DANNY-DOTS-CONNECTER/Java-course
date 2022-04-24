package edu.hitsz.rankings;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 用户类
 *
 * @author Zhoudanni
 */
public class User implements Serializable, Comparable<User> {
    /**
     * 指定一个序列化号
     */
    private static final long serialVersionUID = 6529685098267757690L;

    private String name;
    private int score;
    private Date gameDate;
    private long id;

    public User(long id, String name, int score, Date gameDate) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.gameDate = gameDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH : mm");
        String s = sdf.format(gameDate);
        return this.getName() + ", " + this.getScore() + ", " + s;
    }

    public String[] getSpecificComponent() {
        // "玩家名","得分","记录时间"
        String[] stringArray = new String[3];
        stringArray[0] = name;
        stringArray[1] = Integer.toString(score);

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH : mm");
        String s = sdf.format(gameDate);
        stringArray[2] = s;
        return stringArray;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 排序返回从高到低的数据
     *
     * @param o 用户
     * @return 用户分数差
     */
    @Override
    public int compareTo(User o) {
        return o.getScore() - this.getScore();
    }
}
