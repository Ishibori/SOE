package com.soe.ishibori.stagesofexperience;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import adapters.InventoryAdapter;
import adapters.InventoryShopAdapter;
import adapters.OnItemClickListener;
import models.CharClass;
import models.Character;
import models.Item;
import models.Race;
import utils.CharacterUtils;
import utils.FileUtils;
import utils.ItemUtils;

public class merchant_guild_act extends AppCompatActivity {

    Button btnBackButton;
    TextView lblMerchantGuild;
    TextView lblGold;

    RecyclerView recyclerViewMerchantInventoryList;
    RecyclerView recyclerViewCharacterInventoryList;

    InventoryShopAdapter merchantAdapter;
    InventoryShopAdapter characterAdapter;

    Character user_char;
    Character merchant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_guild_act);

        lblGold = (TextView)findViewById(R.id.lbl_gold);

        recyclerViewMerchantInventoryList = (RecyclerView) findViewById(R.id.merchant_guild_list);
        recyclerViewMerchantInventoryList.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewCharacterInventoryList = (RecyclerView) findViewById(R.id.character_inventory_list);
        recyclerViewCharacterInventoryList.setLayoutManager(new LinearLayoutManager(this));

        btnBackButton = (Button)findViewById(R.id.btnBack);
        btnBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtils.serializeObject(merchant_guild_act.this, user_char);
                Intent intent = new Intent(merchant_guild_act.this, adventure_act.class);
                startActivity(intent);
            }
        });

        lblMerchantGuild = (TextView)findViewById(R.id.lbl_merchant_guild);
        Typeface tf = Typeface.createFromAsset(getAssets(),getResources().getString(R.string.font_cat_childs));
        lblMerchantGuild.setTypeface(tf,Typeface.BOLD);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        user_char = FileUtils.loadCharacter(this);
        merchant = CharacterUtils.getBasicCharacter("Merchant", Race.Races.Human, CharClass.CharClassType.Warrior);
        merchant.Gold = 100000; // unlimited gold
        merchant.Items = ItemUtils.getMerchantItems(this, user_char);

        merchantAdapter = new InventoryShopAdapter(merchant_guild_act.this, merchant, InventoryShopAdapter.InventoryType.Merchant);
        merchantAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {

            }
        });

        recyclerViewMerchantInventoryList.setAdapter(merchantAdapter);

        characterAdapter = new InventoryShopAdapter(merchant_guild_act.this, user_char, InventoryShopAdapter.InventoryType.Character);
        characterAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {

            }
        });

        recyclerViewCharacterInventoryList.setAdapter(characterAdapter);

        //add crossreferences between adapters
        merchantAdapter.setTargetAdapter(characterAdapter);
        characterAdapter.setTargetAdapter(merchantAdapter);

        updateGold();
    }

    public void updateGold()
    {
        lblGold.setText(Integer.toString(user_char.Gold));
    }
}
