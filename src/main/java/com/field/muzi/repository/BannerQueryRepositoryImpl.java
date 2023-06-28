package com.field.muzi.repository;

import com.field.muzi.domain.entity.BannerEntity;
import com.field.muzi.setup.QDirManagerEntity;
import com.field.muzi.setup.QFileManagerEntity;
import com.field.muzi.web.admin.dto.banner.BannerDetailResponse;
import com.field.muzi.web.admin.dto.banner.BannerListResponse;
import com.field.muzi.web.user.dto.banner.UserBannerListResponse;
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

import java.util.List;

import static com.field.muzi.domain.entity.QBannerEntity.bannerEntity;

@Repository
public class BannerQueryRepositoryImpl extends QuerydslRepositorySupport implements BannerQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BannerQueryRepositoryImpl(JPAQueryFactory queryFactory) {
        super(BannerEntity.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<UserBannerListResponse> userBannerList() {
        QFileManagerEntity file = new QFileManagerEntity("file");
        QDirManagerEntity dir = new QDirManagerEntity("dir");
        QFileManagerEntity mFile = new QFileManagerEntity("mFile");
        QDirManagerEntity mDir = new QDirManagerEntity("mDir");
        return queryFactory.select(Projections.constructor(UserBannerListResponse.class,
                                bannerEntity.bannerSeq,
                                bannerEntity.bannerTitle,
                                bannerEntity.bannerContents,
                                bannerEntity.bannerColor,
                                bannerEntity.reservationUrl,
                                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d %T')", bannerEntity.startDate),
                                Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d %T')", bannerEntity.endDate),
                                Projections.constructor(UserBannerListResponse.Img.class,
                                        bannerEntity.bannerImage.fileSeq,
                                        bannerEntity.bannerImage.originName,
                                        dir.dirUrl.append(bannerEntity.bannerImage.fileName)).skipNulls(),
                                Projections.constructor(UserBannerListResponse.Img.class,
                                        bannerEntity.mobileBannerImage.fileSeq,
                                        bannerEntity.mobileBannerImage.originName,
                                        mDir.dirUrl.append(bannerEntity.mobileBannerImage.fileName)).skipNulls()
                        )
                )
                .from(bannerEntity)
                .leftJoin(bannerEntity.bannerImage, file)
                .leftJoin(bannerEntity.bannerImage.dirManager, dir)
                .leftJoin(bannerEntity.mobileBannerImage, mFile)
                .leftJoin(bannerEntity.mobileBannerImage.dirManager, mDir)
                .where(
                        bannerEntity.withdrawal.isFalse(),
                        bannerEntity.bannerStatus.isTrue(),
                        bannerEntity.startDate.lt(Expressions.currentTimestamp()),
                        bannerEntity.endDate.goe(Expressions.currentTimestamp()).or(bannerEntity.endDate.isNull())
                )
                .orderBy(bannerEntity.bannerSeq.desc())
                .fetch();
    }

    @Override
    public BannerDetailResponse adminBannerDetail(String bannerSeq) {
        QFileManagerEntity file = new QFileManagerEntity("file");
        QDirManagerEntity dir = new QDirManagerEntity("dir");
        QFileManagerEntity mFile = new QFileManagerEntity("mFile");
        QDirManagerEntity mDir = new QDirManagerEntity("mDir");


        return queryFactory.select(Projections.constructor(BannerDetailResponse.class,
                        bannerEntity.bannerSeq,
                        bannerEntity.bannerTitle,
                        bannerEntity.bannerContents,
                        bannerEntity.bannerColor,
                        bannerEntity.reservationUrl,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d %T')", bannerEntity.startDate),
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d %T')", bannerEntity.endDate),
                        dir.dirUrl.append(bannerEntity.bannerImage.fileName),
                        mDir.dirUrl.append(bannerEntity.mobileBannerImage.fileName),
                        bannerEntity.bannerStatus
                ))
                .from(bannerEntity)
                .leftJoin(bannerEntity.bannerImage, file)
                .leftJoin(bannerEntity.bannerImage.dirManager, dir)
                .leftJoin(bannerEntity.mobileBannerImage, mFile)
                .leftJoin(bannerEntity.mobileBannerImage.dirManager, mDir)
                .where(
                        bannerEntity.bannerSeq.eq(bannerSeq)
                )
                .fetchOne();
    }

    @Override
    public Page<BannerListResponse> adminBannerList(Pageable pageable) {
        QFileManagerEntity file = new QFileManagerEntity("file");
        QDirManagerEntity dir = new QDirManagerEntity("dir");

        JPAQuery query = queryFactory.select(Projections.constructor(BannerListResponse.class,
                        Expressions.constant(0), // rowNum
                        bannerEntity.bannerSeq,
                        bannerEntity.bannerTitle,
                        bannerEntity.bannerContents,
                        bannerEntity.bannerColor,
                        bannerEntity.reservationUrl,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d %T')", bannerEntity.startDate),
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d %T')", bannerEntity.endDate),
                        dir.dirUrl.append(bannerEntity.bannerImage.fileName),
                        bannerEntity.bannerStatus
                ))
                .from(bannerEntity)
                .leftJoin(bannerEntity.bannerImage, file)
                .leftJoin(bannerEntity.bannerImage.dirManager, dir)
                .where(
                        bannerEntity.withdrawal.isFalse()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(bannerEntity.getType(), bannerEntity.getMetadata());
            query.orderBy(new OrderSpecifier<>(o.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(o.getProperty())));
        }

        QueryResults<BannerListResponse> result = query.fetchResults();

        return new PageImpl(result.getResults(), pageable, result.getTotal());
    }

}
