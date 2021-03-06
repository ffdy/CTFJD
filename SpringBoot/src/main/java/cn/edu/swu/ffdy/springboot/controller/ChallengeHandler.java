package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.Challenge;
import cn.edu.swu.ffdy.springboot.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeHandler {
    ChallengeRepository challengeRepository;

    @Autowired
    ChallengeHandler(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @GetMapping("/findAll")
    public List<Challenge> findAll() {
        return challengeRepository.findAll();
    }
}
