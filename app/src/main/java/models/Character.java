package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import helpers.ICombatant;
import helpers.ItemMetaData;
import utils.AbilityUtils;
import utils.DescriptionUtils;
import utils.CharacterUtils;
import utils.DiceUtils;
import utils.ItemUtils;

/**
 * Created by Ishibori on 03/09/2017.
 */

public class Character implements Serializable, ICombatant {
    public UUID Identifier;
    public String Name = "";
    public int Level = 1;
    public int Xp;
    public int Gold;
    public int Initiative;
    public int Reputation;
    public int CurrentHitpoints;
    public CharClass CharacterClass;
    public Race CharacterRace;
    public ArrayList<BaseAbility> Abilities;
    public ArrayList<Item> Items;
    public String Description;

    // description - to consider
    public int BackgroundStatus;
    public int ParentProfession;
    public int Siblings;
    public int FamilyRelations;
    public int Eyes;
    public int Hair;

    public Character(String name, Race r, CharClass cc)
    {
        Identifier = UUID.randomUUID();
        Name = name;
        CharacterRace = r;
        CharacterClass = cc;

        Items = new ArrayList<>();
        Abilities = new ArrayList<>();

        for (BaseAbility bs : AbilityUtils.getAbilities()) {
            Abilities.add(bs);
        }

        //set level
        Level = 1;
        CurrentHitpoints = getMaxHitpoints();

        //economic status
        BackgroundStatus = DiceUtils.getSingleDiceRoll(0,100);
        Gold = CharacterUtils.getMoneyFromEconomicStatus(BackgroundStatus);
        UpdateDescription();

        //createEquipmentPositionStatusDic();
    }

    public void UpdateDescription()
    {
        Description = DescriptionUtils.getDescription(this);
    }

    public void RollForGold()
    {
        Gold = CharacterUtils.getMoneyFromEconomicStatus(BackgroundStatus);
    }

    public void healCharacter()
    {
        CurrentHitpoints = getMaxHitpoints();
    }

    public int getAttackBonus()
    {
        BaseAbility.Abilities attackAbility = CharacterClass.getAttackAbility();
        int attackBonus = getTotalAbilityBonus(attackAbility);
        attackBonus += getItemBonusesForSkill(SkillBonus.Skills.Attack);

        return (int)(this.Level * this.CharacterClass.ToHitPrLevel) + attackBonus;
    }

    public int getDefenceBonus()
    {
        BaseAbility.Abilities defenceAbility = CharacterClass.getDefenceAbility();
        int defenceBonus = getTotalAbilityBonus(defenceAbility);
        defenceBonus += getItemBonusesForSkill(SkillBonus.Skills.Defence);

        return (int)(this.Level * this.CharacterClass.DefencePrLevel) + defenceBonus;
    }

    public int getMaxHitpoints()
    {
        BaseAbility.Abilities hitpointsAbility = CharacterClass.getHitpointsAbility();
        int hitpointsAbilityBonus = getTotalAbilityBonus(hitpointsAbility);
        int hitpointsItemBonus = getItemBonusesForSkill(SkillBonus.Skills.Hitpoints);

        return this.Level * (this.CharacterClass.HitpointsPrLevel + hitpointsAbilityBonus) + hitpointsItemBonus;
    }

    public int setInitiative(int situationBonus){
        int abilityBonus = getTotalAbilityBonus(BaseAbility.Abilities.Agility);
        this.Initiative = DiceUtils.getSingleDiceRoll(1,20) + abilityBonus + situationBonus;
        return this.Initiative;
    }

    public BaseAbility getSpecificAbility(BaseAbility.Abilities ability)
    {
        for (BaseAbility baseAbility : Abilities) {
            if(baseAbility.AbilityType == ability)
            {
                return baseAbility;
            }
        }

        return null;
    }

    //roll + race + class + items
    public int getTotalAbilityBonus(BaseAbility.Abilities ability)
    {
        int totalBonus = this.CharacterClass.getClassBonus(ability);
        totalBonus += this.CharacterRace.getRaceBonus(ability);
        totalBonus += this.getItemBonusesForAbility(ability);
        BaseAbility baseAbility = getSpecificAbility(ability);

        if(baseAbility != null) {
            totalBonus += AbilityUtils.getAbilityBonus(baseAbility.Value);
        }

        return  totalBonus;
    }

    // abilities should be set in order str, con, ag, int, wis, pre
    public void setAbilities(ArrayList<Integer> abilities)
    {
        for(int i = 0; i < abilities.size(); i++)
        {
            Abilities.get(i).Value = abilities.get(i);
        }
    }

