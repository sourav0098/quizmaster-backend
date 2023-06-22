package com.quizmaster.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.quizmaster.entities.Question;
import com.quizmaster.entities.Quiz;

public interface QuestionRepository extends JpaRepository<Question, String> {

	// find questions by quiz
	Page<Question> findByQuiz(Quiz quiz, Pageable pageable);

}
