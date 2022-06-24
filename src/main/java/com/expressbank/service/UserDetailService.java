package com.expressbank.service;

import com.expressbank.model.UserDetail;
import com.expressbank.model.User;
import com.expressbank.repository.UserRepository;
import com.expressbank.token.ConfirmationToken;
import com.expressbank.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "User with email %s not found!";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        System.out.println(email);

        userOptional.orElseThrow(()
                -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));

        return userOptional.map(UserDetail::new).get();
    }

    public String signUpUser(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exist!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        System.out.println("Token: " + token);

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public int enableAppUser(String email) {
        return userRepository.accountVerifiedUser(email);
    }

}