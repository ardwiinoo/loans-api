package com.ardwiinoo.loansapi.repository;

import com.ardwiinoo.loansapi.model.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Integer> {

    Optional<Authentication> findByRefreshToken(String refreshToken);
}
