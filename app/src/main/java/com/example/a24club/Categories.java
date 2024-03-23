package com.example.a24club;

import static com.example.a24club.MainActivity.highest_score;
import static com.example.a24club.MainActivity.score;

import android.content.Intent;
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
    }
}