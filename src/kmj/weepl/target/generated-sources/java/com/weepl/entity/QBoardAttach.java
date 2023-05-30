package com.weepl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardAttach is a Querydsl query type for BoardAttach
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardAttach extends EntityPathBase<BoardAttach> {

    private static final long serialVersionUID = -830452440L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardAttach boardAttach = new QBoardAttach("boardAttach");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> attachCd = createNumber("attachCd", Long.class);

    public final StringPath attachName = createString("attachName");

    public final StringPath attachUrl = createString("attachUrl");

    public final StringPath boardDiv = createString("boardDiv");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final QNotice notice;

    public final StringPath oriAttachName = createString("oriAttachName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public QBoardAttach(String variable) {
        this(BoardAttach.class, forVariable(variable), INITS);
    }

    public QBoardAttach(Path<? extends BoardAttach> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardAttach(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardAttach(PathMetadata metadata, PathInits inits) {
        this(BoardAttach.class, metadata, inits);
    }

    public QBoardAttach(Class<? extends BoardAttach> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.notice = inits.isInitialized("notice") ? new QNotice(forProperty("notice")) : null;
    }

}

