package cn.edu.swu.ffdy.springboot.admin;

import cn.edu.swu.ffdy.springboot.entity.User;
import cn.edu.swu.ffdy.springboot.repository.UsersRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin/api/user")
public class UserHandler {
    UsersRepository usersRepository;

    @Autowired
    UserHandler(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/all")
    public List<User> getAllUser() {
        return usersRepository.findAll();
    }

    @PostMapping("/edit")
    public String editUser(@RequestBody UserWithPass userWithPass) {
        User oldUser = usersRepository.findById(userWithPass.getId()).orElse(null);
        if(oldUser == null) {
            oldUser = new User();
            oldUser.setName(userWithPass.getName());
            oldUser.setType("user");
            oldUser.setCreated(new Date());
        }
        if (userWithPass.getEmail() != null && !userWithPass.getEmail().equals("")) {
            oldUser.setEmail(userWithPass.getEmail());
        }
        if (userWithPass.getPassword() != null && !userWithPass.getPassword().equals("")) {
            oldUser.setPassword(userWithPass.getPassword());
        }
        usersRepository.save(oldUser);
        return "success";
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId) {
        User oldUser = usersRepository.findById(userId).orElse(null);
        if(oldUser != null) {
            usersRepository.deleteById(userId);
            return "success";
        }
        return "error";
    }

    @ToString
    @Getter
    @Setter
    static private class UserWithPass {
        private Integer id;
        private String name;
        private String password;
        private String email;
    }
}
