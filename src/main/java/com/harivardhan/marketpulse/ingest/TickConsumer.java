package com.harivardhan.marketpulse.ingest;

import com.harivardhan.marketpulse.model.Tick;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
public class TickConsumer {

    private static final Logger log = LoggerFactory.getLogger(TickConsumer.class);

    private final BlockingQueue<Tick> tickQueue;
    private Thread worker;
    private volatile boolean running = true;

    public TickConsumer(BlockingQueue<Tick> tickQueue){
        this.tickQueue = tickQueue;

    }

    @PostConstruct
    public void start() {
        worker = new Thread(this::consumeLoop, "tick-consumer-1");
        worker.start();
        log.info("TickConsumer started");
    }

    private void consumeLoop(){
        while(running) {
            try {
                Tick tick = tickQueue.take();
                log.info("Consumed tick: {}", tick);
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
                log.info("Consumer thread interrupted, exiting");

            }
        }
    }

    @PreDestroy
    public void stop(){
        running = false;
        if(worker != null){
            worker.interrupt();
        }
        log.info("TickConsumer stopped");
    }


}
