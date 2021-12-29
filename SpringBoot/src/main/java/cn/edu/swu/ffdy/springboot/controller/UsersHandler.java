package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.User;
import cn.edu.swu.ffdy.springboot.repository.UsersRepository;
import cn.edu.swu.ffdy.springboot.utils.DockerClientService;
import cn.edu.swu.ffdy.springboot.utils.SessionContents;
import cn.edu.swu.ffdy.springboot.utils.UserInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
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
    public Page<UserInfo> findAllUsers(@PathVariable("page") Integer page, HttpServletRequest request) {
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
        if (oldUser != null) {
            return "用户 " + user.getName() + " 已经存在";
        }
        oldUser = usersRepository.findByEmail(user.getEmail());
        if (oldUser != null) {
            return "邮箱 " + user.getEmail() + " 已被绑定";
        }
        user.setType("user");
        user.setCreated(new Date());
        User newUser = usersRepository.save(user);
        if (newUser != null) {
            return "创建成功";
        } else {
            return "创建失败";
        }
    }

    @PostMapping("/edit")
    public String editUser(@RequestBody UserWithPass userWithPass, HttpServletRequest request) {
        System.out.println(userWithPass);
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(SessionContents.ID);
        if (userId == null) return "用户未登录";
        User oldUser = usersRepository.findById(userId).get();
        if (oldUser != null) {
            if (!userWithPass.getPassword().equals(oldUser.getPassword())) {
                return "密码错误";
            }
            if (userWithPass.getEmail() != null && userWithPass.getEmail() != "") {
                oldUser.setEmail(userWithPass.getEmail());
            }
            if (userWithPass.getNewPass() != null && userWithPass.getNewPass() != "") {
                oldUser.setPassword(userWithPass.getNewPass());
            }
            if (usersRepository.save(oldUser) != null) {
                return "success";
            } else {
                return "修改失败";
            }
        } else {
            return "不存在该用户";
        }
    }

    @PostMapping("/login")
    public UserInfo login(@RequestBody UserWithValidate user, HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        if(!(user.getValidate().equals(session.getAttribute(SessionContents.USER_VALIDATE_CODE)))) {
            return null;
        }

        User oldUser = usersRepository.findByName(user.getName());
        if (oldUser != null) {
            if (oldUser.getPassword().equals(user.getPassword())) {
                session.setAttribute(SessionContents.ID, oldUser.getId());
                session.setAttribute(SessionContents.USER_NAME, oldUser.getName());
                session.setAttribute(SessionContents.USER_TYPE, oldUser.getType());
                session.setAttribute(SessionContents.LOGIN_STATUS, Boolean.TRUE);
                return new UserInfo(oldUser.getName(), oldUser.getEmail());
            }
        }
        return null;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String containerId = (String) session.getAttribute(SessionContents.DOCKER_ID);
        if(containerId != null && containerId != "") {
            DockerClientService.stopContainer(containerId);
            DockerClientService.removeContainer(containerId);
        }
        session.invalidate();
        return "success";
    }

    @ToString
    @Getter
    @Setter
    static private class UserWithPass {
        private String email;
        private String newPass;
        private String password;
    }

    @ToString
    @Getter
    @Setter
    static private class UserWithValidate {
        private String name;
        private String password;
        private String validate;
    }
}
