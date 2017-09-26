package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ishibori on 03/09/2017.
 */

public class Race implements Serializable{
    public enum Races{
        Human,
        Dwarf,
        Elf
    }

    public Races Race;
    //public String Name;
    public String Description;
    public ArrayList<BaseAbility> RaceBonuses;

    public Race()
    {
        RaceBonuses = new ArrayList<>();
    }

    public int getRaceBonus(BaseAbility.Abilities ability)
    {
        for (BaseAbility bs : RaceBonuses) {
            if(bs.AbilityType == ability)
            {
                return bs.Bonus;
            }
        }

        return 0;
    }
}
