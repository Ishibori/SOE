package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ishibori on 15/09/2017.
 */

public class StageOption implements Serializable{
    public enum StageActionType{
        Fight,
        Escape,
        Select,
        Exit
    }

    public String OptionText;
    public StageActionType Action;
    public boolean WasSelected = false;
    public int NextStep;
    public String MsgForNextStage;
    public boolean BeforeMainNextStage;

    public StageOption(String optionText, StageActionType action, int nextStep, String msg, boolean beforeMain){
        this.OptionText = optionText;
        this.Action = action;
        this.NextStep = nextStep;
        this.MsgForNextStage = msg;
        this.BeforeMainNextStage = beforeMain;
    }
}
