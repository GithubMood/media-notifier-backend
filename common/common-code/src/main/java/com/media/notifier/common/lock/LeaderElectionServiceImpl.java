package com.media.notifier.common.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
class LeaderElectionServiceImpl implements LeaderElectionService {
    private static final int LEADER_ELECTION_INITIAL_DELAY = 0;
    private static final int LEADER_LOCK_EXPIRE_IN_SECONDS = 5;
    private static final int REFRESH_LOCK_PERIOD_SECONDS = 2;
    private static final String LEADER_LOCK = "SMARTOMICA_BACKEND_LEADER_LOCK";

    private final DistributedLockProvider distributedLockProvider;
    private volatile boolean isLeader = false;

    @PostConstruct
    void startLeaderElection() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(this::tryToAcquireLeaderLock,
                LEADER_ELECTION_INITIAL_DELAY,
                REFRESH_LOCK_PERIOD_SECONDS,
                TimeUnit.SECONDS);
    }

    private void tryToAcquireLeaderLock() {
        try {
            isLeader = distributedLockProvider.tryLockForTime(LEADER_LOCK, 5, TimeUnit.SECONDS);
            if (isLeader) {
                log.trace("Leader Lock is acquire for next {} seconds", LEADER_LOCK_EXPIRE_IN_SECONDS);
            } else {
                log.trace("Failed to acquire leader lock. Will try again in 2 seconds");
            }
        } catch (Exception e) {
            log.error("Unexpected exception during leader election", e);
        }
    }

    @Override
    public boolean isCurrentLeader() {
        return isLeader;
    }
}
