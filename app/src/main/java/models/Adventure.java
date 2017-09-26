package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ishibori on 07/09/2017.
 */

public class Adventure implements Serializable{
    public String Title;
    public ArrayList<AdventureStage> Stages;
    public ArrayList<StageOption> SelectedOptionsHist;
    public int ActiveStage;
    public boolean IsFinished = false;
    public boolean IsActive = false;
    public int TargetLevel;
    public int AwardedXp;
    public int AwardedGold;

    public Adventure(String title, ArrayList<AdventureStage> stages, int targetLevel, int awardedXp, int awardedGold){
        this.Title = title;
        this.Stages = stages;
        this.TargetLevel = targetLevel;
        this.AwardedXp = awardedXp;
        this.AwardedGold = awardedGold;
        this.ActiveStage = 0;

        this.SelectedOptionsHist = new ArrayList<>();
    }

    public AdventureStage getActiveOrFirstAdventureStage(){
        AdventureStage advStageToReturn = null;

        if(Stages != null && Stages.size() > 0) {
            for (AdventureStage advStage : Stages) {
                if(advStage.IsActive){
                    advStageToReturn = advStage;
                }
            }

            if(advStageToReturn == null){
                advStageToReturn =  Stages.get(0);
            }
        }

        return advStageToReturn;
    }

    public void setNextAdventureStage(int pos, String optionalMsg, boolean beforeMain){
        AdventureStage oldStage = getActiveOrFirstAdventureStage();
        oldStage.IsActive = false;

        AdventureStage newStage = Stages.get(pos);
        newStage.IsActive = true;

        if(optionalMsg != null) {
            newStage.addOptionalMsg(optionalMsg, beforeMain);
        }
    }

    public StageOption getLastSelectedOption(){
        if(this.SelectedOptionsHist != null && this.SelectedOptionsHist.size() > 0){
            return SelectedOptionsHist.get(SelectedOptionsHist.size()-1);
        }
        else{
            return null;
        }
    }
}
