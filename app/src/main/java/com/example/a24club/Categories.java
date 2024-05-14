package com.example.a24club;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Categories extends AppCompatActivity {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Long highest_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Button Cat1 = findViewById(R.id.cat1);
        Button Cat2 = findViewById(R.id.cat2);
        Button Cat3 = findViewById(R.id.cat3);
        Cat1.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        Cat2.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        Cat3.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        readHighScore(mAuth.getCurrentUser().getUid());
        readHighScore2(mAuth.getCurrentUser().getUid());
        readHighScore3(mAuth.getCurrentUser().getUid());
        Cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Quiz2.class);
                startActivity(intent);
            }
        });
        Cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Quiz3.class);
                startActivity(intent);
            }
        });
        Button backButton = findViewById(R.id.back_button);
        backButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void readHighScore(String userId) {
        System.out.println("inside the readHighScore method");
        db.collection("userScores").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && documentSnapshot.contains("highScore")) {
                        highest_score = documentSnapshot.getLong("highScore") != null ? documentSnapshot.getLong("highScore") : 0;
                        System.out.println(highest_score);
                        updateHighScoreText();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("Firestore", "Error getting documents: ", e);
                });
    }
    private void readHighScore2(String userId) {
        System.out.println("inside the readHighScore method");
        db.collection("Quiz2").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && documentSnapshot.contains("highScore")) {
                        highest_score = documentSnapshot.getLong("highScore") != null ? documentSnapshot.getLong("highScore") : 0;
                        System.out.println(highest_score);
                        updateHighScoreText2();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("Firestore", "Error getting documents: ", e);
                });
    }
    private void readHighScore3(String userId) {
        System.out.println("inside the readHighScore method");
        db.collection("Quiz3").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && documentSnapshot.contains("highScore")) {
                        highest_score = documentSnapshot.getLong("highScore") != null ? documentSnapshot.getLong("highScore") : 0;
                        System.out.println(highest_score);
                        updateHighScoreText3();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("Firestore", "Error getting documents: ", e);
                });
    }

    private void updateHighScoreText() {
        Button Cat1 = findViewById(R.id.cat1);
        String highscoreText = "Champions League - " + String.valueOf(highest_score) + "%";
        Cat1.setText(highscoreText);
    }
    private void updateHighScoreText2() {
        Button Cat2 = findViewById(R.id.cat2);
        String highscoreText2 = "Euro - " + String.valueOf(highest_score) + "%";
        Cat2.setText(highscoreText2);
    }
    private void updateHighScoreText3() {
        Button Cat2 = findViewById(R.id.cat3);
        String highscoreText3 = "Premier League - " + String.valueOf(highest_score) + "%";
        Cat2.setText(highscoreText3);
    }

}