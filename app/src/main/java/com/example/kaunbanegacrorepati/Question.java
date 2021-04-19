package com.example.kaunbanegacrorepati;

import java.io.Serializable;

public class Question implements Serializable {
    String question, correct_answer, response;
    String[] options;

    public Question(){

    }

    public Question(String question, String correct_answer, String[] options, String response) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.options = options;
        this.response = response;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
