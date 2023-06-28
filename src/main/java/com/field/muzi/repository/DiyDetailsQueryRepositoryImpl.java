package com.field.muzi.repository;

import com.field.muzi.domain.entity.DiyDetailsEntity;
import com.field.muzi.domain.entity.DiyEntity;
import com.field.muzi.web.user.dto.diy.DiyDetailsResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.field.muzi.domain.entity.QDiyDetailsEntity.diyDetailsEntity;

@Repository
public class DiyDetailsQueryRepositoryImpl extends QuerydslRepositorySupport implements DiyDetailsQueryRepository {
    private final JPAQueryFactory queryFactory;

    public DiyDetailsQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(DiyDetailsEntity.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<DiyDetailsResponse.Details> diyDetails(DiyEntity diy) {
        return queryFactory.select(Projections.constructor(DiyDetailsResponse.Details.class,
                    diyDetailsEntity.content,
                    diyDetailsEntity.etc
                ))
                .from(diyDetailsEntity)
                .where(diyDetailsEntity.diy.eq(diy))
                .fetch();
    }
}
