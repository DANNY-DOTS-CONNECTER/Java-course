package edu.hitsz.application;


import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.booster.AbstractBoosterPacks;
import edu.hitsz.booster.PropBlood;
import edu.hitsz.booster.PropBomb;
import edu.hitsz.booster.PropBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage BACKGROUND_IMAGE;
    public static BufferedImage HERO_IMAGE;
    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage ELITE_ENEMY_IMAGE;
    public static BufferedImage BOSS_ENEMY_IMAGE;
    public static BufferedImage PROP_BLOOD;
    public static BufferedImage PROP_BOMB;
    public static BufferedImage PROP_BULLET;
    public static ImageIcon ICON = new ImageIcon("src/images/me2.png");

    static {
        try {

            BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));

            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/me1.png"));
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/enemy1.png"));
            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet1.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet2.png"));
            ELITE_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/enemy2.png"));
            BOSS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/enemy3.png"));

            PROP_BLOOD = ImageIO.read(new FileInputStream("src/images/blood.png"));
            PROP_BOMB = ImageIO.read(new FileInputStream("src/images/bomb.png"));
            PROP_BULLET = ImageIO.read(new FileInputStream("src/images/bullet_supply.png"));

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);

            CLASSNAME_IMAGE_MAP.put(PropBlood.class.getName(), PROP_BLOOD);
            CLASSNAME_IMAGE_MAP.put(PropBomb.class.getName(), PROP_BOMB);
            CLASSNAME_IMAGE_MAP.put(PropBullet.class.getName(), PROP_BULLET);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

    public static void setBackgroundImage(String file) {
        try {
            BACKGROUND_IMAGE = ImageIO.read(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
