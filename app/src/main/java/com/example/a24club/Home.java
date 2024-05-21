package com.example.a24club;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Home extends AppCompatActivity {

    private Button quizButton;
    private Button articlesButton;
    private Button settingsButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        quizButton = findViewById(R.id.quiz_btn);
        articlesButton = findViewById(R.id.articles_btn);
        settingsButton = findViewById(R.id.settings_btn);

        // Set colors
        quizButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        articlesButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        settingsButton.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        // Set onClickListeners
        quizButton.setOnClickListener(v -> startActivity(new Intent(Home.this, Categories.class)));
        articlesButton.setOnClickListener(v -> startActivity(new Intent(Home.this, Articles.class)));
        settingsButton.setOnClickListener(v -> startActivity(new Intent(Home.this, Settings.class)));

        // Check authentication and fetch scores
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            fetchAndUpdateScores(user.getUid());
        } else {
            Toast.makeText(this, "User not authenticated.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAndUpdateScores(String userId) {
        List<String> quizzes = Arrays.asList("userScores", "Quiz2", "Quiz3", "Quiz4");
        AtomicInteger totalScore = new AtomicInteger(0);

        for (String quiz : quizzes) {
            db.collection(quiz).document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists() && documentSnapshot.contains("highScore")) {
                            Long score = documentSnapshot.getLong("highScore");
                            if (score != null) {
                                totalScore.addAndGet(score.intValue());
                            }
                        } else {
                            Log.d("Firestore", "No highScore field found in " + quiz);
                        }
                        // After all fetches are done, update total score
                        if (totalScore.get() > 0) {
                            updateTotalScore(userId, totalScore.get());
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.d("Firestore", "Error getting documents from " + quiz + ": ", e);
                    });
        }
    }

    private void updateTotalScore(String userId, int total) {
        Map<String, Object> scoreMap = new HashMap<>();
        scoreMap.put("total", total);

        db.collection("totalScore").document(userId)
                .set(scoreMap, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Total score successfully updated!"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error updating total score", e));
    }
}

