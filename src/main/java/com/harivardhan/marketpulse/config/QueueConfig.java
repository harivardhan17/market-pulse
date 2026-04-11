package com.harivardhan.marketpulse.config;

import com.harivardhan.marketpulse.model.Tick;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class QueueConfig {

    @Bean
    public BlockingQueue<Tick> tickQueue(){
        return new LinkedBlockingQueue<>(1000);
    }

}
