package com.maloy.iremember.repositories;

import com.maloy.iremember.entity.Company;
import com.maloy.iremember.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByIdAndCompany(Long userId, Company company);
}
