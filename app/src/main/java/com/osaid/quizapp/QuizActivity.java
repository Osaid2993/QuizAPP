package com.osaid.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
public class QuizActivity extends AppCompatActivity {

    TextView questionText, progressText;
    Button option0, option1, option2, option3, submitButton;
    ProgressBar progressBar;

    Button[] optionButtons;
    List<Question> questions;
    int currentIndex = 0;
    int score = 0;
    int selectedOption = -1;
    boolean answered = false;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        userName = getIntent().getStringExtra("userName");

        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome " + userName + "!");
        questionText = findViewById(R.id.questionText);
        progressText = findViewById(R.id.progressText);
        progressBar = findViewById(R.id.progressBar);
        option0 = findViewById(R.id.option0);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        submitButton = findViewById(R.id.submitButton);

        optionButtons = new Button[]{option0, option1, option2, option3};

        questions = getQuestions();
        progressBar.setMax(questions.size());

        loadQuestion();

        for (int i = 0; i < optionButtons.length; i++) {
            int index = i;
            optionButtons[i].setOnClickListener(v -> {
                if (!answered) {
                    selectedOption = index;
                    resetButtonStyles();
                    optionButtons[index].setBackgroundResource(R.drawable.default_option);
                }
            });
        }

        submitButton.setOnClickListener(v -> {
            if (selectedOption == -1) return;

            if (!answered) {
                answered = true;
                checkAnswer();
                submitButton.setText("Next");
            } else {
                currentIndex++;
                if (currentIndex < questions.size()) {
                    loadQuestion();
                } else {
                    goToResults();
                }
            }
        });
    }

    void loadQuestion() {
        answered = false;
        selectedOption = -1;
        submitButton.setText("Submit");
        resetButtonStyles();

        Question q = questions.get(currentIndex);
        questionText.setText(q.questionText);

        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setText(q.options[i]);
        }

        progressBar.setProgress(currentIndex);
        progressText.setText("Question " + (currentIndex + 1) + " of " + questions.size());
    }

    void checkAnswer() {
        Question q = questions.get(currentIndex);
        if (selectedOption == q.correctAnswerIndex) {
            score++;
            optionButtons[selectedOption].setBackgroundTintList(null);
            optionButtons[selectedOption].setBackgroundResource(R.drawable.correct_answer);
        } else {
            optionButtons[selectedOption].setBackgroundTintList(null);
            optionButtons[selectedOption].setBackgroundResource(R.drawable.wrong_answer);
            optionButtons[q.correctAnswerIndex].setBackgroundTintList(null);
            optionButtons[q.correctAnswerIndex].setBackgroundResource(R.drawable.correct_answer);
        }
    }

    void resetButtonStyles() {
        for (Button btn : optionButtons) {
            btn.setBackgroundTintList(null);
            btn.setBackgroundResource(R.drawable.default_option);
        }
    }

    void goToResults() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("total", questions.size());
        intent.putExtra("userName", userName);
        startActivity(intent);
        finish();
    }

    List<Question> getQuestions() {
        List<Question> list = new ArrayList<>();

        list.add(new Question("Which programming language is used to develop Android apps in this unit?",
                new String[]{"Swift", "Python", "Java", "Ruby"}, 2));

        list.add(new Question("What is the first lifecycle method called when an Android Activity is created?",
                new String[]{"onStart()", "onResume()", "onCreate()", "onRestart()"}, 2));

        list.add(new Question("What is used to navigate between activities in Android?",
                new String[]{"Fragment", "Intent", "Bundle", "View"}, 1));

        list.add(new Question("Which prototype type is cheap, quick to build, and easy to throw away?",
                new String[]{"High fidelity", "Medium fidelity", "Low fidelity", "Working system"}, 2));

        list.add(new Question("What does the Back Stack do when the user presses the Back button?",
                new String[]{"Pauses the current activity", "Restarts the app", "Pops the current activity and resumes the previous one", "Destroys all activities"}, 2));

        return list;
    }
}
