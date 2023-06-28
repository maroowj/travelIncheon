package com.field.muzi.repository;

import com.field.muzi.domain.entity.DiyDetailsEntity;
import com.field.muzi.domain.entity.DiyEntity;
import com.field.muzi.domain.entity.DiyOptionsEntity;
import com.field.muzi.web.user.dto.diy.DiyDetailsResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.field.muzi.domain.entity.QDiyDetailsEntity.diyDetailsEntity;
import static com.field.muzi.domain.entity.QDiyOptionsEntity.diyOptionsEntity;

@Repository
public class DiyOptionsQueryRepositoryImpl extends QuerydslRepositorySupport implements DiyOptionsQueryRepository {
    private final JPAQueryFactory queryFactory;

    public DiyOptionsQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(DiyOptionsEntity.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<DiyDetailsResponse.Options> diyOptions(DiyEntity diy) {
        return queryFactory.select(Projections.constructor(DiyDetailsResponse.Options.class,
                        diyOptionsEntity.content,
                        diyOptionsEntity.etc
                ))
                .from(diyOptionsEntity)
                .where(diyOptionsEntity.diy.eq(diy))
                .fetch();
    }
}
