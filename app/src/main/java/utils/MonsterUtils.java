package utils;

import java.util.ArrayList;

import helpers.Range;
import models.Character;
import models.Monster;

/**
 * Created by Ishibori on 07/09/2017.
 */

public class MonsterUtils {
    public enum MonsterGroup{
        Dragons,
        Demons,
        Celestrials,
        Undead,
        WereCreatures,
        Constructs,
        Humanoids,
        Orcs,
        Trolls,
        Mammels
    }

    public enum TreasureLevel{
        None,
        Poor,
        Normal,
        Wealthy,
        Extraordinary,
        Unique,
    }

    public static ArrayList<Monster> StandardMonsters;

    public static void initMonsterUtils(){
        StandardMonsters = new ArrayList<>();

        StandardMonsters.add(new Monster(MonsterGroup.Orcs, "Goblin","",null,0, 1,1,6, new Range(1,5),TreasureLevel.Poor,1));
        StandardMonsters.add(new Monster(MonsterGroup.Orcs, "Black Orc","",null,1, 3,3,15,new Range(2,7),TreasureLevel.Normal,3));
        //StandardMonsters.add(new Monster(MonsterGroup.Dragons, "Ancient Black Dragon","",null,5, 25,35,200,new Range(3,10),TreasureLevel.Unique,20));
    }

    public static Monster getRandomStandardMonster(){
        int index = DiceUtils.getSingleDiceRoll(0,StandardMonsters.size()-1);
        return StandardMonsters.get(index).clone();
    }

    public static Monster getSpecificStandardMonster(String name){
        for (Monster m: StandardMonsters) {
            if(m.Name.equals(name)){
                return m.clone();
            }
        }

        return null;
    }

    public static int calculateXp(Monster monster, Character user_char){
        int next_level_xp = CharacterUtils.getXpForLevel(user_char.Level);
        int base_xp = next_level_xp / 3;
        double xp_multiplier = (double)monster.BaseLevel / (double)user_char.Level;
        return (int)(base_xp * xp_multiplier);
    }


}
