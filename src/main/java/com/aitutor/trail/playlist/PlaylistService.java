package com.aitutor.trail.playlist;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.aitutor.trail.video.Video;
import com.aitutor.trail.video.VideoRepository;
import com.aitutor.trail.auth.User;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final VideoRepository videoRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository, VideoRepository videoRepository) {
        this.playlistRepository = playlistRepository;
        this.videoRepository = videoRepository;
    }

    // Fetch playlists of a user
    public List<Playlist> getUserPlaylists(Long userId) {
        return playlistRepository.findByUserId(userId);
    }

    // Fetch videos of a playlist
    public List<Video> getVideosOfPlaylist(Long playlistId) {
        return videoRepository.findByPlaylistId(playlistId);
    }

    // Generate playlist from AI video URLs
    public Playlist generatePlaylistFromUrls(String urls, String topic, User user) {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName(topic);
        playlist.setUser(user);

        List<Video> videos = Arrays.stream(urls.split(","))
                .map(url -> {
                    Video video = new Video();
                    video.setUrl(url.trim());
                    video.setPlaylist(playlist); // Link video to playlist
                    return video;
                })
                .collect(Collectors.toList());

        playlist.setVideos(videos);

        // Save playlist along with videos
        playlistRepository.save(playlist);
        videoRepository.saveAll(videos);

        return playlist;
    }
}
