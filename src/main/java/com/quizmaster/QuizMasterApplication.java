package com.quizmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.quizmaster.entities.Role;
import com.quizmaster.repositories.RoleRepository;

@SpringBootApplication
@EnableWebMvc
public class QuizMasterApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Value("${role.admin.id}")
	private String roleAdminId;

	@Value("${role.normal.id}")
	private String roleNormalId;

	public static void main(String[] args) {
		SpringApplication.run(QuizMasterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role roleAdmin = Role.builder().roleId(this.roleAdminId).roleName("ROLE_ADMIN").build();
			Role roleNormal = Role.builder().roleId(this.roleNormalId).roleName("ROLE_NORMAL").build();
			this.roleRepository.save(roleAdmin);
			this.roleRepository.save(roleNormal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
