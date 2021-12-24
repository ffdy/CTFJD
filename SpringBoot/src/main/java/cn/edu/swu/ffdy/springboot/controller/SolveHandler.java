package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.Solve;
import cn.edu.swu.ffdy.springboot.entity.User;
import cn.edu.swu.ffdy.springboot.repository.ChallengeRepository;
import cn.edu.swu.ffdy.springboot.repository.SolveRepository;
import cn.edu.swu.ffdy.springboot.repository.UsersRepository;
import cn.edu.swu.ffdy.springboot.utils.SessionContents;
import cn.edu.swu.ffdy.springboot.utils.UserInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/solve")
public class SolveHandler {
    @Autowired
    SolveRepository solveRepository;
    @Autowired
    ChallengeRepository challengeRepository;
    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/findAll")
    public List<Solve> findAll() {
        return solveRepository.findAll();
    }

    @GetMapping("/myscore")
    public Integer findMyScore(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Integer userId = (Integer) session.getAttribute(SessionContents.ID);
        if (userId == null)
            return null;
        return findByUserId(userId);
    }

    @GetMapping("/allscores")
    public List<UserScore> findAllScores() {
        List<UserScore> userScores = new ArrayList<>();
        List<User> users = usersRepository.findAll();
        for (User user : users) {
            Integer score = findByUserId(user.getId());
            if (score != 0) {
                UserScore userScore = new UserScore(user.getName(), score);
                System.out.println(userScore);
                userScores.add(userScore);
            }
        }
        userScores.sort((o1, o2) -> o2.getScore().compareTo(o1.getScore()));
        return userScores;
    }

    private Integer findByUserId(Integer userId) {
        Integer score = 0;
        List<Solve> solveList = solveRepository.findByUserId(userId);
        for (Solve solve : solveList) {
            try {
                score += challengeRepository.findById(solve.getChallengeId()).get().getValue();
            } catch (Exception ignored) {
            }
        }
        return score;
    }

    @GetMapping("/findbychallengeid/{challengeid}")
    public List<UserInfo> findByChallengeId(@PathVariable("challengeid") Integer challengeId) {
        List<Solve> solveList = solveRepository.findByChallengeId(challengeId);
        List<UserInfo> userInfos = new ArrayList<>();
        for(Solve solve : solveList) {
            User user = usersRepository.findById(solve.getUserId()).get();
            userInfos.add(new UserInfo(user.getName(), user.getEmail()));
        }
        return userInfos;
    }

    @Getter
    @Setter
    @ToString
    private  class UserScore {
        private final String username;
        private final Integer score;

        public UserScore(String username, Integer score) {
            this.username = username;
            this.score = score;
        }
    }
}
