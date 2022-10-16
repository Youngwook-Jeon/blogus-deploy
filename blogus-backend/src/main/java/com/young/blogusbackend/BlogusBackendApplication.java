package com.young.blogusbackend;

import com.young.blogusbackend.model.Bloger;
import com.young.blogusbackend.model.Role;
import com.young.blogusbackend.repository.BlogerRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BlogusBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogusBackendApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public ApplicationRunner initializer(
            BlogerRepository blogerRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> blogerRepository.save(
                Bloger.builder()
                        .email("admin@admin.com")
                        .enabled(true)
                        .role(Role.ROLE_ADMIN)
                        .name("Admin")
                        .password(passwordEncoder.encode("p4ssword!"))
                        .createdAt(Instant.now())
                        .updatedAt(Instant.now())
                        .build()
        );
    }
}
