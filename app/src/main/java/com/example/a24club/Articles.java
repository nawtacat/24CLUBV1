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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Articles extends AppCompatActivity {
    private Button cat1, cat2, cat3, cat4;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        Button backie = findViewById(R.id.backbtn);
        backie.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        backie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Articles.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        cat1 = findViewById(R.id.cat1);
        cat2 = findViewById(R.id.cat2);
        cat3 = findViewById(R.id.cat3);
        cat4 = findViewById(R.id.cat4);

        cat1.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        cat2.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        cat3.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        cat4.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "User not authenticated.", Toast.LENGTH_SHORT).show();
            finish();
            return;  // Stop further execution if user is not logged in
        }

        fetchLeaderboardData();
    }

    private void fetchLeaderboardData() {
        db.collection("totalScore")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<LeaderboardEntry> tempEntries = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String userId = document.getId();
                            Long score = document.getLong("total");
                            if (score != null) {
                                tempEntries.add(new LeaderboardEntry(userId, score.intValue()));
                            } else {
                                Log.d("Firestore", "No total field found for user: " + userId);
                            }
                        }
                        // Fetch nicknames for the top 4 scores
                        fetchNicknamesForTopScores(tempEntries);
                    } else {
                        Log.d("Firestore", "Error getting total scores: ", task.getException());
                    }
                });
    }

    private void fetchNicknamesForTopScores(List<LeaderboardEntry> tempEntries) {
        Collections.sort(tempEntries); // Sort in descending order based on scores

        int limit = Math.min(4, tempEntries.size()); // Get top 4 or less if fewer entries exist
        List<LeaderboardEntry> topEntries = tempEntries.subList(0, limit);

        for (int i = 0; i < topEntries.size(); i++) {
            LeaderboardEntry entry = topEntries.get(i);
            int index = i; // Store the current index for use in the listener
            db.collection("userNames").document(entry.getNickname()) // Here nickname is actually userId
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists() && document.contains("nick")) {
                                String nickname = document.getString("nick");
                                entry.setNickname(nickname); // Update nickname
                                updateButton(index, entry); // Update the corresponding button
                            } else {
                                Log.d("Firestore", "No nickname found for user: " + entry.getNickname());
                            }
                        } else {
                            Log.d("Firestore", "Error getting nickname for user: " + entry.getNickname(), task.getException());
                        }
                    });
        }
    }

    private void updateButton(int index, LeaderboardEntry entry) {
        String text = entry.getNickname() + ": " + entry.getScore();
        switch (index) {
            case 0:
                cat1.setText(text);
                break;
            case 1:
                cat2.setText(text);
                break;
            case 2:
                cat3.setText(text);
                break;
            case 3:
                cat4.setText(text);
                break;
            default:
                break;
        }
    }

    private static class LeaderboardEntry implements Comparable<LeaderboardEntry> {
        private String nickname;
        private int score;

        public LeaderboardEntry(String nickname, int score) {
            this.nickname = nickname;
            this.score = score;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(LeaderboardEntry other) {
            return Integer.compare(other.score, this.score); // Sort in descending order
        }
    }
}

