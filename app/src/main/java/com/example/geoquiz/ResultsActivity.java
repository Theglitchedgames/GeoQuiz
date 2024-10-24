package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView resultTextView = findViewById(R.id.result_text_view);
        Button retryButton = findViewById(R.id.retry_button);

        Intent intent = getIntent();
        int correctAnswers = intent.getIntExtra("correctAnswers", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);

        String resultMessage;
        if (correctAnswers > totalQuestions / 2) {
            resultMessage = "Enhorabuena, has respondido correctamente " + correctAnswers + " preguntas de " + totalQuestions + ".";
        } else {
            resultMessage = "Lo siento, solo has respondido correctamente " + correctAnswers + " preguntas de " + totalQuestions + ". Estudia un poco más.";
        }

        resultTextView.setText(resultMessage);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restartIntent = new Intent(ResultsActivity.this, MainActivity.class);
                restartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(restartIntent);
                finish();
            }
        });
    }
}
