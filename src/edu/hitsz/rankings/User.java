package edu.hitsz.rankings;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Zhoudanni
 */
public class User implements Serializable, Comparable<User> {

    private String name;
    private int score;
    private Date gameDate;

    public User(String name, int score, Date gameDate) {
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

    /**
     * 排序返回从高到低的数据
     * @param o 用户
     * @return 用户分数差
     */
    @Override
    public int compareTo(User o) {
        return o.getScore() - this.getScore();
    }
}
