package cn.edu.swu.ffdy.springboot.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
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
        CreateContainerResponse image = DockerClientService.createContainers("swuctf/web1", 80);
        System.out.println(image);
        DockerClientService.startContainer(image.getId());
    }

    @Test
    public void startImage() {
        DockerClientService.startContainer("457a6d45377e");
    }
}