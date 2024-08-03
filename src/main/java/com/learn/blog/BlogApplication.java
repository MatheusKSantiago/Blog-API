package com.learn.blog;

import com.learn.blog.user.User;
import com.learn.blog.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogApplication {

	@Value("${spring.profiles.active}")
	private String profile;
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ApplicationRunner init(UserRepository userRepo,
								  PasswordEncoder encoder){
		return a->{
			var admin = new User();
			admin.setEmail("admin");
			admin.setPassword(encoder.encode("123"));
			admin.setRole("MANAGER");
			userRepo.save(admin);
		};
	}
}
