package com.quizmaster.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.quizmaster.entities.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, String> {

	Page<Quiz> findByCategoryCategoryId(String categoryId, Pageable pageable);

	Page<Quiz> findByCategoryCategoryIdAndActiveTrue(String categoryId, Pageable pageable);

}
