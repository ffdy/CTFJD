package cn.edu.swu.ffdy.springboot.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.RestartContainerCmd;
import org.junit.jupiter.api.Test;

import javax.print.Doc;
import java.net.SocketException;

import static org.junit.jupiter.api.Assertions.*;

class DockerClientServiceTest {
    @Test
    public void testConnect() {
        DockerClient client = DockerClientService.connectDocker();
        System.out.println(client.listImagesCmd().exec());
    }

    @Test
    public void createImage() throws SocketException {
        CreateContainerResponse image = DockerClientService.createContainers("swuctf/web1", 80, 5555);
        System.out.println(image);
        DockerClientService.startContainer(image.getId());
    }

    @Test
    public void startImage() {
        DockerClientService.startContainer("457a6d45377e");
    }

    @Test
    public void restartImage() {
        DockerClientService.restartContainer("cf2361abea82");
    }

    @Test
    public void stopImage() {
        DockerClientService.stopContainer("cf2361abea82");
        DockerClientService.removeContainer("cf2361abea82");
    }
}