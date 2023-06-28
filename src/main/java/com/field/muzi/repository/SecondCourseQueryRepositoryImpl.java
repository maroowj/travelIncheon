package com.field.muzi.repository;

import com.field.muzi.domain.entity.SecondCourseEntity;

import com.field.muzi.setup.QDirManagerEntity;
import com.field.muzi.setup.QFileManagerEntity;
import com.field.muzi.web.admin.dto.course.CourseSecondListRequest;
import com.field.muzi.web.admin.dto.course.CourseSecondListResponse;
import com.field.muzi.web.user.dto.course.AllCourseListRequest;
import com.field.muzi.web.user.dto.course.AllCourseListResponse;
import com.field.muzi.web.user.dto.course.CategoryGroup;
import com.field.muzi.web.user.dto.course.SecondCourseDetailResponse;
import com.field.muzi.web.user.dto.reservation.ReservationDetailResponse;
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

import java.util.List;

import static com.field.muzi.domain.entity.QSecondCourseEntity.secondCourseEntity;


@Repository
public class SecondCourseQueryRepositoryImpl extends QuerydslRepositorySupport implements SecondCourseQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SecondCourseQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(SecondCourseEntity.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<CourseSecondListResponse> secondCourseList(CourseSecondListRequest request, Pageable pageable) {

        QFileManagerEntity file = new QFileManagerEntity("file");
        QDirManagerEntity dir = new QDirManagerEntity("dir");

        JPAQuery<CourseSecondListResponse> query = queryFactory
                .select(Projections.constructor(CourseSecondListResponse.class,
                        secondCourseEntity.secondCourseSeq,
                        secondCourseEntity.secondCourseTitle,
                        secondCourseEntity.cost,
                        secondCourseEntity.costOption,
                        secondCourseEntity.runningTime,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", secondCourseEntity.startDate),
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", secondCourseEntity.endDate),
                        secondCourseEntity.withdrawal,
                        secondCourseEntity.category,
                        secondCourseEntity.link
                ))
                .from(secondCourseEntity)
                .where(
                        secondCourseEntity.withdrawal.isFalse(),
                        searchSecondCourseWithKeyword( request.getType(), request.getKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(secondCourseEntity.getType(), secondCourseEntity.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        QueryResults<CourseSecondListResponse> result = query.fetchResults();

        return new PageImpl(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public Page<SecondCourseDetailResponse> secondCourseList(Pageable pageable, String category) {
        QFileManagerEntity file = new QFileManagerEntity("file");
        QDirManagerEntity dir = new QDirManagerEntity("dir");

        JPAQuery<SecondCourseDetailResponse> query = queryFactory
                .select(Projections.constructor(SecondCourseDetailResponse.class,
                                secondCourseEntity.secondCourseSeq,
                                secondCourseEntity.secondCourseTitle,
                                secondCourseEntity.course,
                                secondCourseEntity.reservationDay,
                                secondCourseEntity.runningTime,
                                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", secondCourseEntity.startDate),
                                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", secondCourseEntity.endDate),
                                secondCourseEntity.cost,
                                secondCourseEntity.costOption,
                                Projections.constructor(SecondCourseDetailResponse.Img.class,
                                        secondCourseEntity.thumbnailImage.fileSeq,
                                        secondCourseEntity.thumbnailImage.originName,
                                        dir.dirUrl.append(secondCourseEntity.thumbnailImage.fileName)).skipNulls(),
                                secondCourseEntity.descriptionImage,
                                secondCourseEntity.address,
                                secondCourseEntity.category,
                                secondCourseEntity.link
                        )
                )
                .from(secondCourseEntity)
                .leftJoin(secondCourseEntity.thumbnailImage, file)
                .leftJoin(secondCourseEntity.thumbnailImage.dirManager, dir)
                .where(
                        secondCourseEntity.withdrawal.isFalse(),
                        searchSecondCourseByCategory(category)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(secondCourseEntity.getType(), secondCourseEntity.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        QueryResults<SecondCourseDetailResponse> result = query.fetchResults();

        return new PageImpl(result.getResults(), pageable, result.getTotal());
    }

    @Override
    public List<CategoryGroup> categoryGroup() {
        return queryFactory.select(Projections.constructor(CategoryGroup.class,
                secondCourseEntity.category
                ))
                .from(secondCourseEntity)
                .groupBy(secondCourseEntity.category)
                .orderBy(secondCourseEntity.category.asc())
                .fetch();
    }

    @Override
    public List<AllCourseListResponse.SecondCourse> userSecondCourseList(AllCourseListRequest request) {
        QFileManagerEntity file = new QFileManagerEntity("file");
        QDirManagerEntity dir = new QDirManagerEntity("dir");

        return queryFactory.select(Projections.constructor(AllCourseListResponse.SecondCourse.class,
                        secondCourseEntity.secondCourseSeq,
                        secondCourseEntity.secondCourseTitle,
                        secondCourseEntity.runningTime,
                        secondCourseEntity.cost,
                        secondCourseEntity.course,
                        secondCourseEntity.reservationDay,
                        secondCourseEntity.costOption,
                        secondCourseEntity.address,
                        secondCourseEntity.category,
                        Projections.constructor(AllCourseListResponse.Img.class,
                                secondCourseEntity.thumbnailImage.fileSeq,
                                secondCourseEntity.thumbnailImage.originName,
                                dir.dirUrl.append(secondCourseEntity.thumbnailImage.fileName)).skipNulls()
                ))
                .from(secondCourseEntity)
                .leftJoin(secondCourseEntity.thumbnailImage, file)
                .leftJoin(secondCourseEntity.thumbnailImage.dirManager, dir)
                .where(
                        secondCourseEntity.withdrawal.isFalse(),
                        secondCourseEntity.endDate.goe(Expressions.currentTimestamp()).or(secondCourseEntity.endDate.isNull()),
                        searchSecondCourseWithCategory(request.getCategory()),
                        searchSecondCourseByMainAddress(request.getMainAddress()),
                        searchSecondCourseBySubAddress(request.getSubAddress())
                )
                .orderBy(secondCourseEntity.category.asc())
                .fetch();
    }

    @Override
    public SecondCourseDetailResponse secondCourseDetail(String secondCourseSeq) {

        QFileManagerEntity file = new QFileManagerEntity("file");
        QDirManagerEntity dir = new QDirManagerEntity("dir");

        return queryFactory.select(Projections.constructor(SecondCourseDetailResponse.class,
                                secondCourseEntity.secondCourseSeq,
                                secondCourseEntity.secondCourseTitle,
                                secondCourseEntity.course,
                                secondCourseEntity.reservationDay,
                                secondCourseEntity.runningTime,
                                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", secondCourseEntity.startDate),
                                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", secondCourseEntity.endDate),
                                secondCourseEntity.cost,
                                secondCourseEntity.costOption,
                                Projections.constructor(SecondCourseDetailResponse.Img.class,
                                        secondCourseEntity.thumbnailImage.fileSeq,
                                        secondCourseEntity.thumbnailImage.originName,
                                        dir.dirUrl.append(secondCourseEntity.thumbnailImage.fileName)).skipNulls(),
                                secondCourseEntity.descriptionImage,
                                secondCourseEntity.address,
                                secondCourseEntity.category,
                                secondCourseEntity.link
                        )
                )
                .from(secondCourseEntity)
                .leftJoin(secondCourseEntity.thumbnailImage, file)
                .leftJoin(secondCourseEntity.thumbnailImage.dirManager, dir)
                .where(
                        secondCourseEntity.secondCourseSeq.eq(secondCourseSeq),
                        secondCourseEntity.withdrawal.isFalse()
                )
                .fetchOne();
    }

    @Override
    public ReservationDetailResponse.SecondCourse reservationSecondCourse(String secondCourseSeq) {
        return queryFactory.select(Projections.constructor(ReservationDetailResponse.SecondCourse.class,
                    secondCourseEntity.secondCourseSeq,
                    secondCourseEntity.secondCourseTitle,
                    secondCourseEntity.runningTime,
                    secondCourseEntity.course,
                    secondCourseEntity.cost,
                    secondCourseEntity.costOption
                ))
                .from(secondCourseEntity)
                .where(
                    secondCourseEntity.secondCourseSeq.eq(secondCourseSeq)
                )
                .fetchOne();
    }

    private Predicate searchSecondCourseWithKeyword(String searchType, String keyword) {
        if (ObjectUtils.isEmpty(searchType)) {
            return null;
        } else if (searchType.equals("courseTitle")) {
            return secondCourseEntity.secondCourseTitle.contains(keyword);
        }  else {
            return null;
        }
    }

    private Predicate searchSecondCourseWithCategory(String category) {
        if (ObjectUtils.isEmpty(category)) {
            return null;
        } else  {
            return secondCourseEntity.category.contains(category);
        }
    }

    private BooleanExpression searchSecondCourseByMainAddress(String mainAddress) {
        return mainAddress != null ? secondCourseEntity.mainAddress.contains(mainAddress) : null;
    }

    private BooleanExpression searchSecondCourseBySubAddress(String subAddress) {
        return subAddress != null ? secondCourseEntity.subAddress.contains(subAddress) : null;
    }

    private BooleanExpression searchSecondCourseByCategory(String category) {
        return category != null ? secondCourseEntity.category.contains(category) : null;
    }
}
