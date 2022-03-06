package com.media.notifier.common.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
class DistributedLockProviderImpl implements DistributedLockProvider {
    private final RedissonClient redissonClient;

    @Override
    public boolean tryLockForTime(String key, int leaseTime, TimeUnit timeUnit) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(0, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Thread is interrupted: ", e);
            return false;
        }
    }
}
