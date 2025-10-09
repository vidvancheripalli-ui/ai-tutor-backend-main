package com.aitutor.trail.video;

import jakarta.persistence.*;

import com.aitutor.trail.playlist.Playlist;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;
    private double duration;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    public Video() {}

    public Video(String title, String url, double duration, Playlist playlist) {
        this.title = title;
        this.url = url;
        this.duration = duration;
        this.playlist = playlist;
    }

   

	public Video(String url, Playlist playlist) {
		super();
		this.url = url;
		this.playlist = playlist;
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

    public Playlist getPlaylist() { return playlist; }
    public void setPlaylist(Playlist playlist) { this.playlist = playlist; }
}
