package utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

import models.BaseAbility;
import models.CharClass;
import models.Character;
import models.Item;
import models.Race;

/**
 * Created by Ishibori on 03/09/2017.
 */

public class CharacterUtils {
    public static Random RANDOM_GENERATOR = new Random();
    public static Character user_character;

    public static int getMoneyFromEconomicStatus(int economicStatus)
    {
        int factor = DescriptionUtils.getSocialBackgroundIndex(economicStatus);
        int money = factor * (RANDOM_GENERATOR.nextInt(10)+1) + factor * 5 + 1; // min:1 max:76

        return money;
    }

    public static ArrayList<BaseAbility> Abilities;
    public static ArrayList<Race> Races;
    public static ArrayList<CharClass> Classes;
    public static ArrayList<Integer> Levels;

    public static void Init(Context context)
    {
        AbilityUtils.initAbilities();
        ClassUtils.initClasses();
        RaceUtils.initRaces();
        DescriptionUtils.initDescription();
        ItemUtils.initItems(context);
        MonsterUtils.initMonsterUtils();

        Abilities = AbilityUtils.getAbilities();
        Races = RaceUtils.getRaces();
        Classes = ClassUtils.getClasses();
        Levels = createXpLevels();
    }

    public static ArrayList<Race> getRaces()
    {
        return Races;
    }
    public static ArrayList<CharClass> getClasses()
    {
        return Classes;
    }

    public static Character getBasicCharacter(String name, Race.Races race, CharClass.CharClassType charClass)
    {
        Character user_char = new Character(name, RaceUtils.getSpecificRace(race), ClassUtils.getSpecificClass(charClass));

        return user_char;
    }

    //note: the item we provide is a part of the list, but if it is not equipped it will not interfere with the count!
    public static int canEquipItem(Character user_char, Item item)
    {
        //check for class restrictions
        for (ItemUtils.ItemChildClass icc :user_char.CharacterClass.RestrictedItems) {
            if(icc == item.ItemChildClass){
                return -1;
            }
        }

        //check for item amount
        int currentCount = 0;
        for (Item i :user_char.Items) {
            if(i.ItemMeta.ItemParentClass == item.ItemMeta.ItemParentClass && i.isEquipped){
                currentCount++;
            }
        }

        if(currentCount >= ItemUtils.getMaxNumberOfAllowedItems(item.ItemMeta.ItemParentClass)){
            return -2;
        }

        //no problems
        return 0;
    }

    public static ArrayList<Integer> createXpLevels()
    {
        ArrayList<Integer> levelXp = new ArrayList<>();

        for(int i = 1; i < 20; i++) {
            levelXp.add(i * 1000 + (int)Math.pow(2,i));
        }

        return levelXp;
    }

    public static int getXpForLevel(int level)
    {
        return Levels.get(level-1);
    }
}
