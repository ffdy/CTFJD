package cn.edu.swu.ffdy.springboot.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import org.junit.jupiter.api.Test;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

class DockerClientServiceTest {
    @Test
    public void testConnect() {
        DockerClient client = DockerClientService.connectDocker();
        System.out.println(client.listImagesCmd().exec());
    }
//
    @Test
    public void createImage() throws SocketException {

        CreateContainerResponse image = DockerClientService
                .createContainers("mysql", 3306, 3307, "MYSQL_ROOT_PASSWORD=password");
        System.out.println(image);
        DockerClientService.startContainer(image.getId());
    }

    @Test
    public void createImageWithEnvList() throws SocketException {
        List<String> envList = new ArrayList<>();
        envList.add("MYSQL_ROOT_PASSWORD=password");
        envList.add("FLAG=q1234567");
        System.out.println(envList);
        CreateContainerResponse image = DockerClientService
                .createContainers("mysql", 3306, 3307, envList);
        System.out.println(image);
        DockerClientService.startContainer(image.getId());
    }
}