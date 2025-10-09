package com.aitutor.trail.video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import com.aitutor.trail.playlist.Playlist;
import com.aitutor.trail.playlist.PlaylistDto;
import com.aitutor.trail.auth.User;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/generate-playlist")
    public PlaylistDto generatePlaylist(@RequestBody VideoRequest request, HttpSession session) {
        // Get logged-in user
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("User not logged in");
        }

        try {
            // Generate playlist via VideoService
            Playlist playlist = videoService.askGemini(
                    user,
                    request.getTopic(),
                    request.getClassLevel(),
                    request.getLanguage()
            );

            int totalVideos = (playlist.getVideos() != null) ? playlist.getVideos().size() : 0;
            return new PlaylistDto(playlist.getId(), playlist.getPlaylistName(), totalVideos);

        } catch (Exception e) {
            // Wrap any exceptions in a runtime exception (Spring will return 500)
            throw new RuntimeException("Failed to generate playlist", e);
        }
    }
}
