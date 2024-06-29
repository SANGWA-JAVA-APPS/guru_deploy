/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.deploy.conf;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 * @author SANGWA, whatsapp +250784113888
 */
@Configuration
@EnableAsync
public class AsynchronousConf {

    @Bean("asynchrExecutor")
    public Executor asynchrExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setQueueCapacity(150);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setThreadNamePrefix("AsyncTaskThread");
        taskExecutor.initialize();
        return taskExecutor;

    }
}
