package com.expressbank.service;

import com.expressbank.model.User;
import com.expressbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id){
        return userRepository.findById(id);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User getOne(Integer id){
        return userRepository.getOne(id);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password){
        return userRepository.findUserByEmailAndPassword(email, password);
    }
}
