package com.field.muzi.repository;

import com.field.muzi.domain.entity.CompanyEntity;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyQueryRepositoryImpl extends QuerydslRepositorySupport implements CompanyQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CompanyQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(CompanyEntity.class);
        this.queryFactory = queryFactory;
    }

//    @Override
//    public List<CompanyListResponse> userFirstCourseList() {
//
//        return queryFactory.select(Projections.constructor(AllCourseListResponse.FirstCourse.class,
//                        companyEntity.companySeq
//                        ,companyEntity.
//                ))
//                .from(firstCourseEntity)
//                .where(
//                        firstCourseEntity.withdrawal.isFalse(),
//                )
//                .fetch();
//    }

    private StringExpression replaceUrlId(StringExpression colName, String value) {
        return Expressions.stringTemplate("replace({0}, '[id]', {1})", colName, value);
    }
}
