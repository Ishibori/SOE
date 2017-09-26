package utils;

import java.util.ArrayList;

import models.Character;
import models.CombatEngine;
import models.Monster;

/**
 * Created by Ishibori on 19/09/2017.
 */

public class CombatUtils {

    //Create combat engine
    //Add two sides
    // calc initiative for each opponent
    // add to combat list in order
    // Attack = AttckBonus + D20
    // Defence = DefenceBonus + D20

    //factory method
    public static CombatEngine getCombatEngine(ArrayList<Monster> monsters, int monsterSituationBonus, Character character, int characterSituationBonus){
        CombatEngine engine = new CombatEngine(monsters, character);
        engine.initEngine(monsterSituationBonus, characterSituationBonus);
        return engine;
    }
}
