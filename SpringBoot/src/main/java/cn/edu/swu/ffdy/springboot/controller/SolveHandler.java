package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.Solve;
import cn.edu.swu.ffdy.springboot.repository.SolveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/solve")
public class SolveHandler {
    @Autowired
    SolveRepository solveRepository;

    @GetMapping("/findAll")
    public List<Solve> findAll() {
        return solveRepository.findAll();
    }
}
