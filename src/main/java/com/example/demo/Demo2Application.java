package com.example.demo;

import com.example.demo.config.AppConstants;
import com.example.demo.entities.Role;
import com.example.demo.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class Demo2Application implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;


    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class, args);


    }


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    private Logger logger = LoggerFactory.getLogger(Demo2Application.class);

    @Override
    public void run(String... args) throws Exception {
        try {
            Role role = new Role();
            role.setId(AppConstants.ROLE_USER);
            role.setName("ROLE_USER");

            Role role1 = new Role();
            role1.setId(AppConstants.ROLE_ADMIN);
            role1.setName("ROLE_ADMIN");

            List<Role> roleList = List.of(role, role1);

            List<Role> result = this.roleRepo.saveAll(roleList);
            this.logger.info("Roles created : {}", result);


        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
