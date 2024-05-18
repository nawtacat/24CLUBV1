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
import com.google.firebase.firestore.Query;

import java.util.Arrays;
import java.util.List;

public class Articles extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button cat1, cat2, cat3, cat4;
    private List<Button> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        cat1 = findViewById(R.id.cat1);
        cat2 = findViewById(R.id.cat2);
        cat3 = findViewById(R.id.cat3);
        cat4 = findViewById(R.id.cat4);

        cat1.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        cat2.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        cat3.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        cat4.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        buttons = Arrays.asList(cat1, cat2, cat3, cat4);  // Use Arrays.asList for compatibility

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "User not authenticated.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadTopScores();
    }

    private void loadTopScores() {
        db.collection("totalScore")
                .orderBy("total", Query.Direction.DESCENDING)
                .limit(4)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                    for (int i = 0; i < documents.size(); i++) {
                        DocumentSnapshot document = documents.get(i);
                        if (document != null) {
                            String userId = document.getId();
                            Long score = document.getLong("total");
                            if (score != null) {
                                fetchNicknameAndUpdateUI(userId, score, i);
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching top scores: ", e));
    }

    private void fetchNicknameAndUpdateUI(String userId, Long score, int buttonIndex) {
        db.collection("userNames").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    String nickname = documentSnapshot.getString("nick");
                    if (nickname == null) nickname = "Anonymous";  // Default nickname if not found
                    String text = nickname + ": " + score;
                    if (buttonIndex < buttons.size()) {
                        buttons.get(buttonIndex).setText(text);
                    }
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching nickname: ", e));
    }
}
