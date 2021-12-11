package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.Submission;
import cn.edu.swu.ffdy.springboot.repository.SubmissionRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/submission")
public class SubmissionHandler {
    @Autowired
    SubmissionRepository submissionRepository;

    @GetMapping("/findAll")
    public List<Submission> findAll() {
        return submissionRepository.findAll();
    }
}
