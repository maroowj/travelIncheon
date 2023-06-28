package com.field.muzi.repository;

import com.field.muzi.domain.entity.DiyEntity;
import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.web.admin.dto.banner.BannerListResponse;
import com.field.muzi.web.user.dto.diy.DiyDetailsResponse;
import com.field.muzi.web.user.dto.diy.DiyListResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.field.muzi.domain.entity.QBannerEntity.bannerEntity;
import static com.field.muzi.domain.entity.QDiyEntity.diyEntity;
import static com.field.muzi.domain.entity.QSecondCourseEntity.secondCourseEntity;

@Repository
public class DiyQueryRepositoryImpl extends QuerydslRepositorySupport implements DiyQueryRepository {
    private final JPAQueryFactory queryFactory;

    public DiyQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(DiyEntity.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<DiyListResponse> diyListByMember(Pageable pageable, MemberEntity member) {
        JPAQuery<DiyListResponse> query = queryFactory.select(Projections.constructor(DiyListResponse.class,
                        diyEntity.diySeq,
                        diyEntity.diyTitle,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", diyEntity.startDate),
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", diyEntity.endDate),
                        diyEntity.content,
                        diyEntity.etc
                ))
                .from(
                        diyEntity
                )
                .where(diyEntity.member.eq(member))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(diyEntity.getType(), diyEntity.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        QueryResults<DiyListResponse> result = query.fetchResults();

        return new PageImpl(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public DiyDetailsResponse.Diy diy(DiyEntity diy) {
        return queryFactory.select(Projections.constructor(DiyDetailsResponse.Diy.class,
                        diyEntity.diySeq,
                        diyEntity.diyTitle,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", diyEntity.startDate),
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", diyEntity.endDate),
                        diyEntity.content,
                        diyEntity.etc
                ))
                .from(
                        diyEntity
                )
                .where(diyEntity.eq(diy))
                .fetchOne();
    }
}
