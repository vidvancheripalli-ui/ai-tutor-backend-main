package com.aitutor.trail.playlist;

public class PlaylistDto {
    private Long id;
    private String playlistName;
    private int totalVideos;

    public PlaylistDto(Long id, String playlistName, int totalVideos) {
        this.id = id;
        this.playlistName = playlistName;
        this.totalVideos = totalVideos;
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaylistName() { return playlistName; }
    public void setPlaylistName(String playlistName) { this.playlistName = playlistName; }

    public int getTotalVideos() { return totalVideos; }
    public void setTotalVideos(int totalVideos) { this.totalVideos = totalVideos; }
}
