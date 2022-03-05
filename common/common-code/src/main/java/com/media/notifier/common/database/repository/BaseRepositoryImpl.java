package com.media.notifier.common.database.repository;

import com.media.notifier.common.exception.DbResultNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public abstract class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    private final EntityManager em;
    protected final JPAQueryFactory queryFactory;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public T findByIdMandatory(ID id) throws DbResultNotFoundException {
        return findById(id)
                .orElseThrow(() -> {
                    String errorMessage = String.format("Entity [%s] with id [%s] was not found in DB", getDomainClass().getSimpleName(), id);
                    return new DbResultNotFoundException(errorMessage);
                });
    }

    public void clear() {
        em.clear();
    }

    public void detach(T entity) {
        em.detach(entity);
    }
}
