package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.Flag;
import cn.edu.swu.ffdy.springboot.repository.FlagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flag")
public class FlagHandler {
    @Autowired
    FlagRepository flagRepository;

    @GetMapping("/findAll")
    public List<Flag> findAll() {
        return flagRepository.findAll();
    }
}
