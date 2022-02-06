package cn.edu.swu.ffdy.springboot.repository;

import cn.edu.swu.ffdy.springboot.entity.ChallengeContainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeContainerRepository extends JpaRepository<ChallengeContainer, Integer> {
    ChallengeContainer findByContainerId(String containerId);
}
