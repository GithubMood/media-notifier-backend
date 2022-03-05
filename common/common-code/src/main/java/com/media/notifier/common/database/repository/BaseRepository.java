package com.media.notifier.common.database.repository;

import com.media.notifier.common.exception.DbResultNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
    T findByIdMandatory(ID id) throws DbResultNotFoundException;

    void clear();

    void detach(T entity);
}
