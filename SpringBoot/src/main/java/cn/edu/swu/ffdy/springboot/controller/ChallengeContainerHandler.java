package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.ChallengeContainer;
import cn.edu.swu.ffdy.springboot.entity.ChallengeImage;
import cn.edu.swu.ffdy.springboot.repository.ChallengeContainerRepository;
import cn.edu.swu.ffdy.springboot.repository.ChallengeImageRepository;
import cn.edu.swu.ffdy.springboot.utils.DockerClientService;
import cn.edu.swu.ffdy.springboot.utils.SessionContents;
import com.github.dockerjava.api.command.CreateContainerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/docker")
public class ChallengeContainerHandler {
    @Autowired
    ChallengeImageRepository challengeImageRepository;
    @Autowired
    ChallengeContainerRepository challengeContainerRepository;

    /**
     * create and start container，build random flag and save to session
     */
    @GetMapping("/geturl/{challengeId}")
    public String CreateImage(@PathVariable("challengeId") Integer challengeId, HttpServletRequest request) throws SocketException {
        HttpSession session = request.getSession(true);
        String containerId = (String) session.getAttribute(SessionContents.DOCKER_ID);
        if(containerId != null && !containerId.equals("")) {
            DockerClientService.stopContainer(containerId);
            DockerClientService.removeContainer(containerId);

            // docker container tracking
            ChallengeContainer challengeContainer = challengeContainerRepository.findByContainerId(containerId);
            challengeContainer.setTime(new Date());
            challengeContainerRepository.save(challengeContainer);
        }

        ChallengeImage challengeImage = challengeImageRepository.findById(challengeId).orElse(null);
        Integer port = (Integer) session.getAttribute(SessionContents.EXPOSE_PORT);
        if(port == null || port == 0) {
            port = (new DatagramSocket(0)).getLocalPort();
            session.setAttribute(SessionContents.EXPOSE_PORT, port);
        }

        // random flag
        String dynamicFlag = "flag{" + UUID.randomUUID() + "}";

        assert challengeImage != null;
        CreateContainerResponse container =
                DockerClientService.createContainers(
                        challengeImage.getDockerImage(),
                        challengeImage.getRedirectPort(),
                        port,
                        "FLAG=" + dynamicFlag
                );
        DockerClientService.startContainer(container.getId());
        session.setAttribute(SessionContents.DOCKER_ID, container.getId());
        session.setAttribute(SessionContents.DYNAMIC_FLAG, dynamicFlag);

        // docker container tracking
        ChallengeContainer challengeContainer = new ChallengeContainer();
        challengeContainer.setChallengeId(challengeId);
        challengeContainer.setFlag(dynamicFlag);
        challengeContainer.setStatus("Running");
        challengeContainer.setTime(new Date());
        challengeContainer.setUsername((String) session.getAttribute(SessionContents.USER_NAME));
        challengeContainer.setContainerId(container.getId());
        challengeContainerRepository.save(challengeContainer);

        String dockerUrlDirect = "nc 172.18.19.141 ";
        if(challengeImage.getRedirectType().equals("direct"))
            return dockerUrlDirect + port;
        String dockerUrlHttp = "http://172.18.19.141:";
        return dockerUrlHttp + port;
    }

    @GetMapping("/restart")
    public String RestartImage(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String containerId = (String) session.getAttribute(SessionContents.DOCKER_ID);
        if(containerId != null) {
            DockerClientService.restartContainer(containerId);

            // docker container tracking
            ChallengeContainer challengeContainer = challengeContainerRepository.findByContainerId(containerId);
            challengeContainer.setTime(new Date());
            challengeContainerRepository.save(challengeContainer);

            return "success";
        }
        return "fail";
    }

    @GetMapping("/stop")
    public String StopImage(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String containerId = (String) session.getAttribute(SessionContents.DOCKER_ID);
        if(containerId != null) {
            DockerClientService.stopContainer(containerId);
            DockerClientService.removeContainer(containerId);
            session.setAttribute(SessionContents.DOCKER_ID, null);

            // docker container tracking
            ChallengeContainer challengeContainer = challengeContainerRepository.findByContainerId(containerId);
            challengeContainer.setStatus("Destroyed");
            challengeContainerRepository.save(challengeContainer);

            return "success";
        }
        return "fail";
    }
}
