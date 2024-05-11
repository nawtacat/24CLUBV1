package com.example.a24club;

import static com.example.a24club.MainActivity.highest_score;
import static com.example.a24club.MainActivity.score;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Button Cat1 = findViewById(R.id.cat1);
        Button Cat2 = findViewById(R.id.cat2);
        Button Cat3 = findViewById(R.id.cat3);
        Cat1.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        Cat2.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        Cat3.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        String highscoreText = "Champions League - " + String.valueOf(highest_score);
        Cat1.setText(highscoreText);
        Cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MainActivity when Cat1 button is clicked
                Intent intent = new Intent(Categories.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}