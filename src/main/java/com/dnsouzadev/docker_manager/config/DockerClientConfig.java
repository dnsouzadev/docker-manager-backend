package com.dnsouzadev.docker_manager.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerClientConfig {

    @Value("${docker.socket.path}")
    private String dockerSocketPath;

    @Bean
    public DockerClient buildDockerClient() {
        DefaultDockerClientConfig.Builder dockerClientBuilder = DefaultDockerClientConfig
                .createDefaultConfigBuilder();

        if (this.dockerSocketPath != null && this.dockerSocketPath.startsWith("unix://")) {
            dockerClientBuilder.withDockerHost(this.dockerSocketPath)
                    .withDockerTlsVerify(false);
        }

        DefaultDockerClientConfig dockerClientConfig = dockerClientBuilder.build();

        ApacheDockerHttpClient dockerHttpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .build();

        return DockerClientBuilder.getInstance(dockerClientConfig)
                .withDockerHttpClient(dockerHttpClient)
                .build();
    }

}
