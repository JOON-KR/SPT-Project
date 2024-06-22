package com.sptp.dawnary.series.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeries is a Querydsl query type for Series
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeries extends EntityPathBase<Series> {

    private static final long serialVersionUID = -1480539476L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSeries series = new QSeries("series");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imagePath = createString("imagePath");

    public final ListPath<com.sptp.dawnary.like.domain.Like, com.sptp.dawnary.like.domain.QLike> likes = this.<com.sptp.dawnary.like.domain.Like, com.sptp.dawnary.like.domain.QLike>createList("likes", com.sptp.dawnary.like.domain.Like.class, com.sptp.dawnary.like.domain.QLike.class, PathInits.DIRECT2);

    public final com.sptp.dawnary.member.domain.QMember member;

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final ListPath<com.sptp.dawnary.seriesDiary.domain.SeriesDiary, com.sptp.dawnary.seriesDiary.domain.QSeriesDiary> seriesDiaries = this.<com.sptp.dawnary.seriesDiary.domain.SeriesDiary, com.sptp.dawnary.seriesDiary.domain.QSeriesDiary>createList("seriesDiaries", com.sptp.dawnary.seriesDiary.domain.SeriesDiary.class, com.sptp.dawnary.seriesDiary.domain.QSeriesDiary.class, PathInits.DIRECT2);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Long> viewCnt = createNumber("viewCnt", Long.class);

    public QSeries(String variable) {
        this(Series.class, forVariable(variable), INITS);
    }

    public QSeries(Path<? extends Series> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSeries(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSeries(PathMetadata metadata, PathInits inits) {
        this(Series.class, metadata, inits);
    }

    public QSeries(Class<? extends Series> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.sptp.dawnary.member.domain.QMember(forProperty("member")) : null;
    }

}

