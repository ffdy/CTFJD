package cn.edu.swu.ffdy.springboot.controller;


import cn.edu.swu.ffdy.springboot.entity.User;
import cn.edu.swu.ffdy.springboot.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UsersHandler {
    @Autowired
    UsersRepository usersRepository;

    @PostMapping("/findById")
    public Optional<User> findByName(@RequestBody Integer id) {
        return usersRepository.findById(id);
    }

    @GetMapping("/findAll/{page}")
    public Page<User> findAllUsers(@PathVariable("page")Integer page) {
        PageRequest request = PageRequest.of(page, 10);
        return usersRepository.findAll(request);
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User user) {
        User newUser = usersRepository.save(user);
        if(newUser != null) {
            return "success";
        } else {
            return "fail";
        }
    }
}
