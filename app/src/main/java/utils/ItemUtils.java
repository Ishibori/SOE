package utils;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import helpers.ItemMetaData;
import helpers.Range;
import models.BaseAbility;
import models.Character;
import models.Item;
import models.Monster;
import models.SkillBonus;

/**
 * Created by Ishibori on 08/09/2017.
 */

public class ItemUtils {
    public enum ItemParentClass {
        Weapon,
        Armor,
        Shield,
        Helm,
        Cloak,
        Boots,
        Bracers,
        Gauntlets,
        Ring,
        Other
    }

    public enum ItemChildClass {
        ShortSword,
        LongSword,
        Axe,
        Hammer,
        Spear,
        PlateMail,
        ChainMail,
        LeatherMail,
        WallShield,
        Shield,
        Buckler,
        Helm,
        FullHelm,
        Cloak,
        ElvenCloak,
        Boots,
        ElvenBoots,
        MetalBracers,
        LeatherBracers,
        MetalGauntets,
        LeatherGauntlets,
        Ring
    }

    public static int HITPOINTS_FACTOR = 5;

    public static ArrayList<ItemMetaData> ItemsLimits;
    public static ArrayList<Item> Items;
    public static HashMap<ItemChildClass, String> ItemNamesDic;
    public static ArrayList<Item> NormalItems;
    public static ArrayList<Item> LesserArtefactItems;
    public static ArrayList<Item> MajorArtefactItems;

    public static int getMaxNumberOfAllowedItems(ItemParentClass itemParentClass)
    {
        for (ItemMetaData il : ItemsLimits) {
            if(il.ItemParentClass == itemParentClass){
                return il.MaxAllowed;
            }
        }

        return 0;
    }

    public static void initItems(Context context)
    {
        initItemLimitations();
        initItemNames();
        initNormalItems(context);
        initArtefactItems(context);
    }

    public static void initItemLimitations(){
        ItemsLimits = new ArrayList<ItemMetaData>();

        //create data
        ArrayList<ItemChildClass> weaponsData = new ArrayList(Arrays.asList(ItemChildClass.ShortSword, ItemChildClass.LongSword, ItemChildClass.Axe, ItemChildClass.Hammer, ItemChildClass.Spear));
        ArrayList<ItemChildClass> armorData = new ArrayList(Arrays.asList(ItemChildClass.PlateMail, ItemChildClass.ChainMail, ItemChildClass.LeatherMail));
        ArrayList<ItemChildClass> shieldData = new ArrayList(Arrays.asList(ItemChildClass.WallShield, ItemChildClass.Shield, ItemChildClass.Buckler));
        ArrayList<ItemChildClass> helmData = new ArrayList(Arrays.asList(ItemChildClass.FullHelm, ItemChildClass.Helm));
        ArrayList<ItemChildClass> cloakData = new ArrayList(Arrays.asList(ItemChildClass.ElvenCloak, ItemChildClass.Cloak));
        ArrayList<ItemChildClass> bootsData = new ArrayList(Arrays.asList(ItemChildClass.ElvenBoots, ItemChildClass.Boots));
        ArrayList<ItemChildClass> bracersData = new ArrayList(Arrays.asList(ItemChildClass.MetalBracers, ItemChildClass.LeatherBracers));
        ArrayList<ItemChildClass> gauntletsData = new ArrayList(Arrays.asList(ItemChildClass.MetalGauntets, ItemChildClass.LeatherGauntlets));
        ArrayList<ItemChildClass> ringData = new ArrayList(Arrays.asList(ItemChildClass.Ring));

        //add item meta data
        ItemsLimits.add(new ItemMetaData(ItemParentClass.Weapon, 20, weaponsData, 1));
        ItemsLimits.add(new ItemMetaData(ItemParentClass.Armor, 35, armorData, 1));
        ItemsLimits.add(new ItemMetaData(ItemParentClass.Shield, 15, shieldData, 1));
        ItemsLimits.add(new ItemMetaData(ItemParentClass.Helm, 12, helmData, 1));
        ItemsLimits.add(new ItemMetaData(ItemParentClass.Cloak, 10, cloakData, 1));
        ItemsLimits.add(new ItemMetaData(ItemParentClass.Boots, 12, bootsData, 1));
        ItemsLimits.add(new ItemMetaData(ItemParentClass.Bracers, 8, bracersData, 1));
        ItemsLimits.add(new ItemMetaData(ItemParentClass.Gauntlets, 8, gauntletsData, 1));
        ItemsLimits.add(new ItemMetaData(ItemParentClass.Ring, 10, ringData, 2));
        ItemsLimits.add(new ItemMetaData(ItemParentClass.Other, 10, null, 10));
    }

