package com.osaid.quizapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class ResultActivity extends AppCompatActivity {

    TextView congratsText, scoreText;
    Button takeNewQuizButton, finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);
        String userName = getIntent().getStringExtra("userName");

        congratsText = findViewById(R.id.congratsText);
        scoreText = findViewById(R.id.scoreText);
        takeNewQuizButton = findViewById(R.id.takeNewQuizButton);
        finishButton = findViewById(R.id.finishButton);

        congratsText.setText("Congratulations " + userName + "!");
        scoreText.setText("YOUR SCORE: " + score + "/" + total);

        takeNewQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.putExtra("userName", userName);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        finishButton.setOnClickListener(v -> {
            finishAffinity();
        });
    }
}
