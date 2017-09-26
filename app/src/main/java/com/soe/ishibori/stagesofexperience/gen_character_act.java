package com.soe.ishibori.stagesofexperience;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import utils.AbilityUtils;
import utils.ClassUtils;
import utils.CharacterUtils;
import utils.DiceUtils;
import utils.FileUtils;
import utils.ItemUtils;
import utils.RaceUtils;
import models.BaseAbility;
import models.CharClass;
import models.Character;
import models.Race;

import static java.security.AccessController.getContext;

public class gen_character_act extends AppCompatActivity {

    TextView lblCharacterCreation;
    TextView edtName;
    Spinner spnRaces;
    Spinner spnClasses;
    TextView lblGold;
    TextView lblAttack;
    TextView lblDefence;
    TextView lblHitpoints;
    GridLayout gridLayout;
    Button btnReroll;
    TextView txtCharDesc;
    Button btnAcceptChar;

    ArrayList<Integer> diceRolls = new ArrayList<>();
    Character user_char;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_character);

        edtName = (TextView) findViewById( R.id.edt_name);
        lblAttack = (TextView) findViewById( R.id.lbl_skill_attack);
        lblDefence = (TextView) findViewById( R.id.lbl_skill_defence);
        lblHitpoints = (TextView) findViewById( R.id.lbl_skill_hitpoints);
        lblGold = (TextView) findViewById( R.id.lbl_gold);

        spnRaces = (Spinner)findViewById( R.id.race_spinner);
        spnClasses = (Spinner)findViewById( R.id.class_spinner);

        gridLayout = (GridLayout)findViewById( R.id.gridStats);
        btnReroll = (Button) findViewById( R.id.btnRerollStats);
        btnAcceptChar = (Button) findViewById( R.id.btnAcceptCharacter);

        txtCharDesc = (TextView) findViewById( R.id.txt_description);

        edtName.setText("Thalos");
        user_char = CharacterUtils.getBasicCharacter(edtName.getText().toString(), Race.Races.Human, CharClass.CharClassType.Warrior);

        //TEST
        //user_char.Items = ItemUtils.getTestItems(gen_character_act.this);
        //End Test

        edtName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    user_char.Name = edtName.getText().toString();
                    updateDescription();
                    return true;
                }
                return false;
            }
        });

        btnReroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
                updateRolls();
                updateTotalBonuses();
                updateSkills();
                updateDescription();
            }
        });

        btnAcceptChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_char.healCharacter();
                FileUtils.serializeObject(gen_character_act.this, user_char);
                Intent intent = new Intent(gen_character_act.this, adventure_act.class);
                startActivity(intent);
            }
        });

        lblCharacterCreation = (TextView)findViewById(R.id.lbl_character_creation);
        Typeface tf = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_cat_childs));
        lblCharacterCreation.setTypeface(tf,Typeface.BOLD);

        //setup the grid
        createGrid();

        //add races and classes
        loadRacesAndClasses(spnRaces, spnClasses);

        //set rolls
        rollDice();
        updateRolls();

        //update character bonuses
        updateAllBonuses();

        //update character description
        updateDescription();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onPause()
    {
        super.onPause();
        FileUtils.serializeObject(gen_character_act.this, user_char);
    }

    public void rollDice()
    {
        diceRolls = new ArrayList<>();

        for(int i = 0; i < 6; i++)
        {
            int val = DiceUtils.getSingleDiceRoll(3,18);
            diceRolls.add(val);
            user_char.Abilities.get(i).Value = val;
        }

        user_char.BackgroundStatus = DiceUtils.getSingleDiceRoll(0,100); //Rodo move function to char
        //user_char.Gold = CharacterUtils.getMoneyFromEconomicStatus(user_char.BackgroundStatus);

        user_char.setAbilities(diceRolls);
        user_char.healCharacter();
        user_char.RollForGold();
        user_char.Gold = 10000; //Todo:remove
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

                if(column == 5) { // set last column to white
                    tv.setTextColor(getResources().getColor(android.R.color.white));
                }

                gridLayout.addView(tv, params);
            }
        }
    }

    public void updateAllBonuses()
    {
        updateRaceBonuses();
        updateClassBonuses();
        updateTotalBonuses();
        updateSkills();
    }

    public void updateSkills()
    {
        lblAttack.setText(Integer.toString(user_char.getAttackBonus()));
        lblDefence.setText(Integer.toString(user_char.getDefenceBonus()));
        lblHitpoints.setText(Integer.toString(user_char.getMaxHitpoints()));
    }

    public void updateDescription()
    {
        user_char.UpdateDescription();
        txtCharDesc.setText(user_char.Description);
        lblGold.setText(Integer.toString(user_char.Gold));
    }

    public void loadRacesAndClasses(final Spinner spnRaces, final Spinner spnClasses)
    {
        List<String> items = new ArrayList<String>();

        for (Race r: RaceUtils.getRaces()) {
            items.add(r.Race.toString());
        }

        addItemsToSpinner(spnRaces, items);
        spnRaces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_char.CharacterRace = RaceUtils.getSpecificRace(Race.Races.valueOf(spnRaces.getSelectedItem().toString()));
                gen_character_act.this.updateRaceBonuses();
                gen_character_act.this.updateTotalBonuses();
                gen_character_act.this.updateSkills();
                updateDescription();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        items = new ArrayList<String>();

        for (CharClass cc: ClassUtils.getClasses()) {
            items.add(cc.ClassType.toString());
        }

        addItemsToSpinner(spnClasses, items);

        spnClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user_char.CharacterClass = ClassUtils.getSpecificClass(CharClass.CharClassType.valueOf(spnClasses.getSelectedItem().toString())); //values()[spnClasses.getSelectedItemPosition()]);
                gen_character_act.this.updateClassBonuses();
                gen_character_act.this.updateTotalBonuses();
                gen_character_act.this.updateSkills();
                updateDescription();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void updateTotalBonuses()
    {
        int totalStrBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Strength);
        int totalConBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Constitution);
        int totalAgBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Agility);
        int totalIntBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Intelligence);
        int totalWisBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Wisdom);
        int totalPreBonus = user_char.getTotalAbilityBonus(BaseAbility.Abilities.Presence);

        TextView viewTotalBonusStr = (TextView)gridLayout.getChildAt(36);
        TextView viewTotalBonusCon = (TextView)gridLayout.getChildAt(37);
        TextView viewTotalBonusAg = (TextView)gridLayout.getChildAt(38);
        TextView viewTotalBonusInt = (TextView)gridLayout.getChildAt(39);
        TextView viewTotalBonusWis = (TextView)gridLayout.getChildAt(40);
        TextView viewTotalBonusPre = (TextView)gridLayout.getChildAt(41);

        viewTotalBonusStr.setText(Integer.toString(totalStrBonus));
        viewTotalBonusCon.setText(Integer.toString(totalConBonus));
        viewTotalBonusAg.setText(Integer.toString(totalAgBonus));
        viewTotalBonusInt.setText(Integer.toString(totalIntBonus));
        viewTotalBonusWis.setText(Integer.toString(totalWisBonus));
        viewTotalBonusPre.setText(Integer.toString(totalPreBonus));
    }

    public void updateRaceBonuses()
    {
        TextView viewRaceStr = (TextView)gridLayout.getChildAt(24);
        TextView viewRaceCon = (TextView)gridLayout.getChildAt(25);
        TextView viewRaceAg = (TextView)gridLayout.getChildAt(26);
        TextView viewRaceInt = (TextView)gridLayout.getChildAt(27);
        TextView viewRaceWis = (TextView)gridLayout.getChildAt(28);
        TextView viewRacePre = (TextView)gridLayout.getChildAt(29);

        viewRaceStr.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Strength)));
        viewRaceCon.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Constitution)));
        viewRaceAg.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Agility)));
        viewRaceInt.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Intelligence)));
        viewRaceWis.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Wisdom)));
        viewRacePre.setText(Integer.toString(user_char.CharacterRace.getRaceBonus(BaseAbility.Abilities.Presence)));
    }

    public void updateClassBonuses()
    {
        TextView viewClassStr = (TextView)gridLayout.getChildAt(30);
        TextView viewClassCon = (TextView)gridLayout.getChildAt(31);
        TextView viewClassAg = (TextView)gridLayout.getChildAt(32);
        TextView viewClassInt = (TextView)gridLayout.getChildAt(33);
        TextView viewClassWis = (TextView)gridLayout.getChildAt(34);
        TextView viewClassPre = (TextView)gridLayout.getChildAt(35);

        viewClassStr.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Strength)));
        viewClassCon.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Constitution)));
        viewClassAg.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Agility)));
        viewClassInt.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Intelligence)));
        viewClassWis.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Wisdom)));
        viewClassPre.setText(Integer.toString(user_char.CharacterClass.getClassBonus(BaseAbility.Abilities.Presence)));
    }

    public void updateRolls()
    {
        int rollPos = 12;
        TextView viewBaseStr = (TextView)gridLayout.getChildAt(rollPos);
        TextView viewBaseCon = (TextView)gridLayout.getChildAt(rollPos+1);
        TextView viewBaseAg = (TextView)gridLayout.getChildAt(rollPos+2);
        TextView viewBaseInt = (TextView)gridLayout.getChildAt(rollPos+3);
        TextView viewBaseWis = (TextView)gridLayout.getChildAt(rollPos+4);
        TextView viewBasePre = (TextView)gridLayout.getChildAt(rollPos+5);

        viewBaseStr.setText(diceRolls.get(0).toString());
        viewBaseCon.setText(diceRolls.get(1).toString());
        viewBaseAg.setText(diceRolls.get(2).toString());
        viewBaseInt.setText(diceRolls.get(3).toString());
        viewBaseWis.setText(diceRolls.get(4).toString());
        viewBasePre.setText(diceRolls.get(5).toString());

        int rollBonusPos = 18;
        TextView viewBonusStr = (TextView)gridLayout.getChildAt(rollBonusPos);
        TextView viewBonusCon = (TextView)gridLayout.getChildAt(rollBonusPos+1);
        TextView viewBonusAg = (TextView)gridLayout.getChildAt(rollBonusPos+2);
        TextView viewBonusInt = (TextView)gridLayout.getChildAt(rollBonusPos+3);
        TextView viewBonusWis = (TextView)gridLayout.getChildAt(rollBonusPos+4);
        TextView viewBonusPre = (TextView)gridLayout.getChildAt(rollBonusPos+5);

        viewBonusStr.setText(Integer.toString(AbilityUtils.getAbilityBonus(diceRolls.get(0))));
        viewBonusCon.setText(Integer.toString(AbilityUtils.getAbilityBonus(diceRolls.get(1))));
        viewBonusAg.setText(Integer.toString(AbilityUtils.getAbilityBonus(diceRolls.get(2))));
        viewBonusInt.setText(Integer.toString(AbilityUtils.getAbilityBonus(diceRolls.get(3))));
        viewBonusWis.setText(Integer.toString(AbilityUtils.getAbilityBonus(diceRolls.get(4))));
        viewBonusPre.setText(Integer.toString(AbilityUtils.getAbilityBonus(diceRolls.get(5))));
    }

    public void addItemsToSpinner(Spinner spn, List<String> items) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(dataAdapter);
    }
}
