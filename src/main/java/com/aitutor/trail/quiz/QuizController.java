package com.aitutor.trail.quiz;

import com.aitutor.trail.quiz.*;
import com.aitutor.trail.quiz.QuizService;
import com.aitutor.trail.quiz.UserQuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService aiQuizService;

    @Autowired
    private UserQuizResultService userQuizResultService;

    @PostMapping("/take")
    public ResponseEntity<QuizResultResponse> takeQuiz(@RequestBody QuizRequest request) {
        List<UserAnswer> questions = aiQuizService.generateQuiz(request.getPlaylistName());
        return ResponseEntity.ok(new QuizResultResponse(questions));
    }

    @PostMapping("/submit")
    public ResponseEntity<UserQuizResult> submitQuiz(@RequestBody SubmitQuizRequest submitRequest) {
        List<UserAnswer> userAnswers = submitRequest.getAnswers();

        long correctCount = userAnswers.stream()
                .filter(ans -> ans.getSelectedOption() != null &&
                               ans.getSelectedOption().equals(ans.getCorrectAnswer()))
                .count();

        UserQuizResult result = new UserQuizResult();
        result.setUserId(submitRequest.getUserId());
        result.setPlaylistName(submitRequest.getPlaylistName());
        result.setScore((int) correctCount);

        userQuizResultService.saveResult(result);

        return ResponseEntity.ok(result);
    }
}
