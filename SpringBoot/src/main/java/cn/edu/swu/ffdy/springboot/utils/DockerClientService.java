package cn.edu.swu.ffdy.springboot.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

public class DockerClientService {
    /**
     * 连接docker服务器
     * @return
     */
    public static DockerClient connectDocker(){
        DockerClient dockerClient = DockerClientBuilder.getInstance("tcp://172.18.19.141:2375").build();
        Info info = dockerClient.infoCmd().exec();
        System.out.println("docker的环境信息如下：=================");
        System.out.println(info);
        return dockerClient;
    }

    /**
     * 创建容器
     * @return
     */
    public static CreateContainerResponse createContainers(String imageName, Integer redirectPort) throws SocketException {
        //映射端口8088—>80
        int port = (new DatagramSocket(0)).getLocalPort();
        ExposedPort tcp80 = ExposedPort.tcp(redirectPort);
        Ports portBindings = new Ports();
        portBindings.bind(tcp80, Ports.Binding.bindPort(port));

        DockerClient client = connectDocker();
        CreateContainerResponse container = client.createContainerCmd(imageName)
                .withHostConfig(newHostConfig().withPortBindings(portBindings))
                .withExposedPorts(tcp80).exec();
        return container;
    }


    /**
     * 启动容器
     * @param containerId
     */
    public static void startContainer(String containerId){
        DockerClient client = connectDocker();
        client.startContainerCmd(containerId).exec();
    }

    /**
     * 启动容器
     * @param containerId
     */
    public static void stopContainer(String containerId){
        DockerClient client = connectDocker();
        client.stopContainerCmd(containerId).exec();
    }

    /**
     * 删除容器
     * @param containerId
     */
    public static void removeContainer(String containerId){
        DockerClient client = connectDocker();
        client.removeContainerCmd(containerId).exec();
    }

}
