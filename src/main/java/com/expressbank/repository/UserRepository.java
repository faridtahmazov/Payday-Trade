package com.expressbank.repository;

import com.expressbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);



    @Query("SELECT u FROM User u WHERE u.email = :email and u.password = :password")
    Optional<User> findUserByEmailAndPassword(
            @Param("email") String email,
            @Param("password") String password);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.accountVerified = true WHERE u.email = :email")
    int accountVerifiedUser(@Param("email") String email);
}
