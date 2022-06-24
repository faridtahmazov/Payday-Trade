package com.expressbank.service;

import com.expressbank.model.Role;
import com.expressbank.model.User;
import com.expressbank.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public void save(Role role){
        roleRepository.save(role);
    }

    public void saveAll(List<Role> roles){
        roleRepository.saveAll(roles);
    }

    public Optional<Role> findById(Integer id){
        return roleRepository.findById(id);
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public void deleteRoleByUser(Integer id){
        roleRepository.deleteById(id);
    }

    public Optional<Role> findRolesByUser(User user){
        return roleRepository.findRolesByUsers(user);
    }
}
