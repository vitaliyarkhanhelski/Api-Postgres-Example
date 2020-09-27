package com.example.questionanswersapipostgres.controller;

import com.example.questionanswersapipostgres.exception.ResourceNotFoundException;
import com.example.questionanswersapipostgres.model.Question;
import com.example.questionanswersapipostgres.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @GetMapping
    public Page<Question> getQuestion (Pageable pageable) {
        return questionRepository.findAll(pageable);
    }


    @PostMapping
    public Question createQuestion(@Valid @RequestBody Question question){
        return questionRepository.save(question);
    }


    @PutMapping("/{questionId}")
    public Question updateQuestion(@PathVariable Long questionId, @Valid @RequestBody Question questionRequest){
        return  questionRepository.findById(questionId)
                .map(question -> {
                   question.setTitle(questionRequest.getTitle());
                    question.setDescription(questionRequest.getDescription());
                    return questionRepository.save(question);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id "+questionId));
    }


    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId){
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return ResponseEntity.ok().build();
                }).orElseThrow(()-> new ResourceNotFoundException("Question not found with id "+questionId));
    }

}
