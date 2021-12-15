package cn.edu.swu.ffdy.springboot.repository;

import cn.edu.swu.ffdy.springboot.entity.User;
import cn.edu.swu.ffdy.springboot.utils.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<User, Integer> {
    @Query(value = "select new cn.edu.swu.ffdy.springboot.entity.UserInfo(u.name, u.email) from User u")
    Page<UserInfo> findAllUserInfo(PageRequest pageRequest);

    User findByName(String name);
    User findByEmail(String email);
}
