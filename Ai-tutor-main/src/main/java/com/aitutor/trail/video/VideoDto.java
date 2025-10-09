package com.aitutor.trail.video;

public class VideoDto {
    private Long id;
    private String title;
    private String url;
    private double duration;

    public VideoDto(Long id, String title, String url, double duration) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.duration = duration;
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public double getDuration() { return duration; }
    public void setDuration(double duration) { this.duration = duration; }
}
