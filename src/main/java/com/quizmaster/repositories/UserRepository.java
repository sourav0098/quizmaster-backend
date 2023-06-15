package com.quizmaster.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quizmaster.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
	// This method finds a user by email
	Optional<User> findByEmail(String email);

	User findUserByEmailIgnoreCase(String email);
}