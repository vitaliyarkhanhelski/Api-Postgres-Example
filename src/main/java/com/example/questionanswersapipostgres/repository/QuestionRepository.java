package com.example.questionanswersapipostgres.repository;

import com.example.questionanswersapipostgres.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
