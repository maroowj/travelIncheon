package com.field.muzi.repository;

import com.field.muzi.domain.entity.DiyOptionsEntity;
import com.field.muzi.domain.entity.FormEntity;
import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.web.admin.dto.insurance.InsuranceListRequest;
import com.field.muzi.web.admin.dto.insurance.InsuranceListResponse;
import com.field.muzi.web.user.dto.diy.DiyListResponse;
import com.field.muzi.web.user.dto.form.FormListResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import static com.field.muzi.domain.entity.QDiyEntity.diyEntity;
import static com.field.muzi.domain.entity.QFormEntity.formEntity;
import static com.field.muzi.domain.entity.QInquiryEntity.inquiryEntity;

@Repository
public class FormQueryRepositoryImpl extends QuerydslRepositorySupport implements FormQueryRepository {
    private final JPAQueryFactory queryFactory;

    public FormQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(FormEntity.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<FormListResponse> formListByMember(Pageable pageable, MemberEntity member) {
        JPAQuery<FormListResponse> query = queryFactory.select(Projections.constructor(FormListResponse.class,
                        formEntity.formSeq,
                        formEntity.title,
                        formEntity.category,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y.%m.%d')", formEntity.createDate),
                        formEntity.answer
                ))
                .from(
                        formEntity
                )
                .where(
                        formEntity.member.eq(member),
                        formEntity.deletedDate.isNull()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(formEntity.getType(), formEntity.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        QueryResults<FormListResponse> result = query.fetchResults();

        return new PageImpl(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<InsuranceListResponse> insuranceList(Pageable pageable, InsuranceListRequest request) {
        JPAQuery<InsuranceListResponse> query = queryFactory.select(Projections.constructor(InsuranceListResponse.class,
                    Expressions.constant(0),
                    formEntity.formSeq,
                    formEntity.content,
                    formEntity.answer
                ))
                .from(formEntity)
                .where(
                        searchByTravelType(request.getTravelType()),
                        searchByAnswer(request.getAnswer()),
                        searchByQuery(request.getQuery())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(formEntity.getType(), formEntity.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        QueryResults<InsuranceListResponse> result = query.fetchResults();

        return new PageImpl(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression searchByTravelType(String travelType) {
        if(travelType!=null && !travelType.equals("")) {
            return  Expressions.stringTemplate("JSON_EXTRACT({0}, '$.travelType')", formEntity.content).contains(travelType);
        }else {
            return null;
        }
    }

    private Predicate searchByAnswer(String answer) {
        if (ObjectUtils.isEmpty(answer)) {
            return null;
        } else if (answer.equals("noAnswer")) {
            return formEntity.answer.isNull();
        } else if (answer.equals("answer")) {
            return formEntity.answer.isNotNull();
        }  else {
            return null;
        }
    }

    private BooleanExpression searchByQuery(String query) {
        StringTemplate stringTemplate = Expressions.stringTemplate("JSON_EXTRACT({0}, '$.name')", formEntity.content);
        return query !=null ? stringTemplate.contains(query): null;
    }
}
