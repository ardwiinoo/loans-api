package com.ardwiinoo.loansapi.util;

import com.ardwiinoo.loansapi.exception.NotFoundError;
import com.ardwiinoo.loansapi.model.entity.User;
import com.ardwiinoo.loansapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserContextUtil {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new NotFoundError("User not found"));
    }
}
