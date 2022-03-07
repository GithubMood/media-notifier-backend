package com.media.notifier.auth.impl.domain.integration.db.repository;

import com.media.notifier.auth.impl.domain.integration.db.entity.UserEntity;
import com.media.notifier.common.database.repository.BaseRepository;

import java.util.Optional;

public interface UserRepository extends BaseRepository<UserEntity, Long> {
    Optional<UserEntity> findUser(String login, String password);
}
