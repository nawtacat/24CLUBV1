package com.example.a24club;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class Settings extends AppCompatActivity {
    Button buttonc;
    private FirebaseFirestore db;

    Button set_back;
    EditText chang;
    Button nicktext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        set_back = findViewById(R.id.setting_goback);
        db = FirebaseFirestore.getInstance();
        set_back.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        buttonc = findViewById(R.id.logout_btn);
        buttonc.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
        chang = findViewById(R.id.nick);
        nicktext = findViewById(R.id.nick_btn);
        nicktext.setBackgroundTintList(ColorStateList.valueOf((Color.BLACK)));
        chang.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));

        loadNicknameFromFirestore();
        chang.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String newNickname = chang.getText().toString().trim();
                if (!newNickname.isEmpty()) {
                    nicktext.setText(newNickname);  // Update the button text
                    updateFirestoreNickname(newNickname);  // Update Firestore
                    return true;
                }
            }
            return false;
        });
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
        chang.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String newNickname = chang.getText().toString().trim();
                if (!newNickname.isEmpty()) {
                    nicktext.setText(newNickname);  // Update the button text
                    updateFirestoreNickname(newNickname);  // Update Firestore
                    return true;
                }
            }
            return false;
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
        nicktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Settings.this, "This is your nickname, change it below", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void updateFirestoreNickname(String nickname) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(Settings.this, "User not authenticated.", Toast.LENGTH_SHORT).show();
            return;  // Stop further execution if user is not logged in
        }
        String userId = user.getUid();  // Now safely retrieved

        Map<String, Object> data = new HashMap<>();
        data.put("nick", nickname);

        db.collection("userNames").document(userId)
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Settings.this, "Nickname updated successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Settings.this, "Error updating nickname: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
    private void loadNicknameFromFirestore() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(Settings.this, "User not authenticated.", Toast.LENGTH_SHORT).show();
            return;  // Stop further execution if user is not logged in
        }
        String userId = user.getUid();  // Now safely retrieved

        db.collection("userNames").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists() && documentSnapshot.contains("nick")) {
                        String nickname = documentSnapshot.getString("nick");
                        nicktext.setText(nickname);  // Set the fetched nickname as the button text
                    } else {
                        Toast.makeText(Settings.this, "Nickname not found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Settings.this, "Error loading nickname: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

}
