package com.example.questionanswersapipostgres.repository;

import com.example.questionanswersapipostgres.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
    List<Answer> findByQuestionId (Long questionId);
}
