package models;

import java.io.Serializable;

/**
 * Created by Ishibori on 09/09/2017.
 */

public class SkillBonus implements Serializable{
    public enum Skills {
        Attack,
        Defence,
        Hitpoints
    }

    public Skills SkillType;
    public int Bonus;

    public SkillBonus(Skills skillType, int bonus)
    {
        this.SkillType = skillType;
        this.Bonus = bonus;
    }

    public SkillBonus clone()
    {
        return new SkillBonus(this.SkillType, this.Bonus);
    }
}
