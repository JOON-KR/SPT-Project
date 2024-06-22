package com.sptp.dawnary.seriesDiary.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeriesDiary is a Querydsl query type for SeriesDiary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeriesDiary extends EntityPathBase<SeriesDiary> {

    private static final long serialVersionUID = 221312838L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSeriesDiary seriesDiary = new QSeriesDiary("seriesDiary");

    public final com.sptp.dawnary.diary.domain.QDiary diary;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.sptp.dawnary.series.domain.QSeries series;

    public QSeriesDiary(String variable) {
        this(SeriesDiary.class, forVariable(variable), INITS);
    }

    public QSeriesDiary(Path<? extends SeriesDiary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSeriesDiary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSeriesDiary(PathMetadata metadata, PathInits inits) {
        this(SeriesDiary.class, metadata, inits);
    }

    public QSeriesDiary(Class<? extends SeriesDiary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.diary = inits.isInitialized("diary") ? new com.sptp.dawnary.diary.domain.QDiary(forProperty("diary"), inits.get("diary")) : null;
        this.series = inits.isInitialized("series") ? new com.sptp.dawnary.series.domain.QSeries(forProperty("series"), inits.get("series")) : null;
    }

}

