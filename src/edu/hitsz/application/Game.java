package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.booster.*;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.rankings.User;
import edu.hitsz.rankings.UserDao;
import edu.hitsz.rankings.UserDaoImp;
import edu.hitsz.trajectory.StraightTrajectory;
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
public abstract class Game extends JPanel {
    /**
     * 游戏难度设置
     */
    public static int level = 1;

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 40;

    protected HeroAircraft heroAircraft;
    protected List<AbstractEnemy> enemyAircrafts;
    protected List<AbstractBullet> heroBullets;
    protected List<AbstractBullet> enemyBullets;
    protected List<AbstractBoosterPacks> boosterPacks;
    protected EnemyFactory enemyFactory;
    protected UserDao userDao;

    protected int enemyMaxNumber;
    protected int bossScoreThreshold = 200;

    private boolean gameOverFlag = false;
    protected int score = 0;
    protected int time = 0;
    protected boolean bossFlag = false;
    private boolean propBulletWorking = false;
    boolean newDataAddedFlag = false;

    public double portion;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 500;
    private int cycleTime = 0;

    private Thread bulletThread;
    protected final MusicThread[] musicThreads = new MusicThread[7];

    protected int currentTime;
    /**
     * game类构造方法，无参
     */
    public Game() {
        heroAircraft = HeroAircraft.getInstance();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        boosterPacks = new LinkedList<>();
        userDao = new UserDaoImp();

        /*
          Scheduled 线程池，用于定时任务调度
          关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
          apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //TODO 0:bgm
        MusicThread bgm = new MusicThread("src/videos/bgm.wav");
        musicThreads[0] = bgm;
        bgm.start();

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑，模板方法
     */
    public final void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                currentTime = time;
                // 新精英/普通敌机产生
                createEnemyAircraft();
                // 飞机射出子弹
                shootAction();
            }

            //产生boss敌机
            createBossEnemy();

            // 子弹移动
            bulletsMoveAction();

            // 敌机移动
            aircraftsMoveAction();

            //道具包移动
            boosterPacksMoveAction();

            // 撞击检测
            crashCheckAction();

            //音效是否有效检测
            musicCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            gameOverAction();

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

    protected boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 抽象方法：构造普通和精英敌机
     */
    protected abstract void createEnemyAircraft();

    /**
     * 抽象方法：构造BossEnemy
     */
    protected abstract void createBossEnemy();

