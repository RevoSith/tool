package com.revosith.executor;

import com.revosith.constant.NumConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Revosith
 * @description
 * @date 2021/9/18
 **/

@EnableAsync
public class ExecutorConf {

    /**
     * 全局线程池配置
     *
     * @return
     */
    @Bean
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(NumConstant.NUM_16);
        executor.setMaxPoolSize(NumConstant.NUM_32);
        executor.setQueueCapacity(NumConstant.NUM_16);
        executor.setKeepAliveSeconds(NumConstant.NUM_256);
        executor.setThreadNamePrefix("biz-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setAllowCoreThreadTimeOut(true);
        return executor;
    }
}