package com.soe.ishibori.stagesofexperience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import utils.CharacterUtils;

public class main_menu_act extends AppCompatActivity {

    Button btnNew;
    Button btnLoad;
    Button btnOptions;
    Button btnCredits;
    Button btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_act);

        CharacterUtils.Init(this);

        btnNew = (Button)findViewById( R.id.btnNew);
        btnLoad = (Button)findViewById( R.id.btnLoad);
        btnOptions = (Button)findViewById( R.id.btnOptions);
        btnCredits = (Button)findViewById( R.id.btnCredits);
        btnQuit = (Button)findViewById( R.id.btnQuit);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_menu_act.this, gen_character_act.class);
                startActivity(intent);
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_menu_act.this, adventure_act.class);
                startActivity(intent);
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(main_menu_act.this, "OPTIONS", Toast.LENGTH_SHORT).show();
            }
        });

        btnCredits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(main_menu_act.this, "CREDITS", Toast.LENGTH_SHORT).show();
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });


    }
}
