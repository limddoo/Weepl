package com.weepl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotice extends EntityPathBase<Notice> {

    private static final long serialVersionUID = -1855698853L;

    public static final QNotice notice = new QNotice("notice");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<BoardAttach, QBoardAttach> boardAttach = this.<BoardAttach, QBoardAttach>createList("boardAttach", BoardAttach.class, QBoardAttach.class, PathInits.DIRECT2);

    public final ListPath<BoardImg, QBoardImg> boardImg = this.<BoardImg, QBoardImg>createList("boardImg", BoardImg.class, QBoardImg.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDt = _super.modDt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final NumberPath<Long> noticeCd = createNumber("noticeCd", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDt = _super.regDt;

    public final StringPath title = createString("title");

    public QNotice(String variable) {
        super(Notice.class, forVariable(variable));
    }

    public QNotice(Path<? extends Notice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotice(PathMetadata metadata) {
        super(Notice.class, metadata);
    }

}

