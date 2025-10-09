package com.aitutor.trail.playlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.aitutor.trail.auth.User;
import com.aitutor.trail.video.Video;
import com.aitutor.trail.video.VideoDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/my-playlists")
    public List<PlaylistDto> getMyPlaylists(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) throw new RuntimeException("User not logged in");

        List<Playlist> playlists = playlistService.getUserPlaylists(user.getId());
        return playlists.stream()
                .map(p -> new PlaylistDto(p.getId(), p.getPlaylistName(), p.getVideos().size()))
                .toList();
    }

    @GetMapping("/{playlistId}/videos")
    public List<VideoDto> getVideos(@PathVariable Long playlistId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) throw new RuntimeException("User not logged in");

        List<Video> videos = playlistService.getVideosOfPlaylist(playlistId);
        return videos.stream()
                .map(v -> new VideoDto(v.getId(), v.getTitle(), v.getUrl(), v.getDuration()))
                .toList();
    }
}
