package com.expressbank.repository;

import com.expressbank.model.Role;
import com.expressbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRolesByUsers(User user);
}
