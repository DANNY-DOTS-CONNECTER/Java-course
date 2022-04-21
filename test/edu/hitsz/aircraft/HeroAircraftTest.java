package edu.hitsz.aircraft;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    HeroAircraft heroAircraft;
    HeroAircraft heroAircraft2;

    @Test
    @DisplayName("getInstance_test")
    void getInstanceTest() {
        heroAircraft = HeroAircraft.getInstance();
        heroAircraft2 = HeroAircraft.getInstance();
        assertEquals(heroAircraft, heroAircraft2);
    }

    @ParameterizedTest
    @DisplayName("decrease_hp_test")
    @CsvSource({"100,90", "10,0", "5,0", "-10,0"})
    void decreaseHpTest(int num1, int num2) {
        heroAircraft = HeroAircraft.getInstance();
        heroAircraft.setHp(num1);
        heroAircraft.decreaseHp(10);
        assertEquals(num2, heroAircraft.getHp());
    }

    @Test
    @DisplayName("HeroAircraft_shoot_Test")
    void shootTest() {
        heroAircraft = HeroAircraft.getInstance();
        assertNotNull(heroAircraft.shoot(heroAircraft));
    }
}