    public int getItemBonusesForAbility(BaseAbility.Abilities ability)
    {
        int sum = 0;

        for (Item i : Items) {
            if(i.AbilityMods != null) {
                for (BaseAbility bs : i.AbilityMods) {
                    if (bs.AbilityType == ability && i.isEquipped) {
                        sum += bs.Bonus;
                    }
                }
            }
        }

        return sum;
    }

    public int getItemBonusesForSkill(SkillBonus.Skills skill)
    {
        int sum = 0;

        for (Item i : Items) {
            if(i.SkillMods != null) {
                for (SkillBonus sb : i.SkillMods) {
                    if (sb.SkillType == skill && i.isEquipped) {
                        sum += sb.Bonus;
                    }
                }
            }
        }

        return sum;
    }

    public Item getWeapon(){
        for (Item item : Items){
            if(item.isEquipped && item.ItemMeta.ItemParentClass == ItemUtils.ItemParentClass.Weapon){
                return item;
            }

        }

        return null;
    }

//    public boolean isEquipmentSlotFree(ItemMetaData.ItemPositions pos){
//        return EquippedPositionStatus.get(pos);
//    }
//
//    public void insertItemInEquipmentSlot(Item item){
//        if((item.ItemMeta.ItemPosition != item.ItemMeta.ItemPosition.RingLeft) && (item.ItemMeta.ItemPosition != item.ItemMeta.ItemPosition.RingLeft)){
//            if(EquippedPositionStatus.get(ItemMetaData.ItemPositions.RingLeft)){
//                EquippedPositionStatus.put(item.ItemMeta.ItemPosition.RingLeft, true);
//                item.ItemMeta.ItemPosition = ItemMetaData.ItemPositions.RingLeft;
//            }
//            else{
//                EquippedPositionStatus.put(item.ItemMeta.ItemPosition.RingRight, true);
//                item.ItemMeta.ItemPosition = ItemMetaData.ItemPositions.RingRight;
//            }
//        }
//        else
//        {
//            EquippedPositionStatus.put(item.ItemMeta.ItemPosition, true);
//        }
//    }
//
//    public void removeItemInEquipmentSlot(Item item){
//        EquippedPositionStatus.put(item.ItemMeta.ItemPosition, false);
//    }
//
//    public void createEquipmentPositionStatusDic(){
//        EquippedPositionStatus = new HashMap<>();
//        EquippedPositionStatus.put(ItemMetaData.ItemPositions.Helm, false);
//        EquippedPositionStatus.put(ItemMetaData.ItemPositions.Armor, false);
//        EquippedPositionStatus.put(ItemMetaData.ItemPositions.Shield, false);
//        EquippedPositionStatus.put(ItemMetaData.ItemPositions.Bracers, false);
//        EquippedPositionStatus.put(ItemMetaData.ItemPositions.Gauntlets, false);
//        EquippedPositionStatus.put(ItemMetaData.ItemPositions.Cloak, false);
//        EquippedPositionStatus.put(ItemMetaData.ItemPositions.Weapon, false);
//        EquippedPositionStatus.put(ItemMetaData.ItemPositions.Boots, false);
//        EquippedPositionStatus.put(ItemMetaData.ItemPositions.RingLeft, false);
//        EquippedPositionStatus.put(ItemMetaData.ItemPositions.RingRight, false);
//    }

    public void LevelCheck(){
        for (int i = this.Level; i < CharacterUtils.createXpLevels().size()-1; i++){
            if(this.Xp >= CharacterUtils.getXpForLevel(i)){
                this.Level++;
                this.healCharacter();
            }
            else{
                break;
            }
        }
    }

    @Override
    public UUID getIdentifier(){return this.Identifier;}

    @Override
    public int getInitiative() {
        return this.Initiative;
    }

    @Override
    public boolean isCharacter() {
        return true;
    }

    @Override
    public int getAttackValue() {
        return this.getAttackBonus();
    }

    @Override
    public int getDefenceValue() {
        return this.getDefenceBonus();
    }

    @Override
    public int getCurrentHitpoints() {
        return this.CurrentHitpoints;
    }

    @Override
    public void subtractHitpoints(int hitpoints) {
        this.CurrentHitpoints -= hitpoints;
    }

    @Override
    public boolean isDead(){
        if(CurrentHitpoints <= 0){
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int getDamage(){
        Item weapon = getWeapon();

        int damage = 0;
        int strengthBonus = getTotalAbilityBonus(BaseAbility.Abilities.Strength);

        if(weapon == null){ //unarmed
            damage = DiceUtils.getSingleDiceRoll(1,3) + strengthBonus;
        }
        else{ //armed
            damage = DiceUtils.getSingleDiceRoll(weapon.DamageRange.Min,weapon.DamageRange.Max) + strengthBonus;
        }

        return damage;
    }

    @Override
    public String getName(){
        return Name;
    }
}
