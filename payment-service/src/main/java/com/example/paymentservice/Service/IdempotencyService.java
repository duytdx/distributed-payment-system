package com.example.paymentservice.Service;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class IdempotencyService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String PREFIX = "payment:idempotency:";
    private static final Duration EXPIRATION = Duration.ofMinutes(24);

    public IdempotencyService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isDuplicate(String orderId) {
        String key = PREFIX + orderId;
        Boolean exists = redisTemplate.hasKey(key);
        return exists != null && exists;
    }

    public void markDone(String orderId) {
        String key = PREFIX + orderId;
        redisTemplate.opsForValue().set(key, "done", EXPIRATION);
    }
}
