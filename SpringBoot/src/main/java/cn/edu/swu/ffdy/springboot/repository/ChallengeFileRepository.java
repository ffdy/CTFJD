package cn.edu.swu.ffdy.springboot.repository;

import cn.edu.swu.ffdy.springboot.entity.ChallengeFile;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeFileRepository extends JpaRepository<ChallengeFile, Integer> {
    List<ChallengeFile> findAllByChallengeId(Integer challengeId);

    ChallengeFile findByChallengeIdAndName(Integer challengeId, String name);
}
