package utils;

import java.util.ArrayList;

import models.BaseAbility;
import models.Race;

import static utils.AbilityUtils.ABILITY_AG;
import static utils.AbilityUtils.ABILITY_CON;
import static utils.AbilityUtils.ABILITY_INT;
import static utils.AbilityUtils.ABILITY_PRE;
import static utils.AbilityUtils.ABILITY_STR;

/**
 * Created by Ishibori on 04/09/2017.
 */

public class RaceUtils {
   // public final static String RACES_HUMAN = "Human";
   // public final static String RACES_DWARF = "Dwarf";
   // public final static String RACES_ELF = "Elf";

    private static ArrayList<Race> Races = new ArrayList<Race>();

    public static void initRaces() {
        // Races
        Races = new ArrayList<Race>();

        Race human = new Race();
        human.Race = Race.Races.Human;
        human.Description = "Human";
        human.RaceBonuses.add(new BaseAbility(BaseAbility.Abilities.Strength, 1));
        human.RaceBonuses.add(new BaseAbility(BaseAbility.Abilities.Constitution, 1));
        human.RaceBonuses.add(new BaseAbility(BaseAbility.Abilities.Presence, 1));

        Race dwarf = new Race();
        dwarf.Race = Race.Races.Dwarf;
        dwarf.Description = "Dwarf from the Kil-Gir mountains.";
        dwarf.RaceBonuses.add(new BaseAbility(BaseAbility.Abilities.Strength, 1));
        dwarf.RaceBonuses.add(new BaseAbility(BaseAbility.Abilities.Constitution, 2));

        Race elf = new Race();
        elf.Race = Race.Races.Elf;
        elf.Description = "Elf of the hinterlands.";
        elf.RaceBonuses.add(new BaseAbility(BaseAbility.Abilities.Agility, 1));
        elf.RaceBonuses.add(new BaseAbility(BaseAbility.Abilities.Constitution, 1));
        elf.RaceBonuses.add(new BaseAbility(BaseAbility.Abilities.Intelligence, 1));

        Races.add(human);
        Races.add(dwarf);
        Races.add(elf);
    }

    public static ArrayList<Race> getRaces()
    {
        return Races;
    }

    public static Race getSpecificRace(Race.Races user_race)
    {
        for (Race race : Races) {
            if(race.Race == user_race)
            {
                return race;
            }
        }

        return null;
    }
}
