package utils;

import java.util.ArrayList;

import models.BaseAbility;
import models.CharClass;

import static utils.AbilityUtils.ABILITY_AG;
import static utils.AbilityUtils.ABILITY_CON;
import static utils.AbilityUtils.ABILITY_INT;
import static utils.AbilityUtils.ABILITY_PRE;
import static utils.AbilityUtils.ABILITY_STR;
import static utils.AbilityUtils.ABILITY_WIS;

/**
 * Created by Ishibori on 04/09/2017.
 */

public class ClassUtils {
    private static ArrayList<CharClass> Classes;

    public static void initClasses() {
        Classes = new ArrayList<CharClass>();

        CharClass warrior_class = new CharClass(CharClass.CharClassType.Warrior);
        warrior_class.ClassBonuses.add(new BaseAbility(BaseAbility.Abilities.Strength, 1));
        warrior_class.ClassBonuses.add(new BaseAbility(BaseAbility.Abilities.Constitution, 2));
        warrior_class.ClassBonuses.add(new BaseAbility(BaseAbility.Abilities.Presence, 1));
        warrior_class.HitpointsPrLevel = 10;
        warrior_class.ToHitPrLevel = 1.0;
        warrior_class.DefencePrLevel = 1.2;
        warrior_class.DamagePrLevel = 0.7;

        CharClass ranger_class = new CharClass(CharClass.CharClassType.Ranger);
        ranger_class.ClassBonuses.add(new BaseAbility(BaseAbility.Abilities.Constitution, 2));
        ranger_class.ClassBonuses.add(new BaseAbility(BaseAbility.Abilities.Agility, 1));
        ranger_class.ClassBonuses.add(new BaseAbility(BaseAbility.Abilities.Wisdom, 1));
        ranger_class.RestrictedItems.add(ItemUtils.ItemChildClass.PlateMail);
        ranger_class.RestrictedItems.add(ItemUtils.ItemChildClass.WallShield);
        ranger_class.RestrictedItems.add(ItemUtils.ItemChildClass.FullHelm);
        ranger_class.HitpointsPrLevel = 8;
        ranger_class.ToHitPrLevel = 1.0;
        ranger_class.DefencePrLevel = 1.2;
        ranger_class.DamagePrLevel = 1.2;

        CharClass rogue_class = new CharClass(CharClass.CharClassType.Rogue);
        rogue_class.ClassBonuses.add(new BaseAbility(BaseAbility.Abilities.Agility, 3));
        rogue_class.ClassBonuses.add(new BaseAbility(BaseAbility.Abilities.Intelligence, 1));
        rogue_class.RestrictedItems.add(ItemUtils.ItemChildClass.PlateMail);
        rogue_class.RestrictedItems.add(ItemUtils.ItemChildClass.WallShield);
        rogue_class.RestrictedItems.add(ItemUtils.ItemChildClass.FullHelm);
        rogue_class.RestrictedItems.add(ItemUtils.ItemChildClass.Hammer);
        rogue_class.RestrictedItems.add(ItemUtils.ItemChildClass.ChainMail);
        rogue_class.HitpointsPrLevel = 6;
        rogue_class.ToHitPrLevel = 1.2;
        rogue_class.DefencePrLevel = 0.8;
        rogue_class.DamagePrLevel = 2.0;

        Classes.add(warrior_class);
        Classes.add(rogue_class);
        Classes.add(ranger_class);
    }

    public static ArrayList<CharClass> getClasses()
    {
        return Classes;
    }

    public static CharClass getSpecificClass(CharClass.CharClassType classType)
    {
        for (CharClass cc : Classes) {
            if(cc.ClassType == classType)
            {
                return cc;
            }
        }

        return null;
    }
}
