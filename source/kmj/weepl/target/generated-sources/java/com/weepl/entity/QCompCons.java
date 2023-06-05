package com.weepl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompCons is a Querydsl query type for CompCons
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompCons extends EntityPathBase<CompCons> {

    private static final long serialVersionUID = 1193873315L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompCons compCons = new QCompCons("compCons");

    public final NumberPath<Long> consCd = createNumber("consCd", Long.class);

    public final StringPath consContent = createString("consContent");

    public final QReserveApply reserveApply;

    public QCompCons(String variable) {
        this(CompCons.class, forVariable(variable), INITS);
    }

    public QCompCons(Path<? extends CompCons> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompCons(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompCons(PathMetadata metadata, PathInits inits) {
        this(CompCons.class, metadata, inits);
    }

    public QCompCons(Class<? extends CompCons> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.reserveApply = inits.isInitialized("reserveApply") ? new QReserveApply(forProperty("reserveApply"), inits.get("reserveApply")) : null;
    }

}

