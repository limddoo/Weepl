package com.weepl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardImg is a Querydsl query type for BoardImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardImg extends EntityPathBase<BoardImg> {

    private static final long serialVersionUID = -889693248L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardImg boardImg = new QBoardImg("boardImg");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath boardDiv = createString("boardDiv");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> imgCd = createNumber("imgCd", Long.class);

    public final StringPath imgName = createString("imgName");

    public final StringPath imgUrl = createString("imgUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final QNotice notice;

    public final StringPath oriImgName = createString("oriImgName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final StringPath repImgYn = createString("repImgYn");

    public QBoardImg(String variable) {
        this(BoardImg.class, forVariable(variable), INITS);
    }

    public QBoardImg(Path<? extends BoardImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardImg(PathMetadata metadata, PathInits inits) {
        this(BoardImg.class, metadata, inits);
    }

    public QBoardImg(Class<? extends BoardImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.notice = inits.isInitialized("notice") ? new QNotice(forProperty("notice")) : null;
    }

}

