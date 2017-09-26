package com.soe.ishibori.stagesofexperience;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import adventure_fragments.OnFragmentInteractionListener;
import adventure_fragments.adventure_stage_frag;
import adventure_fragments.campaign_selection_frag;
import models.Adventure;
import models.Campaign;
import models.StageOption;
import utils.AdventureUtils;
import utils.CharacterUtils;
import utils.FileUtils;
import models.Character;

public class adventure_act extends FragmentActivity implements OnFragmentInteractionListener {

    TextView txtExploreAdv;
    TextView txtMainMenu;
    TextView txtCharSheet;
    TextView txtMerchantGuild;

    ImageView imgMainMenu;
    ImageView imgCharSheet;
    ImageView imgMerchantGuild;

    Character user_char;

    public Campaign currentCampaign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_act);

        txtMainMenu = (TextView) findViewById( R.id.lbl_main_menu);
        txtCharSheet = (TextView) findViewById( R.id.lbl_character_sheet);
        txtMerchantGuild = (TextView) findViewById( R.id.lbl_shop);

        imgMainMenu = (ImageView) findViewById( R.id.imgMainMenu);
        imgCharSheet = (ImageView) findViewById( R.id.imgCharSheet);
        imgMerchantGuild = (ImageView) findViewById( R.id.imgMerchantGuild);

        txtExploreAdv = (TextView) findViewById( R.id.lbl_explore_adventures);
        Typeface tf = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_cat_childs));
        txtExploreAdv.setTypeface(tf,Typeface.BOLD);

        txtMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainMenu();
            }
        });

        imgMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainMenu();
            }
        });

        txtCharSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCharSheet();
            }
        });

        imgCharSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCharSheet();
            }
        });

        txtMerchantGuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMerchantGuild();
            }
        });

        imgMerchantGuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMerchantGuild();
            }
        });

    }

    public void setNewFragment(){
        currentCampaign = AdventureUtils.getActiveCampaign();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        if(currentCampaign == null){
            fragment = new campaign_selection_frag();
        }
        else {
            fragment=currentCampaign.getNextStageFragment();
        }

        fragmentTransaction.replace(R.id.adventure_action_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        user_char = FileUtils.loadCharacter(adventure_act.this);
        AdventureUtils.initCampaigns(this,user_char);
        setNewFragment();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        FileUtils.serializeObject(adventure_act.this, user_char);
    }

    private void startMainMenu()
    {
        Intent intent = new Intent(adventure_act.this, main_menu_act.class);
        startActivity(intent);
    }

    private void startCharSheet()
    {
        //Toast.makeText(adventure_act.this, "Character Sheet", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(adventure_act.this, character_sheet_act.class);
        startActivity(intent);
    }

    private void startMerchantGuild()
    {
        Intent intent = new Intent(adventure_act.this, merchant_guild_act.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction() {

    }
}
