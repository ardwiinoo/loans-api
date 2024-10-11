package com.ardwiinoo.loansapi.util;

import com.ardwiinoo.loansapi.model.entity.User;
import com.ardwiinoo.loansapi.model.enums.UserRole;
import com.ardwiinoo.loansapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = User.builder()
                .email("example@gmail.com")
                .password("$2a$10$3QZucbjxWFGuOL60Ed7Soe7JX2jbs//l6rCBZAZOuLI2/.Pr5fORq")
                .firstName("Example")
                .lastName("User")
                .phoneNumber("083245671664")
                .userRole(UserRole.USER)
                .isEnable(true)
                .build();

        userRepository.save(user);
    }
}
