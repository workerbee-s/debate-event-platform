package me.shreyeschekuru.dsa.data.service;

import me.shreyeschekuru.dsa.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);
}
