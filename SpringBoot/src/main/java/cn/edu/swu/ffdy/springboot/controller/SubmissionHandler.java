package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.Submission;
import cn.edu.swu.ffdy.springboot.repository.SubmissionRepository;
import cn.edu.swu.ffdy.springboot.utils.SessionContents;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/mysubmission")
    public List<Submission> mySubmission(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Integer userId = (Integer) session.getAttribute(SessionContents.ID);
        return submissionRepository.findByUserId(userId);
    }
}
