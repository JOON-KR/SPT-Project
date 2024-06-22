package com.sptp.dawnary.diary.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiary is a Querydsl query type for Diary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiary extends EntityPathBase<Diary> {

    private static final long serialVersionUID = -1592906330L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiary diary = new QDiary("diary");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imagePath = createString("imagePath");

    public final com.sptp.dawnary.member.domain.QMember member;

    public final NumberPath<Double> sentiment = createNumber("sentiment", Double.class);

    public final ListPath<com.sptp.dawnary.seriesDiary.domain.SeriesDiary, com.sptp.dawnary.seriesDiary.domain.QSeriesDiary> seriesDiaries = this.<com.sptp.dawnary.seriesDiary.domain.SeriesDiary, com.sptp.dawnary.seriesDiary.domain.QSeriesDiary>createList("seriesDiaries", com.sptp.dawnary.seriesDiary.domain.SeriesDiary.class, com.sptp.dawnary.seriesDiary.domain.QSeriesDiary.class, PathInits.DIRECT2);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath title = createString("title");

    public final EnumPath<Weather> weather = createEnum("weather", Weather.class);

    public QDiary(String variable) {
        this(Diary.class, forVariable(variable), INITS);
    }

    public QDiary(Path<? extends Diary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiary(PathMetadata metadata, PathInits inits) {
        this(Diary.class, metadata, inits);
    }

    public QDiary(Class<? extends Diary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.sptp.dawnary.member.domain.QMember(forProperty("member")) : null;
    }

}

