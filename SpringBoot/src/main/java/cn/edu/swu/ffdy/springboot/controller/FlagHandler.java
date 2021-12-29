package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.Flag;
import cn.edu.swu.ffdy.springboot.entity.Solve;
import cn.edu.swu.ffdy.springboot.entity.Submission;
import cn.edu.swu.ffdy.springboot.repository.FlagRepository;
import cn.edu.swu.ffdy.springboot.repository.SolveRepository;
import cn.edu.swu.ffdy.springboot.repository.SubmissionRepository;
import cn.edu.swu.ffdy.springboot.utils.SessionContents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

@RestController
@RequestMapping("/api/flag")
public class FlagHandler {
    @Autowired
    FlagRepository flagRepository;
    @Autowired
    SolveRepository solveRepository;
    @Autowired
    SubmissionRepository submissionRepository;

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

        // 去掉多余的等号
        flag = flag.substring(0,flag.length()-1);
        String flagStatus;

        HttpSession session = request.getSession(true);
        if(currentFlag!=null && currentFlag.getContent().equals(flag)) {
            flagStatus = "correct";
        } else {
            flagStatus = "incorrect";
        }

        Submission submission = new Submission();
        submission.setChallengeId(id);
        submission.setDate(new Date());
        submission.setUserId((Integer) session.getAttribute(SessionContents.ID));
        submission.setProvided(flag);
        submission.setIp(request.getRemoteAddr());
        submission.setType(flagStatus);
        submissionRepository.save(submission);
        System.out.println(submission);

        Integer userId = (Integer) session.getAttribute(SessionContents.ID);
        Solve solve = solveRepository.findByChallengeIdAndUserId(id, userId);
        if(solve==null) {
            Solve newSolve = new Solve();
            newSolve.setId(submission.getId());
            newSolve.setChallengeId(id);
            newSolve.setUserId(userId);
            solveRepository.save(newSolve);
        }
        return flagStatus;
    }
}
