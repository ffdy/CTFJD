package cn.edu.swu.ffdy.springboot.repository;

import cn.edu.swu.ffdy.springboot.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Integer> {
}
