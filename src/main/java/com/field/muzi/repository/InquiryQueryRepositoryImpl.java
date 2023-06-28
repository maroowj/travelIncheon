package com.field.muzi.repository;

import com.field.muzi.domain.entity.FormEntity;
import com.field.muzi.domain.entity.InquiryEntity;
import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.web.admin.dto.banner.BannerListResponse;
import com.field.muzi.web.admin.dto.inquiry.InquiryListRequest;
import com.field.muzi.web.admin.dto.inquiry.InquiryListResponse;
import com.field.muzi.web.user.dto.form.FormListResponse;
import com.field.muzi.web.user.dto.inquiry.InquiryDetailsResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
import org.springframework.util.ObjectUtils;

import static com.field.muzi.domain.entity.QBannerEntity.bannerEntity;
import static com.field.muzi.domain.entity.QDiyEntity.diyEntity;
import static com.field.muzi.domain.entity.QFormEntity.formEntity;
import static com.field.muzi.domain.entity.QInquiryEntity.inquiryEntity;
import static com.field.muzi.domain.entity.QReservationEntity.reservationEntity;

@Repository
public class InquiryQueryRepositoryImpl extends QuerydslRepositorySupport implements InquiryQueryRepository {
    private final JPAQueryFactory queryFactory;

    public InquiryQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(InquiryEntity.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<InquiryDetailsResponse> inquiryList(Pageable pageable, MemberEntity member) {
        JPAQuery<InquiryDetailsResponse> query = queryFactory.select(Projections.constructor(InquiryDetailsResponse.class,
                    inquiryEntity.inquirySeq,
                    inquiryEntity.name,
                    inquiryEntity.businessName,
                    inquiryEntity.businessType,
                    inquiryEntity.serviceType,
                    inquiryEntity.area,
                    inquiryEntity.optionRequest,
                    inquiryEntity.tel,
                    inquiryEntity.email,
                    inquiryEntity.address,
                    inquiryEntity.content,
                    inquiryEntity.answer,
                    Expressions.stringTemplate("DATE_FORMAT({0}, '%Y.%m.%d')", inquiryEntity.createDate)
                ))
                .from(inquiryEntity)
                .where(
                        inquiryEntity.member.eq(member)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(inquiryEntity.getType(), inquiryEntity.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        QueryResults<InquiryDetailsResponse> result = query.fetchResults();

        return new PageImpl(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<InquiryListResponse> inquiryListAdmin(Pageable pageable, InquiryListRequest request) {
        JPAQuery<InquiryListResponse> query = queryFactory.select(Projections.constructor(InquiryListResponse.class,
                        Expressions.constant(0),
                        inquiryEntity.inquirySeq,
                        inquiryEntity.name,
                        inquiryEntity.businessName,
                        inquiryEntity.businessType,
                        inquiryEntity.serviceType,
                        inquiryEntity.area,
                        inquiryEntity.optionRequest,
                        inquiryEntity.tel,
                        inquiryEntity.email,
                        inquiryEntity.address,
                        inquiryEntity.content,
                        inquiryEntity.answer,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y.%m.%d')", inquiryEntity.createDate)
                ))
                .from(inquiryEntity)
                .where(
                        searchByQuery(request.getQuery()),
                        searchByAnswer(request.getAnswer())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(inquiryEntity.getType(), inquiryEntity.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        QueryResults<InquiryListResponse> result = query.fetchResults();

        return new PageImpl(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression searchByQuery(String query) {
        return query !=null ? inquiryEntity.name.contains(query) : null;
    }

    private Predicate searchByAnswer(String answer) {
        if (ObjectUtils.isEmpty(answer)) {
            return null;
        } else if (answer.equals("noAnswer")) {
            return inquiryEntity.answer.isNull();
        } else if (answer.equals("answer")) {
            return inquiryEntity.answer.isNotNull();
        }  else {
            return null;
        }
    }
}
