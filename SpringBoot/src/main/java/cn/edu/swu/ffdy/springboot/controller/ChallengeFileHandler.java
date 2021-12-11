package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.ChallengeFile;
import cn.edu.swu.ffdy.springboot.repository.ChallengeFileRepository;
import cn.edu.swu.ffdy.springboot.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/challengefile")
public class ChallengeFileHandler {
    @Autowired
    ChallengeFileRepository challengeFileRepository;

    @GetMapping("findAll")
    public List<ChallengeFile> findAll() {
        return challengeFileRepository.findAll();
    }
}
