package com.expressbank;

import com.expressbank.model.Role;
import com.expressbank.model.util.RoleUtil;
import com.expressbank.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PaydayTradeApplication {

    @Autowired
    private RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(PaydayTradeApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        CommandLineRunner clr = new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {
                insertRole();
            }
        };

        return clr;
    }

    public void insertRole(){
        Role roleAdmin = new Role(RoleUtil.ROLE_ADMIN.name());
        Role roleUser = new Role(RoleUtil.ROLE_USER.name());

        List<Role> newRole = new ArrayList<>();
        newRole.add(roleAdmin);
        newRole.add(roleUser);

        List<Role> oldRole = roleService.findAll();

        for (int i=0; i<=newRole.size()-1; i++){
            if (oldRole.size()==0){
                roleService.saveAll(newRole);
                System.out.println(newRole + " are saved..");
            }
            else if (i>oldRole.size()-1){
                roleService.save(newRole.get(i));
                System.out.println(newRole.get(i) + " is saved..");
            }
        }
    }

}
