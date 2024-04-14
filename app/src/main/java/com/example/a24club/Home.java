package com.example.a24club;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private Button quizButton;
    private Button articlesButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize quizButton
        quizButton = findViewById(R.id.quiz_btn);
        quizButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        articlesButton = findViewById(R.id.articles_btn);
        articlesButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        settingsButton = findViewById(R.id.settings_btn);
        settingsButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        // Set OnClickListener on quizButton
        quizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start MainActivity
                Intent intent = new Intent(Home.this, Categories.class);

                // Start MainActivity
                startActivity(intent);
            }
        });
        Button articlesButton = findViewById(R.id.articles_btn);

        articlesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ArticlesActivity when the button is clicked
                Intent intent = new Intent(Home.this, Articles.class);
                startActivity(intent);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ArticlesActivity when the button is clicked
                Intent intent = new Intent(Home.this,Settings.class);
                startActivity(intent);
            }
        });

    }
}
