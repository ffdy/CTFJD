package cn.edu.swu.ffdy.springboot.controller;


import cn.edu.swu.ffdy.springboot.entity.User;
import cn.edu.swu.ffdy.springboot.utils.SessionContents;
import cn.edu.swu.ffdy.springboot.utils.UserInfo;
import cn.edu.swu.ffdy.springboot.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
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
    public Page<UserInfo> findAllUsers(@PathVariable("page")Integer page, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        System.out.println(session.getAttribute("username"));
        PageRequest pagerequest = PageRequest.of(page, 10);
        return usersRepository.findAllUserInfo(pagerequest);
    }

    @PostMapping("/findByName")
    public User getUserByName(@RequestBody String name) {
        return usersRepository.findByName(name);
    }

    @PostMapping("/create")
    public String createUser(@RequestBody User user) {
        User oldUser = usersRepository.findByName(user.getName());
        if(oldUser != null) {
            return "用户 " + user.getName() + " 已经存在";
        }
        oldUser = usersRepository.findByEmail(user.getEmail());
        if(oldUser != null) {
            return "邮箱 " + user.getEmail() + " 已被绑定";
        }
        user.setType("user");
        user.setCreated(new Date());
        User newUser = usersRepository.save(user);
        if(newUser != null) {
            return "创建成功";
        } else {
            return "创建失败";
        }
    }

    @PostMapping("/edit")
    public String editUser(@RequestBody User user, @RequestBody String newPass) {
        User oldUser = usersRepository.findById(user.getId()).get();
        if(oldUser != null) {
            if(!user.getName().equals(oldUser.getName())
                    || !user.getEmail().equals(oldUser.getEmail())
                    || !user.getPassword().equals(oldUser.getPassword())) {
                return "ERROR";
            }
            user.setPassword(newPass);
            if(usersRepository.save(user) != null) {
                return "创建成功";
            } else {
                return "创建失败";
            }
        } else {
            return "ERROR";
        }
    }

    @PostMapping("/login")
    public UserInfo login(@RequestBody User user, HttpServletRequest request) {
        User oldUser = usersRepository.findByName(user.getName());
        if(oldUser != null) {
            if(oldUser.getPassword().equals(user.getPassword())) {
                HttpSession session = request.getSession(true);
                session.setAttribute(SessionContents.ID, oldUser.getId());
                session.setAttribute(SessionContents.USER_NAME, oldUser.getName());
                session.setAttribute(SessionContents.USER_TYPE, oldUser.getType());
                return new UserInfo(oldUser.getName(), oldUser.getEmail());
            }
        }
        return null;
    }
}
