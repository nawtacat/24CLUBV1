package com.example.a24club;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;

    public static int score = 0;
    public static int highest_score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.answer1);
        ansB = findViewById(R.id.answer2);
        ansC = findViewById(R.id.answer3);
        ansD = findViewById(R.id.answer4);
        submitBtn = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        //totalQuestionsTextView.setText(String.valueOf(totalQuestion));

        loadNewQuestion();




    }

    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submit_btn){
            if(selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])){
                score++;

            }
            if(selectedAnswer == QuestionAnswer.correctAnswers[0]){
                ansA.setBackgroundColor(Color.GREEN);
            }
            if(selectedAnswer == QuestionAnswer.correctAnswers[1]){
                ansB.setBackgroundColor(Color.GREEN);
            }
            if(selectedAnswer == QuestionAnswer.correctAnswers[2]){
                ansC.setBackgroundColor(Color.GREEN);
            }
            if(selectedAnswer == QuestionAnswer.correctAnswers[3]){
                ansD.setBackgroundColor(Color.GREEN);
            }

            currentQuestionIndex++;
            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadNewQuestion();
                }
            }, 2000);


        }else{
            //choices button clicked
            selectedAnswer  = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);

        }

    }

    void loadNewQuestion(){

        if(currentQuestionIndex == totalQuestion ){

            finishQuiz();
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

    }

    void finishQuiz(){
        if(highest_score < score){
            highest_score = score * 100 / QuestionAnswer.question.length;
        }
        String passStatus = "";
        if(score > totalQuestion*0.60){
            passStatus = "Passed";
        }else{
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is "+ score+" out of "+ totalQuestion)
                .setPositiveButton("Practice again",(dialogInterface, i) -> practiceAgain() )
                .setCancelable(false)
                .show();


    }

    void practiceAgain() {
        score = 0;
        currentQuestionIndex = 0;

        // Create an Intent to start the Categories activity
        Intent intent = new Intent(MainActivity.this, Categories.class);
        startActivity(intent);
    }

}