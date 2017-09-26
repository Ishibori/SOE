package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import helpers.ICombatant;
import helpers.Range;
import utils.DiceUtils;
import utils.MonsterUtils;

/**
 * Created by Ishibori on 07/09/2017.
 */

public class Monster implements ICombatant, Serializable{
    public enum ChallengeLevel{
        Lesser,
        Normal,
        Greater
    }

    public UUID Identifier;
    public MonsterUtils.MonsterGroup ParentGroup;
    public String Name;
    public String Description;
    public ArrayList<BaseAbility> Abilities;
    public int Initiative;
    public int InitiativeBonus;
    public Range DamageRange;
    public int Attack;
    public int Defence;
    public int Hitpoints;
    public MonsterUtils.TreasureLevel TreasureLevel;
    public int BaseLevel;
    public int ActualLevel;

    public Monster(MonsterUtils.MonsterGroup group, String name, String descriptoin, ArrayList<BaseAbility> abilities, int initiativeBonus, int attack, int defence, int hitpoints, Range damageRange, MonsterUtils.TreasureLevel treasureLevel, int baseLevel){
        this.Identifier = UUID.randomUUID();
        this.ParentGroup = group;
        this.Name = name;
        this.Description = descriptoin;
        this.Abilities = abilities;
        this.InitiativeBonus = initiativeBonus;
        this.DamageRange = damageRange;
        this.Attack = attack;
        this.Defence = defence;
        this.Hitpoints = hitpoints;
        this.TreasureLevel = treasureLevel;
        this.BaseLevel = baseLevel;
        this.ActualLevel = BaseLevel;
    }

    public Monster(UUID identifier, MonsterUtils.MonsterGroup group, String name, String descriptoin, ArrayList<BaseAbility> abilities, int initiativeBonus, int attack, int defence, int hitpoints, Range damageRange, MonsterUtils.TreasureLevel treasureLevel, int baseLevel){
        this.Identifier = identifier;
        this.ParentGroup = group;
        this.Name = name;
        this.Description = descriptoin;
        this.Abilities = abilities;
        this.InitiativeBonus = initiativeBonus;
        this.DamageRange = damageRange;
        this.Attack = attack;
        this.Defence = defence;
        this.Hitpoints = hitpoints;
        this.TreasureLevel = treasureLevel;
        this.BaseLevel = baseLevel;
        this.ActualLevel = BaseLevel;
    }

    public int setInitiative(int situationBonus){
        this.Initiative = DiceUtils.getSingleDiceRoll(1,20) + InitiativeBonus + situationBonus;
        return this.Initiative;
    }

    public void RandomizeChallengeLevel(){
        int direction = DiceUtils.getSingleDiceRoll(-1,1);

        if(direction == 0 || BaseLevel < 2)
            return;

        int extend = DiceUtils.getSingleDiceRoll(1, (BaseLevel / 3) + 1);

        ActualLevel += (direction * extend);
        Hitpoints += (extend * direction) * 5;
        Attack += (extend * direction);
        Defence += (extend * direction);

        //alter treasure level
        int currentTreasureLevel = TreasureLevel.ordinal();
        if(TreasureLevel != MonsterUtils.TreasureLevel.None && TreasureLevel != MonsterUtils.TreasureLevel.Unique){
            TreasureLevel = MonsterUtils.TreasureLevel.values()[currentTreasureLevel+direction];
        }
    }

    public int getGold(){
        int treasure = 0;

        switch (TreasureLevel){
            case None:{
                treasure = 0;
            }
            case Poor:{
                treasure = 5 + DiceUtils.getSingleDiceRoll(1,5);
            }
            case Normal:{
                treasure = 10 + DiceUtils.getSingleDiceRoll(1,15);
            }
            case Wealthy:{
                treasure = 25 + DiceUtils.getSingleDiceRoll(1,25);
            }
            case Extraordinary:{
                treasure = 50 + DiceUtils.getSingleDiceRoll(1,50);
            }
            case Unique:{
                treasure = 100 + DiceUtils.getSingleDiceRoll(1,150);
            }
        }

        return treasure;
    }

    public Monster clone(){
        return new Monster(Identifier, ParentGroup, Name,Description, Abilities, InitiativeBonus, Attack, Defence, Hitpoints, DamageRange, TreasureLevel, BaseLevel);
    }

    @Override
    public UUID getIdentifier(){return this.Identifier;}

    @Override
    public int getInitiative() {
        return Initiative;
    }

    @Override
    public boolean isCharacter() {
        return false;
    }

    @Override
    public int getAttackValue() {
        return this.Attack;
    }

    @Override
    public int getDefenceValue() {
        return this.Defence;
    }

    @Override
    public int getCurrentHitpoints() {
        return this.Hitpoints;
    }

    @Override
    public int getDamage() {
        return DiceUtils.getSingleDiceRoll(DamageRange.Min, DamageRange.Max);
    }

    @Override
    public void subtractHitpoints(int hitpoints) {
        this.Hitpoints -= hitpoints;
    }

    @Override
    public boolean isDead(){
        if(Hitpoints <= 0){
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String getName(){
        return Name;
    }
}
