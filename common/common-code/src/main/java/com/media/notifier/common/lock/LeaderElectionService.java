package com.media.notifier.common.lock;

public interface LeaderElectionService {
    boolean isCurrentLeader();
}
