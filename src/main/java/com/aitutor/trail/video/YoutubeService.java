package com.aitutor.trail.video;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeService {

    private final YouTube youtube;

    public YoutubeService(@Value("${youtube.api.key}") String apiKey) throws Exception {
        this.youtube = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                request -> {}
        ).setApplicationName("AI-Tutor").build();
        this.apiKey = apiKey;
    }
    private final String apiKey;
    public List<String> getVideoUrls(String query) throws Exception {
        // Step 1: Search by keyword
        YouTube.Search.List search = youtube.search()
                .list("id")
                .setQ(query)
                .setType("video")
                .setMaxResults((long) 10)
                .setKey(apiKey);

        SearchListResponse searchResponse = search.execute();
        List<SearchResult> results = searchResponse.getItems();

        if (results.isEmpty()) {
            return new ArrayList<>();
        }

        // Collect video IDs
        List<String> videoIds = new ArrayList<>();
        for (SearchResult r : results) {
            if (r.getId().getVideoId() != null) {
                videoIds.add(r.getId().getVideoId());
            }
        }

        // Step 2: Get details (durations)
        YouTube.Videos.List videoRequest = youtube.videos()
                .list("contentDetails")
                .setId(String.join(",", videoIds))
                .setKey(apiKey);

        VideoListResponse videoResponse = videoRequest.execute();

        List<String> validUrls = new ArrayList<>();

        for (com.google.api.services.youtube.model.Video video : videoResponse.getItems()) {
            String durationISO = video.getContentDetails().getDuration();
            Duration duration = Duration.parse(durationISO); // e.g., PT45M12S
            long minutes = duration.toMinutes();

            if (minutes >= 30) { // ✅ Only include videos >= 30 min
                validUrls.add("https://www.youtube.com/watch?v=" + video.getId());
            }
        }

        return validUrls;
    }
}
