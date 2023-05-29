package com.weepl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1893778403L;

    public static final QMember member = new QMember("member1");

    public final StringPath addr = createString("addr");

    public final StringPath addrDtl = createString("addrDtl");

    public final StringPath addrPost = createString("addrPost");

    public final StringPath birD = createString("birD");

    public final StringPath birM = createString("birM");

    public final StringPath birY = createString("birY");

    public final NumberPath<Long> cd = createNumber("cd", Long.class);

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final StringPath id = createString("id");

    public final DateTimePath<java.time.LocalDateTime> jdate = createDateTime("jdate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath pwd = createString("pwd");

    public final DateTimePath<java.time.LocalDateTime> qdate = createDateTime("qdate", java.time.LocalDateTime.class);

    public final EnumPath<com.weepl.constant.Role> role = createEnum("role", com.weepl.constant.Role.class);

    public final EnumPath<com.weepl.constant.MemberStatus> status = createEnum("status", com.weepl.constant.MemberStatus.class);

    public final StringPath tel1 = createString("tel1");

    public final StringPath tel2 = createString("tel2");

    public final StringPath tel3 = createString("tel3");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

