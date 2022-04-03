package edu.hitsz.booster;

import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PropBloodTest {
    HeroAircraft heroAircraft;
    PropBlood blood = new PropBlood(10, 10, 0, 0);

    @BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getInstance();
    }

    @AfterEach
    void tearDown() {
        heroAircraft = null;
    }

    @ParameterizedTest
    @DisplayName("Add_Blood_Test")
    @CsvSource({"0,10", "25,35", "100,110"})
    void bonusTest(int num1, int num2) {
        heroAircraft.setHp(num1);
        System.out.println("加血之前：" + heroAircraft.getHp());
        blood.bonus(heroAircraft);
        System.out.println("加血后：" + heroAircraft.getHp());
        assertEquals(num2, heroAircraft.getHp());
    }

    @Test
    @DisplayName("Vanish_Test")
    void vanishTest() {
        blood.vanish();
        assertTrue(blood.notValid());
    }

    @ParameterizedTest
    @DisplayName("Crash_Test")
    @CsvSource({"100,3,false", "200,34,false", "0,0,true"})
    void crashTest(double locationX, double locationY, boolean flag) {
        heroAircraft.setLocation(locationX, locationY);
        assertEquals(flag, blood.crash(heroAircraft));
    }
}