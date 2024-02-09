package com.ds.spring.p1.repository;

import java.util.Optional;

import com.ds.spring.p1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Modifying
  @Transactional
  @Query("UPDATE User u " +
          "SET u.username = :username, " +
          "u.email = :email, " +
          "u.password = :password " +
          "WHERE u.id = :id ")
  void update(Long id, String username, String email, String password);

  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
