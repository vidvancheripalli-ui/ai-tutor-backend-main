package com.aitutor.trail.video; 
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aitutor.trail.auth.User;
import com.aitutor.trail.playlist.Playlist;
import com.aitutor.trail.playlist.PlaylistRepository;
import com.google.genai.Client;

@Service
public class VideoService {

    private final YoutubeService youTubeUrlService;
    private final PlaylistRepository playlistRepository;
    private final VideoRepository videoRepository;
    private final Client geminiClient;

    public VideoService(YoutubeService youTubeUrlService,
                        PlaylistRepository playlistRepository,
                        VideoRepository videoRepository,
                        Client geminiClient) {
        this.youTubeUrlService = youTubeUrlService;
        this.playlistRepository = playlistRepository;
        this.videoRepository = videoRepository;
        this.geminiClient = geminiClient;
    }

    public Playlist askGemini(User user, String topic, String classLevel, String language) throws Exception {
        // Step 1: Create playlist
        Playlist playlist = new Playlist(topic + " - " + classLevel, user);
        playlist = playlistRepository.save(playlist);

        // Step 2: Get video titles from Gemini AI
        String prompt = "Give me a list of YouTube video titles with channel names for '" + topic +
                        "' for class " + classLevel + " in " + language + ". Return as a comma separated list.";
        var response = geminiClient.models.generateContent("gemini-2.5-flash", prompt, null);

        List<String> videoTitles = Arrays.stream(response.text().split(","))
                                         .map(String::trim)
                                         .collect(Collectors.toList());

        // Step 3: Loop through titles, fetch first URL from YouTube API, save as Video
        for (String title : videoTitles) {
            List<String> urls = youTubeUrlService.getVideoUrls(title);
            if (!urls.isEmpty()) {
                Video video = new Video(urls.get(0), playlist); // only first URL
                videoRepository.save(video);
                playlist.getVideos().add(video);
            }
        }

        return playlistRepository.save(playlist);
    }
}