    public static void initItemNames() {
        ItemNamesDic = new HashMap<>();
        ItemNamesDic.put(ItemChildClass.ShortSword, "Short Sword");
        ItemNamesDic.put(ItemChildClass.LongSword, "Long Sword");
        ItemNamesDic.put(ItemChildClass.Axe, "Axe");
        ItemNamesDic.put(ItemChildClass.Hammer, "Hammer");
        ItemNamesDic.put(ItemChildClass.Spear, "Spear");
        ItemNamesDic.put(ItemChildClass.PlateMail, "Plate Mail");
        ItemNamesDic.put(ItemChildClass.ChainMail, "Chain Mail");
        ItemNamesDic.put(ItemChildClass.LeatherMail, "Leather Mail");
        ItemNamesDic.put(ItemChildClass.WallShield, "Wall Shield");
        ItemNamesDic.put(ItemChildClass.Shield, "Shield");
        ItemNamesDic.put(ItemChildClass.Buckler, "Buckler");
        ItemNamesDic.put(ItemChildClass.Helm, "Helm");
        ItemNamesDic.put(ItemChildClass.FullHelm, "Full Helmet");
        ItemNamesDic.put(ItemChildClass.Cloak, "Cloak");
        ItemNamesDic.put(ItemChildClass.ElvenCloak, "Elven Cloak");
        ItemNamesDic.put(ItemChildClass.Boots, "Boots");
        ItemNamesDic.put(ItemChildClass.ElvenBoots, "Elven Boots");
        ItemNamesDic.put(ItemChildClass.MetalBracers, "Metal Bracers");
        ItemNamesDic.put(ItemChildClass.LeatherBracers, "Leather Bracers");
        ItemNamesDic.put(ItemChildClass.MetalGauntets, "Metal Gauntlets");
        ItemNamesDic.put(ItemChildClass.LeatherGauntlets, "Leather Gauntlets");
        ItemNamesDic.put(ItemChildClass.Ring, "Ring");
    }

