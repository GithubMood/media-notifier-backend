package com.media.notifier.auth.impl.domain.integration.db.repository.jpa;

import com.media.notifier.auth.impl.domain.integration.db.entity.QUserEntity;
import com.media.notifier.auth.impl.domain.integration.db.entity.UserEntity;
import com.media.notifier.auth.impl.domain.integration.db.repository.UserRepository;
import com.media.notifier.common.database.repository.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<UserEntity, Long> implements UserRepository {
    private final QUserEntity user = QUserEntity.userEntity;

    public UserRepositoryImpl(EntityManager em) {
        super(UserEntity.class, em);
    }

    @Override
    public Optional<UserEntity> findUser(String login, String password) {
        return Optional.ofNullable(queryFactory
                .from(user)
                .select(user)
                .where(user.login.eq(login).and(user.password.eq(password)))
                .fetchFirst());
    }
}
