package com.expressbank.token;

import com.expressbank.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    @Autowired
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public void deleteToken(Integer id){
        confirmationTokenRepository.deleteById(id);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public Optional<ConfirmationToken> findById(Integer id){
        return confirmationTokenRepository.findById(id);
    }

    public Optional<ConfirmationToken> findByUser(User user){
        return confirmationTokenRepository.findConfirmationTokenByUser(user);
    }
}

