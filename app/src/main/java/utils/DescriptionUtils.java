package utils;

import java.util.ArrayList;

import models.CharClass;
import models.Character;

/**
 * Created by Ishibori on 04/09/2017.
 */

public class DescriptionUtils {
    public static ArrayList<String> warriorTitles = new ArrayList<>();
    public static ArrayList<String> rogueTitles = new ArrayList<>();
    public static ArrayList<String> mageTitles = new ArrayList<>();

    public static ArrayList<String> background = new ArrayList<>();

    public static void initDescription() {
        background.add("parents are commoners.");
        background.add("parents are farmers.");
        background.add("family runs a prosperous trading company.");
        background.add("parents are from a lesser noble house.");
        background.add("parents are from a greater noble house.");
        background.add("parents are related to the royal familiy.");

        warriorTitles.add("an unknown");
        warriorTitles.add("a rookie");
        warriorTitles.add("a foot Soldier");
        warriorTitles.add("a squire");
        warriorTitles.add("a knight");
        warriorTitles.add("an Arms Master");

        rogueTitles.add("an unknown");
        rogueTitles.add("a street muggler");
        rogueTitles.add("a trickster");
        rogueTitles.add("a thief");
        rogueTitles.add("an assasin");
        rogueTitles.add("a Shade Master");

        mageTitles.add("an unknown");
        mageTitles.add("an apprentice");
        mageTitles.add("an initiate");
        mageTitles.add("an enchanter");
        mageTitles.add("a magus");
        mageTitles.add("an Essence Master");
    }

    public static String getDescription(Character userChar)
    {
        StringBuilder titleBuilder = new StringBuilder();
        titleBuilder.append("You are " + userChar.Name + ", " + getTitleString(userChar) + ". ");
        titleBuilder.append("Your " + getPositionString(userChar));

        return titleBuilder.toString();
    }

    public static String getPositionString(Character userChar)
    {
        int socialIndex = getSocialBackgroundIndex(userChar.BackgroundStatus);
        return background.get(socialIndex);
    }

    public static String getTitleString(Character userChar)
    {
        String title = "Unknown";

            switch (userChar.CharacterClass.ClassType){
                case Warrior:{
                    title = getTitle(warriorTitles, userChar.Level) + " " + userChar.CharacterRace.Race.toString().toLowerCase() + " warrior";
                    break;
                }

                case Rogue:{
                    title = getTitle(rogueTitles, userChar.Level) + " " + userChar.CharacterRace.Race.toString().toLowerCase() + " rogue";
                    break;
                }

                case Ranger:{
                    title = getTitle(mageTitles, userChar.Level) + " " + userChar.CharacterRace.Race.toString().toLowerCase() + " ranger";
                    break;
                }
            }

            return title;
    }

    public static String getTitle(ArrayList<String> titles, int level)
    {
        int index = getLevelIndex(level);
        return titles.get(index);
    }

    public static String getStatusString(Character user_char)
    {
        return "Level " + user_char.Level + " " + user_char.CharacterRace.Race.toString() + " " + user_char.CharacterClass.ClassType.toString();
    }

    public static int getSocialBackgroundIndex(int backgroundStatus)
    {
        int index = 0;

        if(backgroundStatus <= 69) // 69% commoner
        {
            index = 0;
        }
        else if(backgroundStatus >= 70 && backgroundStatus <= 78) // 9% merchant
        {
            index = 1;
        }
        else if(backgroundStatus >= 79 && backgroundStatus <= 85) // 7% lesser noble
        {
            index = 2;
        }
        else if(backgroundStatus >= 86 && backgroundStatus <= 92) // 7% greater noble
        {
            index = 3;
        }
        else if(backgroundStatus >= 93 && backgroundStatus <= 97) // 5% royal relative
        {
            index = 4;
        }
        else if(backgroundStatus >= 98) // 3% royal
        {
            index = 5;
        }

        return index;
    }

    public static int getLevelIndex(int level)
    {
        int index = 0;

        if(level == 1)
        {
            index = 0;
        }
        else if(level >= 2 && level <= 3)
        {
            index = 1;
        }
        else if(level >= 4 && level <= 6)
        {
            index = 2;
        }
        else if(level >= 7 && level <= 10)
        {
            index = 3;
        }
        else if(level >= 11 && level <= 15)
        {
            index = 4;
        }
        else if(level >= 16)
        {
            index = 5;
        }

        return index;
    }
}
