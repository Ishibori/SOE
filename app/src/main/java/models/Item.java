package models;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;

import helpers.ItemMetaData;
import helpers.Range;
import utils.DiceUtils;
import utils.ItemUtils;

import static utils.ItemUtils.HITPOINTS_FACTOR;
import static utils.ItemUtils.NormalItems;

/**
 * Created by Ishibori on 03/09/2017.
 */

public class Item implements Serializable{
    public enum ItemLevelType{
        Normal,
        Magical,
        Artifact
    }

    public int ImageId;
    public String Title;
    public String Description;
    public String ItemName;
    public boolean isEquipped = false;
    public ArrayList<BaseAbility> AbilityMods;
    public ArrayList<SkillBonus> SkillMods;
    public Range DamageRange = null;
    public ItemMetaData ItemMeta;
    public ItemLevelType ItemLevel;
    public ItemUtils.ItemChildClass ItemChildClass; // axe, sword etc.
    public int Price;

    public Item(int imageId, String title, String description, ItemLevelType itemLevel, ItemUtils.ItemChildClass itemClassType, ArrayList<BaseAbility> ability_mods, ArrayList<SkillBonus> skill_mods, Range damage)
    {
        this.ImageId = imageId;
        this.Title = title;
        this.ItemLevel = itemLevel;
        this.ItemChildClass = itemClassType;
        this.AbilityMods = ability_mods;
        this.SkillMods = skill_mods;
        this.DamageRange = damage;
        this.ItemMeta = ItemUtils.getItemMetadata(itemClassType);
        this.Description = getItemDescription(description);
        this.ItemName = getItemName();
        this.Title = getItemTitle();
        this.Price = generatePrice();
    }

    public Item(int imageId, String title, String description, ItemLevelType itemLevel, ItemUtils.ItemChildClass itemClassType, ArrayList<BaseAbility> ability_mods, ArrayList<SkillBonus> skill_mods)
    {
        this.ImageId = imageId;
        this.Title = title;
        this.ItemLevel = itemLevel;
        this.ItemChildClass = itemClassType;
        this.AbilityMods = ability_mods;
        this.SkillMods = skill_mods;
        this.ItemMeta = ItemUtils.getItemMetadata(itemClassType);
        this.Description = getItemDescription(description);
        this.ItemName = getItemName();
        this.Title = getItemTitle();
        this.Price = generatePrice();
    }

    public int generatePrice()
    {
        int abilityMods = getAbilityModsCount();
        int skillMods = getSkillModsCount();

        int price = ItemMeta.BasePrice + abilityMods * 40 + skillMods * 15 + DiceUtils.getSingleDiceRoll(0, 20);

        if(price < 0)
            price = 1;

        return  price;
    }

    public int getAbilityModsCount()
    {
        int count = 0;

        if(AbilityMods != null) {
            for (BaseAbility ba : AbilityMods) {
                count += ba.Bonus;
            }
        }

        return count;
    }

    public int getSkillModsCount()
    {
        int count = 0;

        if(SkillMods != null) {
            for (SkillBonus sb : SkillMods) {
                if(sb.SkillType == SkillBonus.Skills.Hitpoints) {
                    count += sb.Bonus / HITPOINTS_FACTOR;
                }
                else
                {
                    count += sb.Bonus;
                }
            }
        }

        return count;
    }

    public BaseAbility getPrimeAbility()
    {
        BaseAbility maxAbility = null;
        int maxBonus = 0;

        if(AbilityMods != null) {
            for (BaseAbility ab : AbilityMods) {
                if (ab.Bonus >= maxBonus) {
                    maxBonus = ab.Bonus;
                    maxAbility = ab;
                }
            }
        }
        else
        {
            return null;
        }

        return maxAbility;
    }

    public SkillBonus getPrimeSkill()
    {
        SkillBonus maxSkill = null;
        int maxBonus = 0;

        if(SkillMods != null) {
            for (SkillBonus sb : SkillMods) {
                if (sb.Bonus >= maxBonus) {
                    maxBonus = sb.Bonus;
                    maxSkill = sb;
                }
            }
        }
        else
        {
            return null;
        }

        return maxSkill;
    }

    public String getItemTitle()
    {
        return this.ItemName;
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(this.ItemName);
//
//        if(this.ItemLevel == ItemLevelType.Normal){
//            sb.append(" (normal)");
//        }
//        else if(this.ItemLevel == ItemLevelType.Magical){
//            sb.append(" (magical)");
//        }
//        else if(this.ItemLevel == ItemLevelType.Artifact)
//        {
//            return this.Title + " (artefact)";
//        }
//
//        return  sb.toString();
    }

    public String getItemName()
    {
        return ItemUtils.ItemNamesDic.get(ItemChildClass);
    }

    public String getItemDescription(String initialDesc)
    {
        StringBuilder description = new StringBuilder();

        if(this.ItemLevel == ItemLevelType.Normal){
            description.append("This is a normal item.\n");
        }
        else if(this.ItemLevel == ItemLevelType.Magical){
            description.append("This is a magical item.\n");
        }
        else if(this.ItemLevel == ItemLevelType.Artifact)
        {
            description.append("This is an artifact.\n");
        }

        if (initialDesc != null && initialDesc.length() > 0) {
            description.append(initialDesc + "\n");
        }

        if (this.DamageRange != null) {
            description.append("Base Damage: " + this.DamageRange.Min + "-" + this.DamageRange.Max + "\n");
        }

        if (AbilityMods != null) {
            for (BaseAbility ba : AbilityMods) {
                if (ba.Bonus != 0) {
                    String operator = ba.Bonus > 0 ? "+" : "";
                    description.append(ba.AbilityType.toString() + " " + operator + ba.Bonus + "\n");
                }
            }
        }

        if (SkillMods != null) {
            for (SkillBonus sb : SkillMods) {
                if (sb.Bonus != 0) {
                    String operator = sb.Bonus > 0 ? "+" : "";
                    description.append(sb.SkillType.toString() + " " + operator + sb.Bonus + "\n");
                }
            }
        }

        return description.toString();
    }

    //Utility
    public Item clone()
    {
        Range range = null;
        if(DamageRange != null){
            range = new Range(this.DamageRange.Min, this.DamageRange.Max);
        }

        Item item = new Item(this.ImageId, this.Title, this.Description,this.ItemLevel, this.ItemChildClass,null,null,range);
        item.Title = this.Title;
        item.ItemName = this.ItemName;
        item.isEquipped = this.isEquipped;
        item.Price = this.Price;
        item.ItemMeta = this.ItemMeta.clone();
        item.AbilityMods = new ArrayList<>();
        item.SkillMods = new ArrayList<>();

        if(this.AbilityMods != null) {
            for (BaseAbility ba : this.AbilityMods) {
                item.AbilityMods.add(ba.clone());
            }
        }

        if(this.SkillMods != null) {
            for (SkillBonus sb : this.SkillMods) {
                item.SkillMods.add(sb.clone());
            }
        }

        if(item.ItemLevel != item.ItemLevel.Artifact) {
            item.Description = item.getItemDescription("");
        }
        else
        {
            item.Description = item.getItemDescription(item.Description);
        }

        return item;
    }

}
