package com.ardwiinoo.loansapi.repository;

import com.ardwiinoo.loansapi.model.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Integer> {
}
