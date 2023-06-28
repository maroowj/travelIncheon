package com.field.muzi.repository;

import com.field.muzi.domain.entity.MemberInfoEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberInfoQueryRepositoryImpl extends QuerydslRepositorySupport implements MemberInfoQueryRepository {
    private final JPAQueryFactory queryFactory;

    public MemberInfoQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(MemberInfoEntity.class);
        this.queryFactory = queryFactory;
    }

}
