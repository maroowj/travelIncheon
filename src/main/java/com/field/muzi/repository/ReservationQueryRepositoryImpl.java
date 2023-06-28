package com.field.muzi.repository;

import com.field.muzi.domain.entity.CompanyEntity;
import com.field.muzi.domain.entity.ReservationEntity;
import com.field.muzi.web.admin.dto.reservation.ReservationListRequest;
import com.field.muzi.web.admin.dto.reservation.ReservationListResponse;
import com.field.muzi.web.common.dto.CommonCondition;
import com.field.muzi.web.user.dto.reservation.*;
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

import java.util.Date;
import java.util.List;

import static com.field.muzi.domain.entity.QCompanyEntity.companyEntity;
import static com.field.muzi.domain.entity.QFirstCourseEntity.firstCourseEntity;
import static com.field.muzi.domain.entity.QReservationEntity.reservationEntity;
@Repository
public class ReservationQueryRepositoryImpl extends QuerydslRepositorySupport implements ReservationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ReservationQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(ReservationEntity.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<ReservationListResponse> reservationList(CompanyEntity company, ReservationListRequest request, Pageable pageable, CommonCondition condition) {

        JPAQuery<ReservationListResponse> query = queryFactory
                .select(Projections.constructor(ReservationListResponse.class,
                        reservationEntity.reservationSeq,
                        reservationEntity.bookingStatus,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d %T')", reservationEntity.reservationDate),
                        reservationEntity.firstCourse.firstCourseTitle,
                        reservationEntity.firstCourse.courseType,
                        reservationEntity.secondCourse,
                        reservationEntity.managerPhone,
                        reservationEntity.applicant,
                        reservationEntity.guestType,
                        reservationEntity.guestNumber
                ))
                .from(reservationEntity)
                .leftJoin(reservationEntity.firstCourse, firstCourseEntity)
                .leftJoin(firstCourseEntity.company, companyEntity)
                .where(
                        searchCompany(company),
                        searchReservationWithKeyword(request.getType(), request.getKeyword()),
                        dateGoe(condition.getDateFrom()), dateLoe(condition.getDateTo())

                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(reservationEntity.getType(), reservationEntity.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        QueryResults<ReservationListResponse> result = query.fetchResults();

        return new PageImpl(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public int reservationCountByFirstCourse(String firstCourseSeq) {
        return queryFactory.select(
                reservationEntity.reservationSeq.count().intValue()
                )
                .from(reservationEntity)
                .where(
                        reservationEntity.firstCourse.firstCourseSeq.eq(firstCourseSeq),
                        reservationEntity.bookingStatus.ne("예약취소")
                )
                .fetchOne();
    }

    @Override
    public int reservationCountBySecondCourse(String secondCourseSeq) {
        return queryFactory.select(
                        reservationEntity.reservationSeq.count().intValue()
                )
                .from(reservationEntity)
                .where(
                        reservationEntity.secondCourse.contains(secondCourseSeq),
                        reservationEntity.bookingStatus.ne("예약취소")
                )
                .fetchOne();
    }

    @Override
    public List<ReservationCheckResponse> reservationCheckList(ReservationCheckRequest request) {
        return queryFactory.select(Projections.constructor(ReservationCheckResponse.class,
                        reservationEntity.reservationSeq,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d %T')", reservationEntity.reservationDate)
                ))
                .from(reservationEntity)
                .where(
                        reservationEntity.firstCourse.firstCourseSeq.eq(request.getFirstCourseSeq()),
                        reservationEntity.reservationDate.eq(request.getReservationDate()),
                        reservationEntity.bookingStatus.ne("예약취소").or(reservationEntity.bookingStatus.isNull())
                )
                .fetch();
    }

    @Override
    public ReservationDetailResponse.Reservation userReservationInfo(ReservationDetailRequest request) {
        return queryFactory.select(Projections.constructor(ReservationDetailResponse.Reservation.class,
                    reservationEntity.reservationSeq,
                    Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d %T')", reservationEntity.reservationDate),
                    reservationEntity.terms1.as("terms1"),
                    reservationEntity.terms2.as("terms2"),
                    reservationEntity.terms3.as("terms3"),
                    reservationEntity.terms4.as("terms4"),
                    reservationEntity.terms5.as("terms5"),
                    reservationEntity.applicant,
                    reservationEntity.applicantDetail,
                    reservationEntity.guestType,
                    reservationEntity.guestNumber,
                    reservationEntity.managerPhone,
                    reservationEntity.vehicleType,
                    reservationEntity.vehicleNumber,
                    reservationEntity.etc,
                    reservationEntity.firstCourse.firstCourseSeq,
                    reservationEntity.secondCourse,
                    reservationEntity.bookingStatus,
                    reservationEntity.busFare,
                    reservationEntity.guideFee,
                    reservationEntity.insuranceFee
                ))
                .from(reservationEntity)
                .where(
                        reservationEntity.reservationSeq.eq(request.getReservationSeq())
                )
                .fetchOne();
    }

    @Override
    public List<MonthlyReservationCheck> checkList(String firstCourseSeq, Date reservationDate) {
        return queryFactory.select(Projections.constructor(MonthlyReservationCheck.class,
                    reservationEntity.reservationSeq,
                    reservationEntity.applicant
                ))
                .from(reservationEntity)
                .where(
                        reservationEntity.firstCourse.firstCourseSeq.eq(firstCourseSeq),
                        reservationEntity.reservationDate.eq(reservationDate),
                        reservationEntity.bookingStatus.ne("예약취소")
                )
                .fetch();
    }

    private Predicate searchCompany(CompanyEntity company) {
        if (ObjectUtils.isEmpty(company)) {
            return null;
        } else {
            return firstCourseEntity.company.eq(company);
        }
    }

    private Predicate searchReservationWithKeyword(String searchType, String keyword) {
        if (ObjectUtils.isEmpty(searchType)) {
            return null;
        } else if (searchType.equals("courseTitle")) {
            return reservationEntity.firstCourse.firstCourseTitle.contains(keyword);
        } else if (searchType.equals("companyTitle")) {
            return reservationEntity.firstCourse.company.companyTitle.contains(keyword);
        }  else {
            return null;
        }
    }

    private BooleanExpression dateGoe(Date dateGoe) {
        return dateGoe != null ? reservationEntity.reservationDate.goe(dateGoe) : null;
    }

    private BooleanExpression dateLoe(Date dateLoe) {
        return dateLoe != null ? reservationEntity.reservationDate.loe(dateLoe) : null;
    }
}
