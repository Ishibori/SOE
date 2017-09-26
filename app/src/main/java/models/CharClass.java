package models;

import java.io.Serializable;
import java.util.ArrayList;

import utils.ItemUtils;

/**
 * Created by Ishibori on 03/09/2017.
 */

public class CharClass implements Serializable {
    //public String Name;
    public enum CharClassType{
        Warrior,
        Rogue,
        Ranger
    }

    public ArrayList<BaseAbility> ClassBonuses;
    public ArrayList<ItemUtils.ItemChildClass> RestrictedItems;
    public CharClassType ClassType;
    public int HitpointsPrLevel;
    public double ToHitPrLevel;
    public double DamagePrLevel;
    public double DefencePrLevel;

    public CharClass(CharClassType classType)
    {
        ClassBonuses = new ArrayList<>();
        RestrictedItems = new ArrayList<>();
        this.ClassType = classType;
    }

    public int getClassBonus(BaseAbility.Abilities ability)
    {
        for (BaseAbility bs : ClassBonuses) {
            if(bs.AbilityType == ability)
            {
                return bs.Bonus;
            }
        }

        return 0;
    }

    public BaseAbility.Abilities getAttackAbility()
    {
        return BaseAbility.Abilities.Strength;
    }

    public BaseAbility.Abilities getDefenceAbility()
    {
        return BaseAbility.Abilities.Agility;
    }

    public BaseAbility.Abilities getHitpointsAbility()
    {
        return BaseAbility.Abilities.Constitution;
    }


}
