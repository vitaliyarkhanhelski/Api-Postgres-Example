package com.example.questionanswersapipostgres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class QuestionAnswersApiPostgresApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionAnswersApiPostgresApplication.class, args);
    }

}