    private void shootAction() {
        //敌机射击
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft instanceof EliteEnemy || enemyAircraft instanceof BossEnemy) {
                enemyBullets.addAll(enemyAircraft.shoot(enemyAircraft));
            }
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot(heroAircraft));
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
                    //TODO 2:bullet_hit
                    MusicThread hitBgm = new MusicThread("src/videos/bullet_hit.wav");
                    musicThreads[2] = hitBgm;
                    hitBgm.start();

                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if (enemyAircraft.getClass() == EliteEnemy.class) {
                            AbstractBoosterPacks booster = ((EliteEnemy) enemyAircraft).createProp();
                            if (booster != null) {
                                boosterPacks.add(booster);
                            }
                            //精英敌机加15分
                            score += 15;
                        } else if (enemyAircraft.getClass() == MobEnemy.class) {
                            //普通敌机加10分
                            score += 10;
                        } else if (enemyAircraft.getClass() == BossEnemy.class) {
                            //boss机死亡，停止播放音乐
                            musicThreads[1].setBreakPoint(false);
                            musicThreads[1] = null;

                            boosterPacks.addAll(((BossEnemy) enemyAircraft).createProp());
                            score += 25;
                            bossFlag = false;
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
                if (boosterPack instanceof PropBullet) {
                    //TODO 3:get_supply
                    MusicThread supplyBgm = new MusicThread("src/videos/get_supply.wav");
                    musicThreads[3] = supplyBgm;
                    supplyBgm.start();

                    //终止上一个正在进行的bulletThread线程
                    if (propBulletWorking) {
                        bulletThread.interrupt();
                    }
                    boosterPack.bonus(heroAircraft);
                    propBulletWorking = true;
                    Runnable r = () -> {
                        try {
                            TimeUnit.SECONDS.sleep(10);
                            heroAircraft.setShootNum(1);
                            heroAircraft.setStrategy(new StraightTrajectory());
                            propBulletWorking = false;
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                    };
                    bulletThread = new Thread(r);
                    bulletThread.start();
                } else if (boosterPack instanceof PropBlood) {
                    MusicThread supplyBgm = new MusicThread("src/videos/get_supply.wav");
                    musicThreads[3] = supplyBgm;
                    supplyBgm.start();

                    boosterPack.bonus(heroAircraft);
                } else if (boosterPack instanceof PropBomb) {
                    //TODO 4:bombBgm
                    MusicThread bombBgm = new MusicThread("src/videos/bomb_explosion.wav");
                    musicThreads[4] = bombBgm;
                    bombBgm.start();

//                    for(AbstractEnemy listener : enemyAircrafts){
//                        ((PropBomb) boosterPack).addListener(listener);
//                        if(listener instanceof MobEnemy){
//                            score += 10;
//                        }else if(listener instanceof  EliteEnemy){
//                            score += 15;
//                        }
//                    }
//
//                    for(AbstractBullet bullet : enemyBullets){
//                        if(bullet instanceof EnemyBullet){
//                            ((PropBomb) boosterPack).addListener(bullet);
//                        }
//                    }

                    addObserver(boosterPack);

                    boosterPack.bonus(heroAircraft);
                }
                boosterPack.vanish();
            }
        }
    }

    protected void addObserver(AbstractBoosterPacks boosterPack){
        for(AbstractEnemy listener : enemyAircrafts){
            ((PropBomb) boosterPack).addListener(listener);
            if(listener instanceof MobEnemy){
                score += 10;
            }else if(listener instanceof  EliteEnemy){
                score += 15;
            }
        }

        for(AbstractBullet bullet : enemyBullets){
            if(bullet instanceof EnemyBullet){
                ((PropBomb) boosterPack).addListener(bullet);
            }
        }
    }

    protected void deleteObserver(AbstractBoosterPacks boosterPack){
        for(AbstractEnemy listener : enemyAircrafts){
            ((PropBomb) boosterPack).removeListener(listener);
        }
        for(AbstractBullet bullet : enemyBullets){
            ((PropBomb) boosterPack).removeListener(bullet);
        }
    }

    private void musicCheckAction() {
        if (bossFlag && !musicThreads[1].isAlive()) {
            MusicThread bossBgm = new MusicThread("src/videos/bgm_boss.wav");
            musicThreads[1] = bossBgm;
            bossBgm.start();
        }
        if (!musicThreads[0].isAlive()) {
            MusicThread bgm = new MusicThread("src/videos/bgm.wav");
            musicThreads[0] = bgm;
            bgm.start();
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

    protected void gameOverAction(){
        if (heroAircraft.getHp() <= 0) {
            // 游戏结束
            //TODO 5:game_over
            MusicThread gameOverBgm = new MusicThread("src/videos/game_over.wav");
            musicThreads[5] = gameOverBgm;
            gameOverBgm.start();

            //休眠50ms让gameOverBgm播放
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //上下两句调换过位置
            gameOverFlag = true;
            executorService.shutdown();

            for (MusicThread music : musicThreads) {
                if (music != null){
                    music.setBreakPoint(false);
                }
            }

            //添加游戏用户信息
            String inputName = JOptionPane.showInputDialog("游戏结束，你的得分为：" + score + "\n输入姓名记录得分：");
            if (inputName != null) {
                newDataAddedFlag = true;
                long id = System.currentTimeMillis();
                User user = new User(id, inputName, score, new Date());
                try {
                    userDao.addUser(user);
                    userDao.printUserRanking();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            synchronized (Main.lock) {
                Main.lock.notify();
            }

            System.out.println("Game Over!");
        }
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
        g.setColor(new Color(0x676E73));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }
}
