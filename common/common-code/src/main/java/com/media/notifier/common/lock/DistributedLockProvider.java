package com.media.notifier.common.lock;

import java.util.concurrent.TimeUnit;

public interface DistributedLockProvider {
    boolean tryLockForTime(String key, int leaseTime, TimeUnit timeUnit);
}
