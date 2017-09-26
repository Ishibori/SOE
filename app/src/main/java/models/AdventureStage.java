package models;

import java.io.Serializable;
import java.util.ArrayList;

import utils.MonsterUtils;

/**
 * Created by Ishibori on 07/09/2017.
 */

public class AdventureStage implements Serializable{
    public enum StageType{
        Introduction,
        Combat,
        Battle,
        Exploration,
        Decision,
        SkillCheck,
        Puzzle,
        Enlightment,
        Treasure,
        Conclusion,
        Other,
    }

    public Adventure ParentAdventure;
    public String Title;
    public StageType Stage;
    public boolean IsActive = false;
    public String MainText;
    public String OptionalMsg;
    public ArrayList<Monster> Monsters;
    public ArrayList<StageOption> StageOptions;

    public AdventureStage(Adventure parent, String title, StageType stage, String mainText, ArrayList<Monster> monsters, ArrayList<StageOption> stageOptions)
    {
        this.ParentAdventure = parent;
        this.Title = title;
        this.Stage = stage;
        this.MainText = mainText;
        this.Monsters = monsters;
        this.StageOptions = stageOptions;
    }

    public StageOption getSelectOption(){
        if(StageOptions != null){
            for (StageOption so : StageOptions) {
                if(so.WasSelected){
                    return so;
                }
            }
        }

        return null;
    }

    public void addOptionalMsg(String msg, boolean beforeMain){
        this.OptionalMsg = msg;

        if(beforeMain){
            MainText = msg + MainText;
        }
        else {
            MainText += msg;
        }
    }

    public ArrayList<Monster> getMonsters(){
        Monsters = new ArrayList<>();
        Monsters.add(MonsterUtils.getRandomStandardMonster());
        Monsters.add(MonsterUtils.getRandomStandardMonster());
        Monsters.add(MonsterUtils.getRandomStandardMonster());
        return  Monsters;
    }

}
