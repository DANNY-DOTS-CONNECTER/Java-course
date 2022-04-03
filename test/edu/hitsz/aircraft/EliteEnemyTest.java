package edu.hitsz.aircraft;

import edu.hitsz.booster.AbstractBoosterPacks;
import edu.hitsz.booster.PropBlood;
import edu.hitsz.booster.PropBomb;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {
    EliteEnemy eliteEnemy;

    @BeforeEach
    void setUp() {
        eliteEnemy = new EliteEnemy(20, 15, 5, 5, 20);
    }

    @AfterEach
    void tearDown() {
        eliteEnemy = null;
    }

    @Test
    @DisplayName("EliteEnemy_shootTest")
    void shootTest() {
        assertNotNull(eliteEnemy.shoot());
    }

    @ParameterizedTest
    @DisplayName("Crash_Bullet_Test")
    @CsvSource({"20,20,true", "85,80,false", "35,10,true"})
    void crashTest(int locationX, int locationY, boolean flag) {
        AbstractBullet bullet = new HeroBullet(locationX, locationY, 10, 10, 9);
        assertEquals(flag, eliteEnemy.crash(bullet));
    }

    @ParameterizedTest
    @DisplayName("EliteEnemy_Forward_Test")
    @CsvSource({"200,300,false", "512,768,true", "30,20,false", "0,0,false"})
    void forwardTest(double locationX, double locationY, boolean judge) {
        eliteEnemy.setLocation(locationX, locationY);
        eliteEnemy.forward();
        assertEquals(judge, eliteEnemy.notValid());
    }

    @Test
    @DisplayName("Create_Prop_Test")
    void createPropTest() {
        int num = 500;
        boolean nullFlag = false;
        boolean bloodFlag = false;
        boolean bombFlag = false;
        boolean bulletFlag = false;
        List<AbstractBoosterPacks> propList = new LinkedList<>();
        for (int i = 0; i < num; i++) {
            propList.add(eliteEnemy.createProp());
        }
        for (int i = 0; i < num; i++) {
            for (AbstractBoosterPacks prop : propList) {
                if (prop == null) {
                    nullFlag = true;
                } else if (prop instanceof PropBlood) {
                    bloodFlag = true;
                } else if (prop instanceof PropBomb) {
                    bombFlag = true;
                } else {
                    bulletFlag = true;
                }
            }
        }
        assertTrue(nullFlag && bloodFlag && bombFlag && bulletFlag);
    }
}