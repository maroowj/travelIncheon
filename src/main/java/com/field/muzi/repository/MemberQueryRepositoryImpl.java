package com.field.muzi.repository;

import com.field.muzi.domain.entity.MemberEntity;
import com.field.muzi.domain.user.Role;
import com.field.muzi.web.admin.dto.member.MemberListRequest;
import com.field.muzi.web.admin.dto.member.MemberListResponse;
import com.field.muzi.web.common.dto.member.LoginRequest;
import com.field.muzi.web.user.dto.diy.DiyListResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.*;
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
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.field.muzi.domain.entity.QDiyEntity.diyEntity;
import static com.field.muzi.domain.entity.QMemberEntity.memberEntity;
import static com.field.muzi.domain.entity.QMemberTypeEntity.memberTypeEntity;
import static com.field.muzi.domain.entity.QSecondCourseEntity.secondCourseEntity;

@Repository
public class MemberQueryRepositoryImpl extends QuerydslRepositorySupport implements MemberQueryRepository {
    private final JPAQueryFactory queryFactory;

    public MemberQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(MemberEntity.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<MemberEntity> findByMemberIdAndRole(LoginRequest request, List<Role> role) {
        return Optional.ofNullable(
                queryFactory
                        .select(memberEntity)
                        .from(memberEntity)
                        .join(memberTypeEntity).on(memberTypeEntity.member.eq(memberEntity))
                        .where(
                                memberEntity.memberId.eq(request.getMemberId()),
                                searchRole(role),
                                memberEntity.withdrawal.isFalse()
                        )
                        .fetchOne()
        );
    }

    @Override
    public Optional<MemberEntity> findByMemberIdAndRole(String id, Role role) {
        return Optional.ofNullable(
                queryFactory
                        .select(memberEntity)
                        .from(memberEntity)
                        .join(memberTypeEntity).on(memberTypeEntity.member.eq(memberEntity))
                        .where(
                                memberEntity.memberId.eq(id),
                                memberTypeEntity.memberType.eq(role),
                                memberEntity.withdrawal.isFalse()
                        )
                        .fetchOne()
        );
    }

    @Override
    public Page<MemberListResponse> memberList(Pageable pageable, MemberListRequest request) {
        JPAQuery<MemberListResponse> query = queryFactory.select(Projections.constructor(MemberListResponse.class,
                        Expressions.constant(0),
                        memberEntity.memberSeq,
                        memberEntity.memberInfo.email,
                        memberEntity.memberInfo.memberName,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", memberEntity.createDate)
                ))
                .from(memberEntity)
                .where(
                        searchByQuery(request.getQuery()),
                        memberEntity.withdrawal.isFalse(),
                        memberEntity.roles.contains(Role.ADMIN).not()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(memberEntity.getType(), memberEntity.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        QueryResults<MemberListResponse> result = query.fetchResults();

        return new PageImpl(result.getResults(), pageable, result.getTotal());
    }


    private Predicate searchRole(List<Role> roles) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for (Role role : roles) {
            booleanBuilder.or(
                    memberTypeEntity.memberType.eq(role)
            );
        }

        return booleanBuilder;
    }

    private BooleanExpression searchByQuery(String query) {
        return StringUtils.hasText(query) ? memberEntity.memberInfo.email.contains(query).or(memberEntity.memberInfo.memberName.contains(query)) : null;
    }
}
