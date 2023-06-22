package com.quizmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quizmaster.entities.Quiz;


public interface QuizRepository extends JpaRepository<Quiz, String> {

}
