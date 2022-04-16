package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.booster.*;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.rankings.User;
import edu.hitsz.rankings.UserDao;
import edu.hitsz.rankings.UserDaoList;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;


/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz-zdn
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<AbstractBullet> heroBullets;
    private final List<AbstractBullet> enemyBullets;
    private final List<AbstractBoosterPacks> boosterPacks;
    private EnemyFactory enemyFactory;
    private UserDao userDao;

    private int enemyMaxNumber = 5;
    private int bossScoreThreshold = 300;

    private boolean gameOverFlag = false;
    private int score = 0;
    private int time = 0;
    private boolean bossFlag = true;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 500;
    private int cycleTime = 0;


    public Game() {
        heroAircraft = HeroAircraft.getInstance();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        boosterPacks = new LinkedList<>();
        userDao = new UserDaoList();
        /*
          Scheduled 线程池，用于定时任务调度
          关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
          apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新普通敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    //每隔一段时间随机产生一架普通敌机或精英敌机
                    //精英敌机产生概率为0.8
                    double portion = 0.9;
                    if (Math.random() >= portion) {
                        //产生精英敌机的概率会低一些
                        enemyFactory = new EliteEnemyFactory();
                        enemyAircrafts.add(enemyFactory.createOperation());
                    } else {
                        //产生普通敌机
                        enemyFactory = new MobEnemyFactory();
                        enemyAircrafts.add(enemyFactory.createOperation());
                    }
                }
                // 飞机射出子弹
                shootAction();
            }

            //产生boss敌机，放在if (timeCountAndNewCycleJudge())外面，否则达到分数也会等一会儿才会创建boss敌机
            if (bossFlag && score > bossScoreThreshold) {
                BossEnemyFactory bossEnemyFactory = new BossEnemyFactory();
                enemyAircrafts.add(bossEnemyFactory.createOperation());
                bossFlag = false;
                if(score < 500){
                    bossScoreThreshold += 300;
                }else {
                    bossScoreThreshold += 200;
                }
            }

            // 子弹移动
            bulletsMoveAction();

            // 敌机移动
            aircraftsMoveAction();

            //道具包移动
            boosterPacksMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                gameOverFlag = true;//上下两句调换过位置
                executorService.shutdown();

                User user = new User("test",score, new Date());
                try{
                    userDao.addUser(user);
                    userDao.printUserRanking();
                }catch (IOException | ClassNotFoundException e){
                    e.printStackTrace();
                }
                System.out.println("Game Over!");
            }

        };

        /*
          以固定延迟时间进行执行
          本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        //敌机射击
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft instanceof EliteEnemy || enemyAircraft instanceof BossEnemy) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (AbstractBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    /**
     * 敌机移动
     */
    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    /**
     * 道具包移动
     */
    private void boosterPacksMoveAction() {
        for (AbstractBoosterPacks booster : boosterPacks) {
            booster.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {

        // TODO 敌机子弹攻击英雄
        for (AbstractBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞到敌机子弹
                // 英雄机减少一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (AbstractBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        //如果是精英敌机产生道具+加分
                        if (enemyAircraft.getClass() == EliteEnemy.class) {
                            AbstractBoosterPacks booster = ((EliteEnemy) enemyAircraft).createProp();
                            if (booster != null) {
                                boosterPacks.add(booster);
                            }
                            //精英敌机加20分
                            score += 15;
                        } else if(enemyAircraft.getClass() == MobEnemy.class){
                            //普通敌机加10分
                            score += 10;
                        }else if (enemyAircraft.getClass() == BossEnemy.class){
                            //Boss敌机产生道具*2
                            AbstractBoosterPacks booster = ((BossEnemy) enemyAircraft).createProp();
                            AbstractBoosterPacks booster2 = ((BossEnemy) enemyAircraft).createProp();
                            boosterPacks.add(booster);
                            boosterPacks.add(booster2);
                            score += 25;
                            bossFlag = true;
                        }
                    }
                }

                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractBoosterPacks boosterPack : boosterPacks) {
            if (boosterPack.notValid()) {
                //避免重复加同一个道具
                continue;
            }
            if (boosterPack.crash(heroAircraft)) {
                boosterPack.bonus(heroAircraft);
                boosterPack.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * 4. 检查道具包是否有效
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        boosterPacks.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param g 画笔
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        // 绘制道具包
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, boosterPacks);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(0xFF0000));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }
}
