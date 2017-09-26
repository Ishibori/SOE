package com.soe.ishibori.stagesofexperience;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import adapters.InventoryAdapter;
import adapters.OnItemClickListener;
import helpers.EquipmentSlot;
import helpers.ItemMetaData;
import utils.AbilityUtils;
import utils.CharacterUtils;
import utils.DescriptionUtils;
import utils.FileUtils;
import models.BaseAbility;
import models.Character;
import models.Item;
import utils.ItemUtils;

public class character_sheet_act extends Activity {
    public static int MAX_NUM_ITEMS = 10;

    TextView lblCharacterSheet;
    TextView lblCharacterName;
    TextView lblCharacterStatus;
    TextView lblXpBar;
    TextView lblHitpointsBar;
    TextView lblAttack;
    TextView lblDefence;
    TextView lblHitpoints;
    TextView lblGold;

    ProgressBar xp_bar;
    ProgressBar hitpoints_bar;

    RelativeLayout layoutCharItems;
    GridLayout gridLayout;
    RecyclerView recyclerViewInventoryList;
    InventoryAdapter adapter;
    Button btnBackButton;

    Character user_char;
    ArrayList<EquipmentSlot> equipmentSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheet_act);

        user_char = FileUtils.loadCharacter(this);

        lblGold = (TextView) findViewById(R.id.lbl_gold);
        lblCharacterName = (TextView) findViewById(R.id.lbl_character_name);
        lblCharacterStatus = (TextView) findViewById(R.id.lbl_character_status);

        lblCharacterName.setText(user_char.Name);
        lblCharacterStatus.setText(DescriptionUtils.getStatusString(user_char));

        lblAttack = (TextView) findViewById(R.id.lbl_skill_attack);
        lblDefence = (TextView) findViewById(R.id.lbl_skill_defence);
        lblHitpoints = (TextView) findViewById(R.id.lbl_skill_hitpoints);

        lblXpBar = (TextView) findViewById(R.id.lbl_xp_progress);;
        lblHitpointsBar = (TextView) findViewById(R.id.lbl_hitpoints_status);

        xp_bar =(ProgressBar)findViewById(R.id.pbar_xp);
        hitpoints_bar =(ProgressBar)findViewById(R.id.pbar_hitpoints);

        lblXpBar.setText(Integer.toString(user_char.Xp) + "/" + Integer.toString(CharacterUtils.Levels.get(user_char.Level)));

        layoutCharItems = (RelativeLayout)findViewById(R.id.layoutCharItems);
        gridLayout = (GridLayout)findViewById(R.id.gridStats);
        recyclerViewInventoryList = (RecyclerView) findViewById(R.id.itemsList);
        recyclerViewInventoryList.setLayoutManager(new LinearLayoutManager(this));

        adapter = new InventoryAdapter(character_sheet_act.this, user_char);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                setLevelsAndHitpoints();
                updateItemBonuses();
                updateTotalBonuses();
                updateSkills();

                if(item.isEquipped) {
                    Toast.makeText(character_sheet_act.this, item.Title + " equipped!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(character_sheet_act.this, item.Title + " unequipped!", Toast.LENGTH_SHORT).show();
                }

                setEquipmentSlotsStatus();
            }
        });

        recyclerViewInventoryList.setAdapter(adapter);

        btnBackButton = (Button)findViewById(R.id.btnBack);
        btnBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtils.serializeObject(character_sheet_act.this, user_char);
                Intent intent = new Intent(character_sheet_act.this, adventure_act.class);
                startActivity(intent);
            }
        });

        createGrid();

        lblCharacterSheet = (TextView)findViewById(R.id.lbl_character_sheet);
        Typeface tf = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_cat_childs));
        lblCharacterSheet.setTypeface(tf,Typeface.BOLD);

        createEquipmentSlots();
    }

    public void createEquipmentSlots(){
        equipmentSlots = new ArrayList<>();

        for (EquipmentSlot.ItemPositions pos : EquipmentSlot.ItemPositions.values()) {
            EquipmentSlot equipment = new EquipmentSlot(this, pos, ItemUtils.getDrawableIdentifier(this,"shield_icon"));
            equipment.addToLayout(layoutCharItems);
            equipmentSlots.add(equipment);
        }

        setEquipmentSlotsStatus();
    }

    public EquipmentSlot.ItemPositions getEquipmentSlotPosition(Item item){
        EquipmentSlot.ItemPositions returnType = EquipmentSlot.ItemPositions.Weapon;

        switch (item.ItemMeta.ItemParentClass){
            case Weapon:{
                returnType = EquipmentSlot.ItemPositions.Weapon;
                break;
            }
            case Armor:{
                returnType = EquipmentSlot.ItemPositions.Armor;
                break;
            }
            case Shield:{
                returnType = EquipmentSlot.ItemPositions.Shield;
                break;
            }
            case Helm:{
                returnType = EquipmentSlot.ItemPositions.Helm;
                break;
            }
            case Cloak:{
                returnType = EquipmentSlot.ItemPositions.Cloak;
                break;
            }
            case Boots:{
                returnType = EquipmentSlot.ItemPositions.Boots;
                break;
            }
            case Bracers:{
                returnType = EquipmentSlot.ItemPositions.Bracers;
                break;
            }
            case Gauntlets:{
                returnType = EquipmentSlot.ItemPositions.Gauntlets;
                break;
            }
            case Ring:{
                //TODO: special case - check for empty slots
                returnType = EquipmentSlot.ItemPositions.RingLeft;
                //returnType = EquipmentSlot.ItemPositions.RingRight;
                break;
            }
        }

        return returnType;
    }

    public EquipmentSlot getEquipmentSlot(EquipmentSlot.ItemPositions itemPos){
        if (equipmentSlots != null) {
            for (EquipmentSlot es : equipmentSlots){
                if(es.PosType == itemPos){
                    return es;
                }
            }
        }

        return  null;
    }

    public void setEquipmentSlotsStatus(){
        //reset equipment slots
        for (EquipmentSlot es : equipmentSlots) {
            es.setSlotIdle();
        }

        //set active slots
        for (Item item : user_char.Items) {
            if(item.isEquipped){
                EquipmentSlot.ItemPositions itemPos = getEquipmentSlotPosition(item);
                EquipmentSlot slot = getEquipmentSlot(itemPos);
                slot.setSlotActive();
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        user_char = FileUtils.loadCharacter(this);
        adapter.updateCharacterInto(user_char);

        //make a level check
        user_char.LevelCheck();

        updateAll();

        //setEquipmentStatus();
    }

    public void updateAll(){
        setLevelsAndHitpoints();
        updateSkills();
        updateRolls();
        updateRaceBonuses();
        updateClassBonuses();
        updateItemBonuses();
        updateTotalBonuses();
        updateGold();
    }

    public void updateGold()
    {
        lblGold.setText(Integer.toString(user_char.Gold));
    }

    @Override
    public void onPause()
    {
        super.onPause();
        FileUtils.serializeObject(character_sheet_act.this, user_char);
    }

    public void setLevelsAndHitpoints()
    {
        int nextLevelXp = CharacterUtils.getXpForLevel(user_char.Level);
        xp_bar.setMax(nextLevelXp);
        xp_bar.setProgress(user_char.Xp);
        lblXpBar.setText(Integer.toString(user_char.Xp) + "/" + Integer.toString(nextLevelXp));

        //set hitpoints
        int maxHitpoints = user_char.getMaxHitpoints();
        user_char.CurrentHitpoints = user_char.CurrentHitpoints > maxHitpoints? maxHitpoints : user_char.CurrentHitpoints;

        hitpoints_bar.setMax(maxHitpoints);
        hitpoints_bar.setProgress(user_char.CurrentHitpoints);
        lblHitpointsBar.setText(Integer.toString(user_char.CurrentHitpoints) + "/" + Integer.toString(maxHitpoints));
    }

    public void createGrid()
    {
        for(int column = 1; column < gridLayout.getColumnCount(); column++) {
            for (int row = 1; row < gridLayout.getRowCount(); row++) {
                TextView tv = new TextView(this);
                tv.setText("0");
                GridLayout.Spec columnSpec = GridLayout.spec(column);
                GridLayout.Spec rowSpec = GridLayout.spec(row);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                params.setGravity(1);

                if(column == 6) { // set last column to white
                    tv.setTextColor(getResources().getColor(android.R.color.white));
                }

                gridLayout.addView(tv, params);
            }
        }
    }

    public void updateSkills()
    {
        lblAttack.setText(Integer.toString(user_char.getAttackBonus()));
        lblDefence.setText(Integer.toString(user_char.getDefenceBonus()));
        lblHitpoints.setText(Integer.toString(user_char.getMaxHitpoints()));
    }

    public void updateTotalBonuses()
    {
        int totalStrBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Strength);
        int totalConBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Constitution);
        int totalAgBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Agility);
        int totalIntBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Intelligence);
        int totalWisBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Wisdom);
        int totalPreBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Presence);

        int totalPos = 43;
        TextView viewTotalBonusStr = (TextView)gridLayout.getChildAt(totalPos);
        TextView viewTotalBonusCon = (TextView)gridLayout.getChildAt(totalPos+1);
        TextView viewTotalBonusAg = (TextView)gridLayout.getChildAt(totalPos+2);
        TextView viewTotalBonusInt = (TextView)gridLayout.getChildAt(totalPos+3);
        TextView viewTotalBonusWis = (TextView)gridLayout.getChildAt(totalPos+4);
        TextView viewTotalBonusPre = (TextView)gridLayout.getChildAt(totalPos+5);

        viewTotalBonusStr.setText(Integer.toString(totalStrBonus));
        viewTotalBonusCon.setText(Integer.toString(totalConBonus));
        viewTotalBonusAg.setText(Integer.toString(totalAgBonus));
        viewTotalBonusInt.setText(Integer.toString(totalIntBonus));
        viewTotalBonusWis.setText(Integer.toString(totalWisBonus));
        viewTotalBonusPre.setText(Integer.toString(totalPreBonus));
    }

    public void updateItemBonuses()
    {
        int itemPos = 37;
        TextView viewItemStr = (TextView)gridLayout.getChildAt(itemPos);
        TextView viewItemCon = (TextView)gridLayout.getChildAt(itemPos+1);
        TextView viewItemAg = (TextView)gridLayout.getChildAt(itemPos+2);
        TextView viewItemInt = (TextView)gridLayout.getChildAt(itemPos+3);
        TextView viewItemWis = (TextView)gridLayout.getChildAt(itemPos+4);
        TextView viewItemPre = (TextView)gridLayout.getChildAt(itemPos+5);

        viewItemStr.setText(Integer.toString(user_char.getItemBonusesForAbility(BaseAbility.Abilities.Strength)));
        viewItemCon.setText(Integer.toString(user_char.getItemBonusesForAbility(BaseAbility.Abilities.Constitution)));
        viewItemAg.setText(Integer.toString(user_char.getItemBonusesForAbility(BaseAbility.Abilities.Agility)));
        viewItemInt.setText(Integer.toString(user_char.getItemBonusesForAbility(BaseAbility.Abilities.Intelligence)));
        viewItemWis.setText(Integer.toString(user_char.getItemBonusesForAbility(BaseAbility.Abilities.Wisdom)));
        viewItemPre.setText(Integer.toString(user_char.getItemBonusesForAbility(BaseAbility.Abilities.Presence)));
    }

    public void updateRaceBonuses()
    {
        int racePos = 25;
        TextView viewRaceStr = (TextView)gridLayout.getChildAt(racePos);
        TextView viewRaceCon = (TextView)gridLayout.getChildAt(racePos+1);
        TextView viewRaceAg = (TextView)gridLayout.getChildAt(racePos+2);
        TextView viewRaceInt = (TextView)gridLayout.getChildAt(racePos+3);
        TextView viewRaceWis = (TextView)gridLayout.getChildAt(racePos+4);
        TextView viewRacePre = (TextView)gridLayout.getChildAt(racePos+5);

        viewRaceStr.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Strength)));
        viewRaceCon.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Constitution)));
        viewRaceAg.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Agility)));
        viewRaceInt.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Intelligence)));
        viewRaceWis.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Wisdom)));
        viewRacePre.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Presence)));
    }

    public void updateClassBonuses()
    {
        int classPos = 31;
        TextView viewClassStr = (TextView)gridLayout.getChildAt(classPos);
        TextView viewClassCon = (TextView)gridLayout.getChildAt(classPos+1);
        TextView viewClassAg = (TextView)gridLayout.getChildAt(classPos+2);
        TextView viewClassInt = (TextView)gridLayout.getChildAt(classPos+3);
        TextView viewClassWis = (TextView)gridLayout.getChildAt(classPos+4);
        TextView viewClassPre = (TextView)gridLayout.getChildAt(classPos+5);

        viewClassStr.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Strength)));
        viewClassCon.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Constitution)));
        viewClassAg.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Agility)));
        viewClassInt.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Intelligence)));
        viewClassWis.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Wisdom)));
        viewClassPre.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Presence)));
    }

    public void updateRolls()
    {
        int rollPos = 13;
        TextView viewBaseStr = (TextView)gridLayout.getChildAt(rollPos);
        TextView viewBaseCon = (TextView)gridLayout.getChildAt(rollPos+1);
        TextView viewBaseAg = (TextView)gridLayout.getChildAt(rollPos+2);
        TextView viewBaseInt = (TextView)gridLayout.getChildAt(rollPos+3);
        TextView viewBaseWis = (TextView)gridLayout.getChildAt(rollPos+4);
        TextView viewBasePre = (TextView)gridLayout.getChildAt(rollPos+5);

        viewBaseStr.setText(Integer.toString(user_char.Abilities.get(0).Value));
        viewBaseCon.setText(Integer.toString(user_char.Abilities.get(1).Value));
        viewBaseAg.setText(Integer.toString(user_char.Abilities.get(2).Value));
        viewBaseInt.setText(Integer.toString(user_char.Abilities.get(3).Value));
        viewBaseWis.setText(Integer.toString(user_char.Abilities.get(4).Value));
        viewBasePre.setText(Integer.toString(user_char.Abilities.get(5).Value));

        int rollBonusPos = 19;
        TextView viewBonusStr = (TextView)gridLayout.getChildAt(rollBonusPos);
        TextView viewBonusCon = (TextView)gridLayout.getChildAt(rollBonusPos+1);
        TextView viewBonusAg = (TextView)gridLayout.getChildAt(rollBonusPos+2);
        TextView viewBonusInt = (TextView)gridLayout.getChildAt(rollBonusPos+3);
        TextView viewBonusWis = (TextView)gridLayout.getChildAt(rollBonusPos+4);
        TextView viewBonusPre = (TextView)gridLayout.getChildAt(rollBonusPos+5);

        viewBonusStr.setText(Integer.toString(AbilityUtils.getAbilityBonus(user_char.Abilities.get(0).Value)));
        viewBonusCon.setText(Integer.toString(AbilityUtils.getAbilityBonus(user_char.Abilities.get(1).Value)));
        viewBonusAg.setText(Integer.toString(AbilityUtils.getAbilityBonus(user_char.Abilities.get(2).Value)));
        viewBonusInt.setText(Integer.toString(AbilityUtils.getAbilityBonus(user_char.Abilities.get(3).Value)));
        viewBonusWis.setText(Integer.toString(AbilityUtils.getAbilityBonus(user_char.Abilities.get(4).Value)));
        viewBonusPre.setText(Integer.toString(AbilityUtils.getAbilityBonus(user_char.Abilities.get(5).Value)));
    }

}
