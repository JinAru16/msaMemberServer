package com.auth.auth.user.repository;


import com.auth.auth.common.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findUsersByUsername(String username);

    Optional<Users> findUsersByEmail(String email);
}
