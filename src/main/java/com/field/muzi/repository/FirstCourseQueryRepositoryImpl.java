package com.field.muzi.repository;

import com.field.muzi.domain.entity.CompanyEntity;
import com.field.muzi.domain.entity.FirstCourseEntity;
import com.field.muzi.setup.QDirManagerEntity;
import com.field.muzi.setup.QFileManagerEntity;
import com.field.muzi.web.admin.dto.course.CourseFirstLIstResponse;
import com.field.muzi.web.admin.dto.course.CourseFirstListRequest;
import com.field.muzi.web.user.dto.course.AllCourseListRequest;
import com.field.muzi.web.user.dto.course.AllCourseListResponse;
import com.field.muzi.web.user.dto.course.FirstCourseDetailResponse;
import com.field.muzi.web.user.dto.reservation.MonthlyFirstCourse;
import com.field.muzi.web.user.dto.reservation.ReservationDetailResponse;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringExpression;
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
import static com.field.muzi.domain.entity.QSecondCourseEntity.secondCourseEntity;
import static com.field.muzi.setup.QFileManagerEntity.fileManagerEntity;
@Repository
public class FirstCourseQueryRepositoryImpl extends QuerydslRepositorySupport implements FirstCourseQueryRepository {

    private final JPAQueryFactory queryFactory;

