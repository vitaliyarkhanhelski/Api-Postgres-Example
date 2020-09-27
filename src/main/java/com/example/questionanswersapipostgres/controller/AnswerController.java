package com.example.questionanswersapipostgres.controller;

import com.example.questionanswersapipostgres.exception.ResourceNotFoundException;
import com.example.questionanswersapipostgres.model.Answer;
import com.example.questionanswersapipostgres.repository.AnswerRepository;
import com.example.questionanswersapipostgres.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/questions")
public class AnswerController {

    private AnswerRepository answerRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public AnswerController(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }


    @GetMapping("/{questionId}/answers")
    public List<Answer> getAnswerByQuestionId(@PathVariable Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }


    @PostMapping("/{questionId}/answers")
    public Answer addAnswer(@PathVariable Long questionId, @Valid @RequestBody Answer answer){
        return questionRepository.findById(questionId)
                .map(question -> {
                    System.out.println("!!!!!!!!!!!!!!!!!!!! I A INSIDE !!!!!!!!!!");
                    answer.setQuestion(question);
                    return answerRepository.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id "+questionId));
    }


    @PutMapping("/{questionId}/answers/{answerId}")
    public Answer updateAnswer(@PathVariable Long questionId, @PathVariable Long answerId, @Valid @RequestBody Answer answerRequest){
        if (!questionRepository.existsById(questionId)){
            throw new ResourceNotFoundException("Question not found with id "+questionId);
        }
        return answerRepository.findById(answerId)
                .map(answer -> {
                    answer.setText(answerRequest.getText());
                    return answerRepository.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id "+questionId));
    }


    @DeleteMapping("/{questionId}/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId, @PathVariable Long answerId){
        if (!questionRepository.existsById(questionId)){
            throw new ResourceNotFoundException("Question not found with id "+questionId);
        }
        return answerRepository.findById(answerId)
                .map(answer -> {
                    answerRepository.delete(answer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id "+questionId));
    }
}
