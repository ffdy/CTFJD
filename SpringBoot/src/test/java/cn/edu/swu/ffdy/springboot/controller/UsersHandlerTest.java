package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.User;
import cn.edu.swu.ffdy.springboot.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsersHandlerTest {
    @Autowired
    UsersRepository usersRepository;
    @Test
    public void find() {
//        System.out.println(usersRepository.findByName("admin"));
        PageRequest request = PageRequest.of(0, 10);
//        Page<User> ans =  usersRepository.findAll(request);
//        System.out.println(ans.);
        System.out.println(usersRepository.findAllUserInfo(request));
    }


}