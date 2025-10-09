package com.aitutor.trail.quiz;
import com.aitutor.trail.quiz.UserQuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuizResultRepository extends JpaRepository<UserQuizResult, Long> {
}