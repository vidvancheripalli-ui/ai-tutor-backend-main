package com.aitutor.trail.quiz;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    private final Client geminiClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public QuizService(Client geminiClient) {
        this.geminiClient = geminiClient;
    }

    public List<UserAnswer> generateQuiz(String playlistName) {
        String prompt = """
                Generate 10 multiple-choice questions for the playlist/topic: '%s'.
                Each question must have exactly 4 options and 1 correct answer.
                Return JSON strictly in this format:
                {
                  "questions": [
                    {
                      "questionText": "string",
                      "options": ["option1", "option2", "option3", "option4"],
                      "correctAnswer": "string"
                    }
                  ]
                }
                """.formatted(playlistName);

        try {
            GenerateContentResponse response =
                    geminiClient.models.generateContent("gemini-2.5-flash", prompt,null);

            String jsonResponse = response.text();
            String cleanResponse = jsonResponse
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();
            Map<?, ?> parsed = objectMapper.readValue(cleanResponse, Map.class);
            List<Map<String, Object>> questionsRaw = (List<Map<String, Object>>) parsed.get("questions");

            List<UserAnswer> userAnswers = new ArrayList<>();
            for (Map<String, Object> q : questionsRaw) {
                UserAnswer ua = new UserAnswer();
                ua.setQuestionText((String) q.get("questionText"));
                ua.setOptions((List<String>) q.get("options"));
                ua.setCorrectAnswer((String) q.get("correctAnswer"));
                ua.setSelectedOption("");
                userAnswers.add(ua);
            }
            return userAnswers;

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate quiz: " + e.getMessage(), e);
        }
    }
}
