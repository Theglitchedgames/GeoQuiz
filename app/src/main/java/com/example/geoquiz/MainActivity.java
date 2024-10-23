package com.example.geoquiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Botones y TextView
    private Button mTrueButton;      // Botón para respuesta "True"
    private Button mFalseButton;     // Botón para respuesta "False"
    private Button mNextButton;      // Botón para pasar a la siguiente pregunta
    private TextView mQuestionTextView; // TextView para mostrar la pregunta actual

    // Array de preguntas
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0; // Índice de la pregunta actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Asegúrate de que el layout tenga el nombre correcto

        // Inicializar el TextView para mostrar preguntas
        mQuestionTextView = findViewById(R.id.question_text_view);

        // Vincular los botones con sus ID en el layout
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);

        // Mostrar la primera pregunta
        updateQuestion();

        // Listener para el botón True
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true); // Verificar si la respuesta es verdadera
            }
        });

        // Listener para el botón False
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false); // Verificar si la respuesta es falsa
            }
        });

        // Listener para el botón Next
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pasar a la siguiente pregunta
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion(); // Actualizar el TextView con la nueva pregunta
            }
        });
    }

    private void updateQuestion() {
        // Obtener el recurso de la pregunta actual y mostrarlo
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        // Obtener la respuesta correcta de la pregunta actual
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId; // ID del recurso del mensaje a mostrar

        // Determinar si la respuesta del usuario es correcta o incorrecta
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast; // Respuesta correcta
        } else {
            messageResId = R.string.incorrect_toast; // Respuesta incorrecta
        }

        // Mostrar el mensaje correspondiente
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
