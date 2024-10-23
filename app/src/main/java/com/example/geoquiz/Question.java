package com.example.geoquiz;

public class Question {
    private int mTextResId;     // Recurso de la pregunta (ID de cadena)
    private boolean mAnswerTrue; // Respuesta correcta

    // Constructor de la clase Question
    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    // Método para obtener el ID del recurso de texto de la pregunta
    public int getTextResId() {
        return mTextResId;
    }

    // Método para establecer el ID del recurso de texto de la pregunta
    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    // Método para comprobar si la respuesta es verdadera
    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    // Método para establecer la respuesta correcta
    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
