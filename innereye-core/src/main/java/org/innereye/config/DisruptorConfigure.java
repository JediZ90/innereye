package org.innereye.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.lmax.disruptor.dsl.Disruptor;

@Component
public class DisruptorConfigure {

    @Bean(name = "disruptor")
    public Disruptor<?> disruptor() {
        Executor executor = Executors.newCachedThreadPool();
        return null;
    }
}