    public static void initNormalItems(Context context)
    {
        NormalItems = new ArrayList<>();

        //weapons
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.ShortSword, getAbilityMods(0,0,0,0,0,0), getSkillMods(1,0,0), new Range(2,6)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.LongSword, getAbilityMods(0,0,0,0,0,1), getSkillMods(1,1,0), new Range(2,8)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.Axe,  null, getSkillMods(2,-1,0), new Range(3,7)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.Hammer,  getAbilityMods(1,0,0,0,0,0), getSkillMods(1,-1,0), new Range(3,7)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.Spear,  null, getSkillMods(2,0,0), new Range(1,6)));

        //armor
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.PlateMail, getAbilityMods(0,0,-2,0,0,1), getSkillMods(0,7,15)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.ChainMail, getAbilityMods(0,0,-1,0,0,0), getSkillMods(0,5,10)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.LeatherMail, getAbilityMods(0,0,0,0,0,0), getSkillMods(0,3,5)));

        //shields
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.WallShield, getAbilityMods(0,0,-1,0,0,0), getSkillMods(0,4,5)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.Shield, getAbilityMods(0,0,-1,0,0,0), getSkillMods(0,3,0)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.Buckler, getAbilityMods(0,0,0,0,0,0), getSkillMods(0,1,0)));

        //helms
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.Helm, getAbilityMods(0,0,0,0,0,0), getSkillMods(0,1,0)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.FullHelm, getAbilityMods(0,0,0,0,0,1), getSkillMods(0,2,0)));

        //cloaks
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.Cloak, getAbilityMods(0,0,0,0,0,1), null));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.ElvenCloak, getAbilityMods(0,0,0,0,0,1), getSkillMods(0,1,0)));

        //boots
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.Boots, null, getSkillMods(0,1,0)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.ElvenBoots, getAbilityMods(0,0,1,0,0,0), getSkillMods(0,1,0)));

        //bracers
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.MetalBracers, null, getSkillMods(0,2,0)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.LeatherBracers, null, getSkillMods(0,1,0)));

        //gauntlets
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.MetalGauntets, null, getSkillMods(0,2,0)));
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.LeatherGauntlets, null, getSkillMods(0,1,0)));

        //ring
        NormalItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "", "", Item.ItemLevelType.Normal, ItemChildClass.Ring, null, null));
    }


    public static void initArtefactItems(Context context)
    {
        LesserArtefactItems = new ArrayList<>();
        MajorArtefactItems = new ArrayList<>();

        //2-3+ abilities, 2-3+ skills
        LesserArtefactItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "Long Sword of Cyril", "",Item.ItemLevelType.Artifact, ItemChildClass.LongSword, getAbilityMods(1,0,1,0,0,1), getSkillMods(1,2,0), new Range(3,10)));

        //4-5+ abilities, 4-5+ skills
        MajorArtefactItems.add(new Item(getDrawableIdentifier(context,"default_item_icon"), "Elven Cloak of Mir", "",Item.ItemLevelType.Artifact, ItemChildClass.ElvenCloak, getAbilityMods(1,0,2,0,1,1), getSkillMods(2,3,0)));
    }

    public static ItemMetaData getItemMetadata(ItemChildClass itemChildClass)
    {
        for (ItemMetaData imd : ItemsLimits) {
            for (ItemChildClass icc : imd.ItemChildClasses) {
                if(icc == itemChildClass){
                    return imd;
                }
            }
        }

        return null;
    }

    public static ArrayList<Item> getMerchantItems(Context context, Character user_char)
    {
        Items = new ArrayList<>();
        int numOfItems = DiceUtils.getSingleDiceRoll(1,3);

        for(int i = 0; i < numOfItems; i++){
            Items.add(generateRandomItem(context, user_char, 900, 995, 1000));
        }

        return Items;
    }

    public static Item generateRandomItem(Context context, Character user_char, int lowerLimit, int upperLimit, int total)
    {
        Item itemToReturn = null;

        // find item power 90% normal, 9.5% magical, 0.5% artifact
        int itemPower = DiceUtils.getSingleDiceRoll(1,total);

        if(itemPower < lowerLimit){ // normal item
            itemToReturn = getRandomItemFromList(NormalItems);
        }
        else if(itemPower >= lowerLimit && itemPower < upperLimit)// magical item
        {
            itemToReturn = getRandomMagicalItem(user_char);
        }
        else // special power item
        {
            int artefactPower = DiceUtils.getSingleDiceRoll(1,100);

            if(artefactPower > 80){
                itemToReturn = getRandomItemFromList(LesserArtefactItems);
            }
            else {
                itemToReturn = getRandomItemFromList(MajorArtefactItems);
            }

        }

        return itemToReturn;
    }

    public static Item getRandomItemFromList(ArrayList<Item> items)
    {
        int index = DiceUtils.getSingleDiceRoll(0,items.size()-1);
        Item selectedItem = items.get(index).clone();
        selectedItem.Price = selectedItem.generatePrice();

        return  selectedItem;
    }

    public static Item addAllSkillsToItem(Item item, int count){

        for(int i = 0; i < count; i++){
            int skillType = DiceUtils.getSingleDiceRoll(0,2);
            item = addSkillBonusToItem(item, SkillBonus.Skills.values()[skillType], 1);
        }

        return item;
    }

    public static Item addAllAbilitiesToItem(Item item, int count){

        for(int i = 0; i < count; i++){
            int abilityType = DiceUtils.getSingleDiceRoll(0,5);
            item = addAbilityBonusToItem(item, BaseAbility.Abilities.values()[abilityType], 1);
        }

        return item;
    }

    public static Item addAbilityBonusToItem(Item item, BaseAbility.Abilities ability, int bonusToAdd){
        boolean bonusAdded = false;

        if(item.AbilityMods == null)
        {
            item.AbilityMods = new ArrayList<>();
        }

        for (BaseAbility ba : item.AbilityMods) {
            if(ba.AbilityType == ability){
                ba.Bonus += bonusToAdd;
                bonusAdded = true;
            }
        }

        if(!bonusAdded){
            item.AbilityMods.add(new BaseAbility(ability, bonusToAdd));
        }

        return item;
    }

    public static Item addSkillBonusToItem(Item item, SkillBonus.Skills skill, int bonusToAdd){
        boolean bonusAdded = false;

        if(item.SkillMods == null)
        {
            item.SkillMods = new ArrayList<>();
        }

        for (SkillBonus sb : item.SkillMods) {
            if(sb.SkillType == skill){
                if(skill == SkillBonus.Skills.Hitpoints){
                    sb.Bonus += bonusToAdd * HITPOINTS_FACTOR;
                }
                else{
                    sb.Bonus += bonusToAdd;
                }
                bonusAdded = true;
            }
        }

        if(!bonusAdded){
            int factor = skill == SkillBonus.Skills.Hitpoints? HITPOINTS_FACTOR : 1;
            item.SkillMods.add(new SkillBonus(skill, bonusToAdd * factor));
        }

        return item;
    }

    //Todo: possibly change power of item depending on user_char in the future
    public static Item getRandomMagicalItem(Character user_char)
    {
        Item item = getRandomItemFromList(NormalItems);
        item.ItemLevel = Item.ItemLevelType.Magical;

        int abilityMods = DiceUtils.getSingleDiceRoll(1,2); // 1-3 points additional
        int skillMods = DiceUtils.getSingleDiceRoll(1,3); // 1-5 points additional

        item = addAllAbilitiesToItem(item, abilityMods);
        item = addAllSkillsToItem(item, skillMods);

        item.Title = item.getItemTitle();
        item.Description = item.getItemDescription("");
        item.Price = item.generatePrice();

        return item;
    }

    public static int getDrawableIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static SkillBonus.Skills getSkillType(int roll) {
        if(roll == 0){
            return SkillBonus.Skills.Attack;
        }
        else if(roll == 1) {
            return SkillBonus.Skills.Defence;
        }
        else if(roll == 2){
            return SkillBonus.Skills.Hitpoints;
        }

        return SkillBonus.Skills.Attack;
    }

    public static ArrayList<BaseAbility> getAbilityMods(int str, int con, int ag, int ig, int wis, int pre) {
        ArrayList<BaseAbility> abilities = new ArrayList<>();

        if(str != 0){
            abilities.add(new BaseAbility(BaseAbility.Abilities.Strength, str));
        }

        if(con != 0){
            abilities.add(new BaseAbility(BaseAbility.Abilities.Constitution, con));
        }

        if(ag != 0){
            abilities.add(new BaseAbility(BaseAbility.Abilities.Agility, ag));
        }

        if(ig != 0){
            abilities.add(new BaseAbility(BaseAbility.Abilities.Intelligence, ig));
        }

        if(wis != 0){
            abilities.add(new BaseAbility(BaseAbility.Abilities.Wisdom, pre));
        }

        return abilities;
    }

    public static ArrayList<SkillBonus> getSkillMods(int attack, int defence, int hitpoints) {
        ArrayList<SkillBonus> skills = new ArrayList<>();

        if(attack != 0){
            skills.add(new SkillBonus(SkillBonus.Skills.Attack, attack));
        }

        if(defence != 0){
            skills.add(new SkillBonus(SkillBonus.Skills.Defence, defence));
        }

        if(hitpoints != 0){
            skills.add(new SkillBonus(SkillBonus.Skills.Hitpoints, hitpoints));
        }

        return skills;
    }

    public static int getItemTextColor(Item item)
    {
        if(item.ItemLevel == Item.ItemLevelType.Normal){
            return Color.rgb(255,255,255);
        }
        else if(item.ItemLevel == Item.ItemLevelType.Magical){
            return Color.rgb(17,170,255);
        }
        else {
            return Color.rgb(255,187,17);
        }
    }

}