    public FirstCourseQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(FirstCourseEntity.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<CourseFirstLIstResponse> firstCourseList(CourseFirstListRequest request, CompanyEntity company, Pageable pageable) {

        QFileManagerEntity file = new QFileManagerEntity("file");
        QDirManagerEntity dir = new QDirManagerEntity("dir");

        JPAQuery<CourseFirstLIstResponse> query = queryFactory
                .select(Projections.constructor(CourseFirstLIstResponse.class,
                firstCourseEntity.firstCourseSeq,
                firstCourseEntity.firstCourseTitle,
                companyEntity.companyTitle,
                companyEntity.companySeq,
                companyEntity.companyColor,
                firstCourseEntity.reservationTime,
                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", firstCourseEntity.startDate),
                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", firstCourseEntity.endDate),
                firstCourseEntity.courseType,
                firstCourseEntity.courseDetail,
                firstCourseEntity.reservationDay,
                companyEntity.companyAddress,
                firstCourseEntity.withdrawal,
                Projections.constructor(AllCourseListResponse.Img.class,
                        firstCourseEntity.thumbnailImage.fileSeq,
                        firstCourseEntity.thumbnailImage.originName,
                        dir.dirUrl.append(firstCourseEntity.thumbnailImage.fileName)).skipNulls(),
                firstCourseEntity.runningTime
                ))
                .from(firstCourseEntity)
                .leftJoin(firstCourseEntity.company, companyEntity)
                .leftJoin(firstCourseEntity.thumbnailImage, file)
                .leftJoin(file.dirManager, dir)
                .where(
                        firstCourseEntity.withdrawal.isFalse(),
                        searchCompany(company),
                        searchSecondCourseWithKeyword(request.getType(), request.getKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(firstCourseEntity.getType(), firstCourseEntity.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        QueryResults<CourseFirstLIstResponse> result = query.fetchResults();

        return new PageImpl(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public List<AllCourseListResponse.FirstCourse> userFirstCourseList(AllCourseListRequest request) {

        QFileManagerEntity file = new QFileManagerEntity("file");
        QDirManagerEntity dir = new QDirManagerEntity("dir");

        return queryFactory.select(Projections.constructor(AllCourseListResponse.FirstCourse.class,
                        firstCourseEntity.firstCourseSeq,
                        firstCourseEntity.firstCourseTitle,
                        companyEntity.companyTitle,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", firstCourseEntity.startDate),
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", firstCourseEntity.endDate),
                        firstCourseEntity.courseType,
                        firstCourseEntity.courseDetail,
                        firstCourseEntity.reservationDay,
                        Projections.constructor(AllCourseListResponse.Img.class,
                                firstCourseEntity.thumbnailImage.fileSeq,
                                firstCourseEntity.thumbnailImage.originName,
                                dir.dirUrl.append(firstCourseEntity.thumbnailImage.fileName)).skipNulls(),
                        firstCourseEntity.runningTime,
                        firstCourseEntity.address
                ))
                .from(firstCourseEntity)
                .leftJoin(firstCourseEntity.company, companyEntity)
                .leftJoin(firstCourseEntity.thumbnailImage, file)
                .leftJoin(file.dirManager, dir)
                .where(
                        firstCourseEntity.withdrawal.isFalse(),
                        firstCourseEntity.endDate.goe(request.getSearchDate()).or(firstCourseEntity.endDate.isNull())
                )
                .fetch();
    }

    @Override
    public FirstCourseDetailResponse firstCourseDetail(String firstCourseSeq) {

        QFileManagerEntity file = new QFileManagerEntity("file");
        QDirManagerEntity dir = new QDirManagerEntity("dir");

        return queryFactory.select(Projections.constructor(FirstCourseDetailResponse.class,
                    firstCourseEntity.firstCourseSeq,
                    firstCourseEntity.firstCourseTitle,
                    companyEntity.companyTitle,
                    companyEntity.companySeq,
                    firstCourseEntity.courseType,
                    firstCourseEntity.courseDetail,
                    firstCourseEntity.runningTime,
                    firstCourseEntity.reservationTime,
                    firstCourseEntity.reservationDay,
                    Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", firstCourseEntity.startDate),
                    Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", firstCourseEntity.endDate),
                    firstCourseEntity.applicant,
                    firstCourseEntity.applicantDetail,
                    firstCourseEntity.guestNumber,
                    firstCourseEntity.managerPhone,
                    firstCourseEntity.vehicle,
                    firstCourseEntity.etc,
                    firstCourseEntity.requirement,
                    firstCourseEntity.terms1,
                    firstCourseEntity.terms2,
                    firstCourseEntity.terms3,
                    firstCourseEntity.terms4,
                    firstCourseEntity.terms5,
                    Projections.constructor(FirstCourseDetailResponse.Img.class,
                            firstCourseEntity.thumbnailImage.fileSeq,
                            firstCourseEntity.thumbnailImage.originName,
                            dir.dirUrl.append(firstCourseEntity.thumbnailImage.fileName)).skipNulls(),
                    firstCourseEntity.descriptionImage,
                    firstCourseEntity.address
                        )
                )
                .from(firstCourseEntity)
                .leftJoin(firstCourseEntity.thumbnailImage, file)
                .leftJoin(firstCourseEntity.thumbnailImage.dirManager, dir)
                .leftJoin(firstCourseEntity.company, companyEntity)
                .where(
                        firstCourseEntity.firstCourseSeq.eq(firstCourseSeq),
                        firstCourseEntity.withdrawal.isFalse()
                )
                .fetchOne();
    }

    @Override
    public ReservationDetailResponse.FirstCourse reservationFirstCourse(String firstCourseSeq) {
        return queryFactory.select(Projections.constructor(ReservationDetailResponse.FirstCourse.class,
                    firstCourseEntity.firstCourseSeq,
                    firstCourseEntity.firstCourseTitle,
                    firstCourseEntity.runningTime,
                    firstCourseEntity.courseType,
                    firstCourseEntity.courseDetail,
                    firstCourseEntity.requirement
                ))
                .from(firstCourseEntity)
                .where(
                        firstCourseEntity.firstCourseSeq.eq(firstCourseSeq)
                )
                .fetchOne();
    }

    @Override
    public List<MonthlyFirstCourse> monthlyFirstCourseList(Date searchDate) {
        return queryFactory.select(Projections.constructor(MonthlyFirstCourse.class,
                    firstCourseEntity.firstCourseSeq,
                    firstCourseEntity.firstCourseTitle,
                    firstCourseEntity.reservationDay,
                    firstCourseEntity.reservationTime
                ))
                .from(firstCourseEntity)
                .where(
                        firstCourseEntity.withdrawal.isFalse(),
                        firstCourseEntity.startDate.loe(searchDate),
                        firstCourseEntity.endDate.goe(searchDate).or(firstCourseEntity.endDate.isNull())
                )
                .fetch();
    }

    private StringExpression replaceUrlId(StringExpression colName, String value) {
        return Expressions.stringTemplate("replace({0}, '[id]', {1})", colName, value);
    }

    private Predicate searchCompany(CompanyEntity company) {
        if (ObjectUtils.isEmpty(company)) {
            return null;
        } else {
            return firstCourseEntity.company.eq(company);
        }
    }

    private Predicate searchSecondCourseWithKeyword(String searchType, String keyword) {
        if (ObjectUtils.isEmpty(searchType)) {
            return null;
        } else if (searchType.equals("courseTitle")) {
            return firstCourseEntity.firstCourseTitle.contains(keyword);
        } else if (searchType.equals("companyTitle")) {
        return firstCourseEntity.company.companyTitle.contains(keyword);
    }  else {
            return null;
        }
    }
}
