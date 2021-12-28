package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.ChallengeImage;
import cn.edu.swu.ffdy.springboot.repository.ChallengeImageRepository;
import cn.edu.swu.ffdy.springboot.utils.DockerClientService;
import com.github.dockerjava.api.command.CreateContainerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.SocketException;

@RestController
@RequestMapping("/docker")
public class ChallengeImageHandler {
    @Autowired
    ChallengeImageRepository challengeImageRepository;

    @GetMapping("/geturl/{challengeId}")
    public String CreateImage(@PathVariable("challengeId") Integer challengeId) throws SocketException {
        ChallengeImage challengeImage = challengeImageRepository.findById(challengeId).get();
        CreateContainerResponse container = DockerClientService.createContainers(challengeImage.getDockerImage(), challengeImage.getRedirectPort());
        return container.getId();
    }
}
