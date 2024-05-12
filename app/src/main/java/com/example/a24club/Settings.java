package com.example.a24club;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    Button buttonc;
    Button set_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        set_back = findViewById(R.id.setting_goback);
        set_back.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        buttonc = findViewById(R.id.logout_btn);
        buttonc.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        buttonc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear isLoggedIn in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                // Navigate to LoginActivity (or any other login screen)
                Intent intent = new Intent(Settings.this, Login.class);
                startActivity(intent);
                finish(); // Finish the Settings activity
            }
        });
        set_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear isLoggedIn in SharedPreferences
                // Navigate to LoginActivity (or any other login screen)
                Intent intent = new Intent(Settings.this, Home.class);
                startActivity(intent);
                finish(); // Finish the Settings activity
            }
        });
    }
}
