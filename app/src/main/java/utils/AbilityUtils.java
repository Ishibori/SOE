package utils;

import java.util.ArrayList;

import models.BaseAbility;

/**
 * Created by Ishibori on 04/09/2017.
 */

public class AbilityUtils {
    public final static String ABILITY_STR = "Strength";
    public final static String ABILITY_CON = "Constitution";
    public final static String ABILITY_AG = "Agility";
    public final static String ABILITY_INT = "Intelligence";
    public final static String ABILITY_WIS = "Wisdom";
    public final static String ABILITY_PRE = "Presence";

    private static ArrayList<BaseAbility> Abilities;

    public static void initAbilities() {
        Abilities = new ArrayList<BaseAbility>();

        BaseAbility bs_strength = new BaseAbility();
        bs_strength.AbilityType = BaseAbility.Abilities.Strength;
        bs_strength.Description = "Measures the characters strength.";

        BaseAbility bs_constitution = new BaseAbility();
        bs_constitution.AbilityType = BaseAbility.Abilities.Constitution;
        bs_constitution.Description = "Measures the characters constitution.";

        BaseAbility bs_agility = new BaseAbility();
        bs_agility.AbilityType = BaseAbility.Abilities.Agility;
        bs_agility.Description = "Measures the characters agility.";

        BaseAbility bs_intelligence = new BaseAbility();
        bs_intelligence.AbilityType = BaseAbility.Abilities.Intelligence;
        bs_intelligence.Description = "Measures the characters intelligence.";

        BaseAbility bs_wisdom = new BaseAbility();
        bs_wisdom.AbilityType = BaseAbility.Abilities.Wisdom;
        bs_wisdom.Description = "Measures the characters Wisdom.";

        BaseAbility bs_presence = new BaseAbility();
        bs_presence.AbilityType = BaseAbility.Abilities.Presence;
        bs_presence.Description = "Measures the characters Presence.";

        Abilities.add(bs_strength);
        Abilities.add(bs_constitution);
        Abilities.add(bs_agility);
        Abilities.add(bs_intelligence);
        Abilities.add(bs_wisdom);
        Abilities.add(bs_presence);
    }

    public static ArrayList<BaseAbility> getAbilities()
    {
        return Abilities;
    }

    public static int getAbilityBonus(int value)
    {
        int bonus = 0;

        if(value > 10) {
            bonus = (int) Math.floor((value-10) / 2);
        } else if(value < 10){
            bonus = (int) -Math.ceil((10-value) / 2);
        }

        return bonus;
    }
}
