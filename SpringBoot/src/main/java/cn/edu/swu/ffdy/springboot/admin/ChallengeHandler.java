package cn.edu.swu.ffdy.springboot.admin;

import cn.edu.swu.ffdy.springboot.entity.Challenge;
import cn.edu.swu.ffdy.springboot.entity.ChallengeImage;
import cn.edu.swu.ffdy.springboot.repository.ChallengeFileRepository;
import cn.edu.swu.ffdy.springboot.repository.ChallengeImageRepository;
import cn.edu.swu.ffdy.springboot.repository.ChallengeRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/challenge")
public class ChallengeHandler {
    ChallengeRepository challengeRepository;
    ChallengeFileRepository challengeFileRepository;
    ChallengeImageRepository challengeImageRepository;

    @Autowired
    ChallengeHandler(ChallengeRepository challengeRepository, ChallengeFileRepository challengeFileRepository, ChallengeImageRepository challengeImageRepository) {
        this.challengeFileRepository = challengeFileRepository;
        this.challengeRepository = challengeRepository;
        this.challengeImageRepository = challengeImageRepository;
    }

    @PostMapping("/edit")
    public String editChallenge(@RequestBody ChallengeWithInfo challengeWithInfo) {
        if(challengeWithInfo != null) {
            Challenge newChallenge = new Challenge();
            if(challengeWithInfo.getId() != null) {
                newChallenge.setId(challengeWithInfo.getId());
            }
            newChallenge.setCategory(challengeWithInfo.getCategory());
            newChallenge.setDescription(challengeWithInfo.getDescription());
            newChallenge.setName(challengeWithInfo.getName());
            newChallenge.setValue(challengeWithInfo.getValue());
            newChallenge.setState(challengeWithInfo.getState());
            newChallenge.setType(challengeWithInfo.getType());
            newChallenge = challengeRepository.save(newChallenge);

            if(challengeWithInfo.getType().equals("dynamic_docker")) {
                ChallengeImage challengeImage = challengeImageRepository.findById(newChallenge.getId()).orElse(null);
                if(challengeImage == null) {
                    challengeImage =  new ChallengeImage();
                }
                challengeImage.setDockerImage(challengeWithInfo.getDockerImage());
                challengeImage.setRedirectType(challengeWithInfo.getRedirectType());
                challengeImage.setRedirectPort(challengeWithInfo.getRedirectPort());
                challengeImageRepository.save(challengeImage);
            }

            return "success";
        }
        return "error";
    }

    @GetMapping("/delete/{challengeId}")
    public String deleteChallenge(@PathVariable("challengeId") Integer challengeId) {
        if(challengeId != null) {
            challengeRepository.deleteById(challengeId);
            challengeImageRepository.deleteById(challengeId);
            challengeFileRepository.deleteAllByChallengeId(challengeId);
            return "success";
        }
        return "error";
    }

    @ToString
    @Setter
    @Getter
    private static class ChallengeWithInfo extends Challenge {
        private String dockerImage;
        private String redirectType;
        private Integer redirectPort;
    }
}