package cn.edu.swu.ffdy.springboot.utils;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import java.net.SocketException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;

public class DockerClientService {
    /**
     * 连接docker服务器
     */
    public static DockerClient connectDocker(){
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerTlsVerify(false)
//                .withDockerHost("tcp://localhost:2375")
                 .withDockerHost("unix://var/run/docker.sock")
//                .withApiVersion("1.41")
//                .withRegistryUrl("https://index.docker.io/v1/")
                .build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        return DockerClientImpl.getInstance(config, httpClient);
    }

    /**
     * 创建容器
     */
    public static CreateContainerResponse createContainers(String imageName, Integer redirectPort, Integer port, List<String> envList) throws SocketException {
        ExposedPort exposedPort = ExposedPort.tcp(redirectPort);
        Ports portBindings = new Ports();
        portBindings.bind(exposedPort, Ports.Binding.bindPort(port));

        return  connectDocker().createContainerCmd(imageName)
                .withHostConfig(newHostConfig().withPortBindings(portBindings))
                .withExposedPorts(exposedPort)
                .withEnv(envList).exec();
    }

    public static CreateContainerResponse createContainers(String imageName, Integer redirectPort, Integer port, String env) throws SocketException {
        List<String> envList = new ArrayList<>();
        envList.add(env);
        return createContainers(imageName, redirectPort, port, envList);
    }

    public static CreateContainerResponse createContainers(String imageName, Integer redirectPort, Integer port) throws SocketException {
        return createContainers(imageName, redirectPort, port, (List<String>) null);
    }

    /**
     * 启动容器
     */
    public static void startContainer(String containerId){
        DockerClient client = connectDocker();
        client.startContainerCmd(containerId).exec();
    }

    /**
     * 重启容器
     */
    public static void restartContainer(String containerId){
        DockerClient client = connectDocker();
        client.restartContainerCmd(containerId).exec();
    }

    /**
     * 停止容器
     */
    public static void stopContainer(String containerId){
        DockerClient client = connectDocker();
        client.stopContainerCmd(containerId).exec();
    }

    /**
     * 删除容器
     */
    public static void removeContainer(String containerId){
        DockerClient client = connectDocker();
        client.removeContainerCmd(containerId).exec();
    }

}
