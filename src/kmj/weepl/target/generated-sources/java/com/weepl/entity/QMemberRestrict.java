package com.weepl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberRestrict is a Querydsl query type for MemberRestrict
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberRestrict extends EntityPathBase<MemberRestrict> {

    private static final long serialVersionUID = -978391559L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberRestrict memberRestrict = new QMemberRestrict("memberRestrict");

    public final NumberPath<Long> cd = createNumber("cd", Long.class);

    public final DateTimePath<java.time.LocalDateTime> eddt = createDateTime("eddt", java.time.LocalDateTime.class);

    public final QMember member;

    public final EnumPath<com.weepl.constant.RestrictStatus> status = createEnum("status", com.weepl.constant.RestrictStatus.class);

    public final DateTimePath<java.time.LocalDateTime> stdt = createDateTime("stdt", java.time.LocalDateTime.class);

    public QMemberRestrict(String variable) {
        this(MemberRestrict.class, forVariable(variable), INITS);
    }

    public QMemberRestrict(Path<? extends MemberRestrict> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberRestrict(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberRestrict(PathMetadata metadata, PathInits inits) {
        this(MemberRestrict.class, metadata, inits);
    }

    public QMemberRestrict(Class<? extends MemberRestrict> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

