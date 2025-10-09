package com.aitutor.trail.quiz;



import java.util.List;

public class QuizResultResponse {
    private List<UserAnswer> questions;

    public QuizResultResponse(List<UserAnswer> questions) {
        this.questions = questions;
    }

    public List<UserAnswer> getQuestions() { return questions; }
    public void setQuestions(List<UserAnswer> questions) { this.questions = questions; }
}

