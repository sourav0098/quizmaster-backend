package com.quizmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quizmaster.entities.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

}
