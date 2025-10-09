package com.aitutor.trail.quiz;

import com.aitutor.trail.quiz.UserQuizResult;
import com.aitutor.trail.quiz.UserQuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQuizResultService {

    @Autowired
    private UserQuizResultRepository repository;

    public void saveResult(UserQuizResult result) {
        repository.save(result);
    }
}
