package com.example.demo;

import com.example.demo.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo2ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserRepo userRepo;

    @Test
    void testRepo(){
        String className = this.userRepo.getClass().getName();
        System.out.println(className);
    }

}
