package com.weepl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReserveApply is a Querydsl query type for ReserveApply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReserveApply extends EntityPathBase<ReserveApply> {

    private static final long serialVersionUID = -687906987L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReserveApply reserveApply = new QReserveApply("reserveApply");

    public final DateTimePath<java.time.LocalDateTime> cancDt = createDateTime("cancDt", java.time.LocalDateTime.class);

    public final StringPath consDiv = createString("consDiv");

    public final StringPath consReqContent = createString("consReqContent");

    public final QMember member;

    public final NumberPath<Long> reserveApplyCd = createNumber("reserveApplyCd", Long.class);

    public final DateTimePath<java.time.LocalDateTime> reserveDt = createDateTime("reserveDt", java.time.LocalDateTime.class);

    public final QReserveSchedule reserveSchedule;

    public final StringPath reserveStatus = createString("reserveStatus");

    public QReserveApply(String variable) {
        this(ReserveApply.class, forVariable(variable), INITS);
    }

    public QReserveApply(Path<? extends ReserveApply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReserveApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReserveApply(PathMetadata metadata, PathInits inits) {
        this(ReserveApply.class, metadata, inits);
    }

    public QReserveApply(Class<? extends ReserveApply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.reserveSchedule = inits.isInitialized("reserveSchedule") ? new QReserveSchedule(forProperty("reserveSchedule")) : null;
    }

}

