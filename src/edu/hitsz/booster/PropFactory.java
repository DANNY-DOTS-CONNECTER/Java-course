package edu.hitsz.booster;

import edu.hitsz.aircraft.AbstractAircraft;

public interface PropFactory {
    AbstractBoosterPacks createOperation(int locationX, int locationY);
}
