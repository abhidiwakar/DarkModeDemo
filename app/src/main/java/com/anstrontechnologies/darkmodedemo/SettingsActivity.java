package com.anstrontechnologies.darkmodedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.anstrontechnologies.darkmodedemo.helpers.AppPreferenceManager;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;

public class SettingsActivity extends AppCompatActivity {

    AppPreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new AppPreferenceManager(this);
        if (preferenceManager.getDarkModeState()){
            setTheme(R.style.AppThemeDark_NoActionBar);
        }else{
            setTheme(R.style.AppTheme_NoActionBar);
        }
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_arrow_back));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        CircularRevealCardView cardView = findViewById(R.id.darkModeCard);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Dark Mode")
                        .setMessage("Enabling/Disabling dark mode requires app UI to restart! Do you want to continue?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (preferenceManager.getDarkModeState()){
                                    darkMode(false);
                                }else{
                                    darkMode(true);
                                }
                            }
                        }).setNegativeButton("No", null)
                        .create().show();
            }
        });
    }

    private void darkMode(boolean b) {
        preferenceManager.setDarkModeState(b);
        Toast.makeText(this, "Changing dark mode!", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}
