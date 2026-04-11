package com.harivardhan.marketpulse.model;

import java.time.Instant;

public record Tick(String symbol, double price, Instant timeStamp){}