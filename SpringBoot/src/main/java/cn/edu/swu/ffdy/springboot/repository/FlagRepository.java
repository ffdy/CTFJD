package cn.edu.swu.ffdy.springboot.repository;

import cn.edu.swu.ffdy.springboot.entity.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlagRepository extends JpaRepository<Flag, Integer> {
    Flag findByChallengeId(Integer id);
}
