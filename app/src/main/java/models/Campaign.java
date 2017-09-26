package models;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;

import adventure_fragments.adventure_stage_frag;
import adventure_fragments.campaign_selection_frag;

import static models.AdventureStage.StageType.Introduction;

/**
 * Created by Ishibori on 15/09/2017.
 */

public class Campaign implements Serializable{
    public static String CAMPAIGN_KEY = "CAMPAIGN_KEY";
    public Character user_char;

    public String Title;
    public boolean IsActive = false;
    public boolean IsAccesible = false;
    public ArrayList<Adventure> Adventures;
    public int minLevel;
    public int maxLevel;

    public Campaign(String title, ArrayList<Adventure> adventures, boolean isAccesible, int minLevel, int maxLevel, Character user_char){
        this.Title = title;
        this.Adventures = adventures;
        this.IsAccesible = isAccesible;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.user_char = user_char;
    }

    public Adventure getActiveOrFirstAdventure(){
        Adventure advToReturn = null;

        if(Adventures != null && Adventures.size() > 0) {
            for (Adventure adv : Adventures) {
                if(adv.IsActive){
                    advToReturn = adv;
                }
            }

            if(advToReturn == null){
                advToReturn =  Adventures.get(0);
            }

            advToReturn.IsActive = true;
        }

        return advToReturn;
    }

    public Fragment getNextStageFragment(){
        Fragment fragToReturn = null;

        Adventure currentAdventure = this.getActiveOrFirstAdventure();
        if(currentAdventure != null) {

           StageOption so = currentAdventure.getLastSelectedOption();
            if(so != null && so.Action == StageOption.StageActionType.Exit){
                fragToReturn = new campaign_selection_frag();
            }
            else {
                AdventureStage currentAdventureStage = currentAdventure.getActiveOrFirstAdventureStage();
                if (currentAdventureStage != null) {
                    fragToReturn = new adventure_stage_frag();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable(Campaign.CAMPAIGN_KEY, this);
                    fragToReturn.setArguments(bundle1);
                }
            }
        }

        return fragToReturn;
    }

    public static Fragment getCampaignFragment(){
        return new campaign_selection_frag();
    }

    public AdventureStage getNextStage(){
        AdventureStage currentAdventureStage = null;

        Adventure currentAdventure = this.getActiveOrFirstAdventure();
        if(currentAdventure != null) {
            currentAdventureStage = currentAdventure.getActiveOrFirstAdventureStage();
            if (currentAdventureStage != null) {
                return currentAdventureStage;
            }
        }

        return null;
    }

    public Adventure getNextAdventure(){
        Adventure currentAdventure = null;

        currentAdventure = this.getActiveOrFirstAdventure();
        if(currentAdventure != null) {
          return currentAdventure;
        }

        return null;
    }


    public String getTargetLevelString(){
        return "Level " + minLevel + "-" + maxLevel;
    }
}
