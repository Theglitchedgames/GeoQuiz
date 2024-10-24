package com.example.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_INDEX = "index";
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private TextView mScoreTextView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private boolean[] mAnsweredQuestions;
    private int mCurrentIndex = 0;
    private int mCorrectAnswers = 0;
    private int mTotalQuestions = mQuestionBank.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCorrectAnswers = savedInstanceState.getInt("correctAnswers", 0);
            mAnsweredQuestions = savedInstanceState.getBooleanArray("answeredQuestions");
        } else {
            mAnsweredQuestions = new boolean[mQuestionBank.length];
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        mNextButton = findViewById(R.id.next_button);
        mPrevButton = findViewById(R.id.prev_button);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mScoreTextView = findViewById(R.id.score_text_view);

        updateQuestion();
        updateScore();

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putInt("correctAnswers", mCorrectAnswers);
        outState.putBooleanArray("answeredQuestions", mAnsweredQuestions);
    }

    private void updateQuestion() {
        if (areAllQuestionsAnswered()) {
            showResults();
        } else {
            int question = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(question);

            // Deshabilitar los botones si la pregunta ya fue contestada
            if (mAnsweredQuestions[mCurrentIndex]) {
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
            } else {
                mTrueButton.setEnabled(true);
                mFalseButton.setEnabled(true);
            }
        }
    }

    private void updateScore() {
        String scoreText = "Se han acertado " + mCorrectAnswers + " preguntas de " + mTotalQuestions;
        mScoreTextView.setText(scoreText);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;

            if (!mAnsweredQuestions[mCurrentIndex]) {
                mCorrectAnswers++;
                mAnsweredQuestions[mCurrentIndex] = true;
                updateScore();
            }
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

        // Bloquear los botones después de responder
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);

        // Verificar si se ha contestado todo
        if (areAllQuestionsAnswered()) {
            showResults();
        }
    }

    private boolean areAllQuestionsAnswered() {
        for (boolean answered : mAnsweredQuestions) {
            if (!answered) {
                return false;
            }
        }
        return true;
    }

    private void showResults() {
        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
        intent.putExtra("correctAnswers", mCorrectAnswers);
        intent.putExtra("totalQuestions", mTotalQuestions);
        startActivity(intent);
        finish();
    }
}
