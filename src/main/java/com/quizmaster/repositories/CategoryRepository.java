package com.quizmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quizmaster.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {

}
