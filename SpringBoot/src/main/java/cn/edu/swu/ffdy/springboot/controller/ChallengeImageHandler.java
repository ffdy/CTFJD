package cn.edu.swu.ffdy.springboot.controller;

import cn.edu.swu.ffdy.springboot.entity.ChallengeImage;
import cn.edu.swu.ffdy.springboot.repository.ChallengeImageRepository;
import cn.edu.swu.ffdy.springboot.utils.DockerClientService;
import cn.edu.swu.ffdy.springboot.utils.SessionContents;
import com.github.dockerjava.api.command.CreateContainerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.DatagramSocket;
import java.net.SocketException;

@RestController
@RequestMapping("/docker")
public class ChallengeImageHandler {
    @Autowired
    ChallengeImageRepository challengeImageRepository;

    private final String dockerUrlHttp = "http://172.18.19.141:";
    private final String dockerUrlDirect = "nc 172.18.19.141 ";

    @GetMapping("/geturl/{challengeId}")
    public String CreateImage(@PathVariable("challengeId") Integer challengeId, HttpServletRequest request) throws SocketException {
        HttpSession session = request.getSession(true);
        String containerId = (String) session.getAttribute(SessionContents.DOCKER_ID);
        if(containerId != null && containerId != "") {
            DockerClientService.stopContainer(containerId);
            DockerClientService.removeContainer(containerId);
        }

        ChallengeImage challengeImage = challengeImageRepository.findById(challengeId).get();
        Integer port = (Integer) session.getAttribute(SessionContents.EXPOSE_PORT);
        if(port == null || port == 0) {
            port = (new DatagramSocket(0)).getLocalPort();
            session.setAttribute(SessionContents.EXPOSE_PORT, port);
        }

        CreateContainerResponse container = DockerClientService.createContainers(challengeImage.getDockerImage(), challengeImage.getRedirectPort(), port);
        DockerClientService.startContainer(container.getId());
        session.setAttribute(SessionContents.DOCKER_ID, container.getId());
//        session.setAttribute(SessionContents.DYNAMIC_FLAG, );
        if(challengeImage.getRedirectType().equals("direct"))
            return dockerUrlDirect + port;
        return dockerUrlHttp + port;
    }

    @GetMapping("/restart")
    public String RestartImage(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String containerId = (String) session.getAttribute(SessionContents.DOCKER_ID);
        if(containerId != null) {
            DockerClientService.restartContainer(containerId);
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
            return "success";
        }
        return "fail";
    }
}
