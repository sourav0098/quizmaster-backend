package com.quizmaster.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.quizmaster.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, String> {

	List<Question> findByQuizQuizId(String quizId);

	long countByQuizQuizId(String quizId);
	
}
