package utils;

import android.content.Context;

import com.soe.ishibori.stagesofexperience.R;

import java.util.ArrayList;

import models.Adventure;
import models.AdventureStage;
import models.Campaign;
import models.Character;
import models.StageOption;

/**
 * Created by Ishibori on 07/09/2017.
 */

public class AdventureUtils {
    public enum TimeOfDay{
        Dawn,
        Morning,
        Midday,
        Afternoon,
        Sunset,
        Dusk,
        Evening,
        Night
    }

    //WeatherTypes
    public enum WeatherType
    {
        Rain,
        RainStorm,
        Snow,
        Blizzard,
        Hail,
        Sun,
        Clouds,
        Mist
    }

    //Overall adventure setting
    public enum TerrainType {
        Plain,
        Hills,
        Caves,
        Mountain,
        Forest,
        Jungle,
        Swamp,
        Desert,
        Coast,
        Plane,
        City,
        Underground,
        Dungeon
    }

    //Encounter types
    public enum EncounterTypes{
        Combat,
        Puzzle,
        Decision,
        Treasure,
        Other
    }

    public static ArrayList<Campaign> Campaigns;

    public static void initCampaigns(Context context, Character user_char) {
        Campaigns = new ArrayList<>();

        //The Dark Forest Campaign
        ArrayList<Adventure> adventures = new ArrayList<>();
        ArrayList<AdventureStage> stages = new ArrayList<>();

        int awardedGold = 50;
        int awardedXp = 700;
        //TODO: change architecture
        //stage1
        String intro = context.getResources().getString(R.string.adventure_heading_north_intro);
        intro = intro.replace("{1}",Integer.toString(awardedGold));

        StageOption option1 = new StageOption("Start", StageOption.StageActionType.Select,1,null,true);
        StageOption option2 = new StageOption("Cancel", StageOption.StageActionType.Exit,0,null,true);
        ArrayList<StageOption> stageOptions = new ArrayList<>();
        stageOptions.add(option1);
        stageOptions.add(option2);

        //stage2
        String makeCamp = context.getResources().getString(R.string.adventure_heading_north_stage1);

        StageOption option3 = new StageOption("The Forest", StageOption.StageActionType.Select,2, "The forest provides great shelter, but something disturbs you during the night. A wolf with cold blue eyes is jumping for your throat.",true);
        StageOption option4 = new StageOption("The Plains", StageOption.StageActionType.Select,2,"There is no shelter on the open ground and the night is cold, but you have a fairly decent overview of your surroundsings. When a band of goblins approach you quickly spot them!",true);
        ArrayList<StageOption> stageOptions1 = new ArrayList<>();
        stageOptions1.add(option3);
        stageOptions1.add(option4);

        //stage2
        String critterAmbush = "You have to fight!";

        StageOption option5 = new StageOption("Attack", StageOption.StageActionType.Fight,3, "",true);
        StageOption option6 = new StageOption("Run", StageOption.StageActionType.Exit,0,"You run home!",true);
        ArrayList<StageOption> stageOptions2 = new ArrayList<>();
        stageOptions2.add(option5);
        stageOptions2.add(option6);

        ArrayList<StageOption> stageOptions3 = new ArrayList<>();
        StageOption option7 = new StageOption("Attack", StageOption.StageActionType.Select,4, "",true);
        stageOptions3.add(option7);

        ArrayList<StageOption> stageOptions4 = new ArrayList<>();
        StageOption option8 = new StageOption("Attack", StageOption.StageActionType.Select,5, "",true);
        stageOptions4.add(option8);

        ArrayList<StageOption> stageOptions5 = new ArrayList<>();
        StageOption option9 = new StageOption("Attack", StageOption.StageActionType.Select,6, "",true);
        stageOptions5.add(option9);

        ArrayList<StageOption> stageOptions6 = new ArrayList<>();
        StageOption option10 = new StageOption("Attack", StageOption.StageActionType.Select,7, "",true);
        stageOptions6.add(option10);

        ArrayList<StageOption> stageOptions7 = new ArrayList<>();
        StageOption option11 = new StageOption("Attack", StageOption.StageActionType.Exit,0, "",true);
        stageOptions7.add(option11);

        // create adventure
        Adventure advHeadingNorth = new Adventure("Heading North", stages, 1, awardedXp, awardedGold);
        adventures.add(advHeadingNorth);

        //add stages
        stages.add(new AdventureStage(advHeadingNorth, "Introduction", AdventureStage.StageType.Introduction, intro, null, stageOptions));
        stages.add(new AdventureStage(advHeadingNorth,"The Camp", AdventureStage.StageType.Decision, makeCamp, null, stageOptions1));
        stages.add(new AdventureStage(advHeadingNorth,"The Ambush", AdventureStage.StageType.Combat, critterAmbush, null,stageOptions2));
        stages.add(new AdventureStage(advHeadingNorth, "The Search", AdventureStage.StageType.Exploration, "explore", null,stageOptions3));
        stages.add(new AdventureStage(advHeadingNorth,"The Pursuit", AdventureStage.StageType.SkillCheck, "pursue", null,stageOptions4));
        stages.add(new AdventureStage(advHeadingNorth,"Wolf's End", AdventureStage.StageType.Puzzle, "What happened", null,stageOptions5));
        stages.add(new AdventureStage(advHeadingNorth,"Wolf's End", AdventureStage.StageType.Combat, "fight and retreat", null,stageOptions6));
        stages.add(new AdventureStage(advHeadingNorth,"The Stand", AdventureStage.StageType.Battle, "The stand", null,stageOptions7));

        Campaigns.add(new Campaign("The Dark Forest", adventures, true, 1, 4, user_char));
    }

    public static Campaign getActiveCampaign(){
        if(Campaigns != null && Campaigns.size() > 0) {
            for (Campaign campaign : Campaigns) {
                if(campaign.IsActive){
                    return campaign;
                }
            }
        }

        return null;
    }

    public static ArrayList<Campaign> getCampaigns()
    {
        return Campaigns;
    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "strings", context.getPackageName());
    }
}
