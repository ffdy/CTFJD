package cn.edu.swu.ffdy.springboot.repository;

import cn.edu.swu.ffdy.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {
}
