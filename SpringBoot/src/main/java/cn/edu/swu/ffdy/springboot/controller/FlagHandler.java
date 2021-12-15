package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.Flag;
import cn.edu.swu.ffdy.springboot.repository.FlagRepository;
import cn.edu.swu.ffdy.springboot.repository.SolveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
@RequestMapping("/flag")
public class FlagHandler {
    @Autowired
    FlagRepository flagRepository;
    @Autowired
    SolveRepository solveRepository;

    @PostMapping("/check/{challengeId}")
    public String checkFlag(@PathVariable("challengeId") Integer id,
                            @RequestBody String flag,
                            HttpServletRequest request) {
        Flag currentFlag = flagRepository.findByChallengeId(id);
        try {
            flag = URLDecoder.decode(flag, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(currentFlag!=null && (currentFlag.getContent()+'=').equals(flag)) {
            HttpSession session = request.getSession(true)
            return "flag正确";
        } else {
            return "flag错误"+flag;
        }
    }
}
