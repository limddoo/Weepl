package com.weepl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReserveSchedule is a Querydsl query type for ReserveSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReserveSchedule extends EntityPathBase<ReserveSchedule> {

    private static final long serialVersionUID = 269080912L;

    public static final QReserveSchedule reserveSchedule = new QReserveSchedule("reserveSchedule");

    public final DateTimePath<java.time.LocalDateTime> reserveDt = createDateTime("reserveDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> reserveScheduleCd = createNumber("reserveScheduleCd", Long.class);

    public final StringPath reserveTime = createString("reserveTime");

    public QReserveSchedule(String variable) {
        super(ReserveSchedule.class, forVariable(variable));
    }

    public QReserveSchedule(Path<? extends ReserveSchedule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReserveSchedule(PathMetadata metadata) {
        super(ReserveSchedule.class, metadata);
    }

}

