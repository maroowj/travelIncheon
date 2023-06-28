package com.field.muzi.repository;

import com.field.muzi.domain.entity.MemberTypeEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class MemberTypeQueryRepositoryImpl extends QuerydslRepositorySupport implements MemberTypeQueryRepository {
    private final JPAQueryFactory queryFactory;

    public MemberTypeQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(MemberTypeEntity.class);
        this.queryFactory = queryFactory;
    }
}
