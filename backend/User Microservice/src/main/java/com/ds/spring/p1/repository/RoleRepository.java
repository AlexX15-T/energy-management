package com.ds.spring.p1.repository;

import java.util.Optional;

import com.ds.spring.p1.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ds.spring.p1.models.UserType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(UserType name);
}
