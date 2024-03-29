package com.usermanagerservice.UserManagerService;

import com.usermanagerservice.UserManagerService.models.Role;
import com.usermanagerservice.UserManagerService.models.User;
import com.usermanagerservice.UserManagerService.repositoty.RoleRepository;
import com.usermanagerservice.UserManagerService.repositoty.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class UserManagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;

			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User(
					1L,
					"admin",
					passwordEncoder.encode("admin"),
					"",
					"",
					"",
					null,
					roles
					);
			userRepository.save(admin);
		};
	}
}
