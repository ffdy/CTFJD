package cn.edu.swu.ffdy.springboot.repository;

import cn.edu.swu.ffdy.springboot.entity.Solve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolveRepository extends JpaRepository<Solve, Integer> {
    Solve findByChallengeIdAndUserId(Integer ChallengeId, Integer userId);

    List<Solve> findByUserId(Integer userId);
    List<Solve> findByChallengeId(Integer challengeid);
}
