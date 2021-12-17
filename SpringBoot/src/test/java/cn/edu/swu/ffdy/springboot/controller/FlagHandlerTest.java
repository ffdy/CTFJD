package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.Flag;
import cn.edu.swu.ffdy.springboot.repository.FlagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FlagHandlerTest {
    @Autowired
    FlagRepository flagRepository;

    @Test
    public void check() {
//        int id=6;
//        Flag flag = flagRepository.findByChallengeId(id);
//        System.out.println(flag);
        String flags = "asdfasdf";
        System.out.println(flags);
        flags = flags.substring(0,flags.length()-1);
        System.out.println(flags);
    }
}