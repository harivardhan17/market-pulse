package com.harivardhan.marketpulse.ingest;

import com.harivardhan.marketpulse.model.Tick;
import com.harivardhan.marketpulse.store.TickStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TickGenerator {
    private static final  Logger log = LoggerFactory.getLogger(TickGenerator.class);
    private static final  List<String> SYMBOLS = List.of("AAPL", "GOOGL", "TSLA", "MSFT", "AMZN");

    private final BlockingQueue<Tick> tickQueue;
    private final TickStore tickStore;

    public TickGenerator(BlockingQueue<Tick> tickQueue, TickStore tickStore){
        this.tickQueue = tickQueue;
        this.tickStore = tickStore;
    }

    @Scheduled(fixedRate = 500)
    public void generate() {
        String symbol = SYMBOLS.get(ThreadLocalRandom.current().nextInt(SYMBOLS.size()));
        double price = 100 + ThreadLocalRandom.current().nextDouble() * 900;

        Tick tick = new Tick(symbol, Math.round(price * 100.0)/100.0, Instant.now());
        log.info("Generated tick: {}", tick);

        try{
            tickQueue.put(tick);
            tickStore.add(tick);
            log.debug("Stored tick: {} (window.size: {})",tick, tickStore.size(tick.symbol()));
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
            log.info("Interrupted while putting tick", e);
        }
    }

}