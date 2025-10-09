package com.aitutor.trail.quiz;

import java.util.List;

public class SubmitQuizRequest {
    private Long userId;
    private String playlistName;
    private List<UserAnswer> answers;

    // getters & setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getPlaylistName() { return playlistName; }
    public void setPlaylistName(String playlistName) { this.playlistName = playlistName; }
    public List<UserAnswer> getAnswers() { return answers; }
    public void setAnswers(List<UserAnswer> answers) { this.answers = answers; }
}

