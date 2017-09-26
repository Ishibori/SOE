package models;

import java.io.Serializable;

/**
 * Created by Ishibori on 03/09/2017.
 */

public class BaseAbility implements Serializable{
    public enum Abilities {
        Strength,
        Constitution,
        Agility,
        Intelligence,
        Wisdom,
        Presence
    }

    public Abilities AbilityType;
    public String Description;
    public int Value;
    public int Bonus;

    public BaseAbility()
    {

    }

    public BaseAbility(Abilities id, int bonus)
    {
        this.AbilityType = id;
        this.Bonus = bonus;
    }

    public BaseAbility(Abilities id, String desc, int val, int bonus)
    {
        this.AbilityType = id;
        this.Description = desc;
        this.Value = val;
        this.Bonus = bonus;
    }

    public BaseAbility clone()
    {
        return new BaseAbility(this.AbilityType, this.Description, this.Value, this.Bonus);
    }
}
