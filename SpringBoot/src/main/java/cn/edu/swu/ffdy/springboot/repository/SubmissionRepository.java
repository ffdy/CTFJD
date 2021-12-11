package cn.edu.swu.ffdy.springboot.repository;

import cn.edu.swu.ffdy.springboot.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
}
