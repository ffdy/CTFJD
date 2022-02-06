package cn.edu.swu.ffdy.springboot.admin;

import cn.edu.swu.ffdy.springboot.entity.ChallengeContainer;
import cn.edu.swu.ffdy.springboot.repository.ChallengeContainerRepository;
import cn.edu.swu.ffdy.springboot.utils.DockerClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/api/container")
public class ChallengeContainerHandler {
    @Autowired
    ChallengeContainerRepository challengeContainerRepository;

    @GetMapping("/allcontainers")
    public List<ChallengeContainer> getAllContainer() {
        List<ChallengeContainer> challengeContainerList = challengeContainerRepository.findAll();
        challengeContainerList.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));
        return challengeContainerList;
    }

    @GetMapping("/restart/{containerId}")
    public String restarContainer(@PathVariable("containerId") Integer id) {
        ChallengeContainer challengeContainer = challengeContainerRepository.findById(id).orElse(null);

        if(challengeContainer != null) {
            String containerId = challengeContainer.getContainerId();
            if(containerId != null) {
                DockerClientService.restartContainer(containerId);
                return "success";
            }
        }
        return "error";
    }

    @GetMapping("/stop/{containerId}")
    public String stopContainer(@PathVariable("containerId") Integer id) {
        ChallengeContainer challengeContainer = challengeContainerRepository.findById(id).orElse(null);

        if(challengeContainer != null) {
            String containerId = challengeContainer.getContainerId();
            if(containerId != null) {
                DockerClientService.stopContainer(containerId);
                return "success";
            }
        }
        return "error";
    }
}
