package com.maintain.vacation.config;
import com.maintain.vacation.model.User;
import com.maintain.vacation.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User("admin", passwordEncoder.encode("admin123"), 25);
                userRepository.save(admin);
            }
            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User("user", passwordEncoder.encode("user123"), 20);
                userRepository.save(user);
            }
        };
    }
}
