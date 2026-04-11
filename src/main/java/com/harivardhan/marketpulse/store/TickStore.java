package com.harivardhan.marketpulse.store;

import com.harivardhan.marketpulse.model.Tick;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TickStore {

    private static final int WINDOW_SIZE = 100;
    private final ConcurrentMap<String, Deque<Tick>> store = new ConcurrentHashMap<>();

    public void add(Tick tick) {
        Deque<Tick> window = store.computeIfAbsent(tick.symbol(), k -> new ArrayDeque<>());
        synchronized (window){
            if(window.size() == WINDOW_SIZE){
                window.pollFirst();
            }
            window.addLast(tick);
        }
    }

    public List<Tick> getWindow(String symbol) {
    Deque<Tick> window = store.get(symbol);
    if(window == null){
        return List.of();
    }
    synchronized(window){
        return new ArrayList<>(window);
    }
    }

    public int size(String symbol) {
        Deque<Tick> window = store.get(symbol);
        if(window == null) {
            return 0;
        }
        synchronized(window) {
            return window.size();
        }
    }

}
