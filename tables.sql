-- 회원

CREATE TABLE member(
    userNo NUMBER,
    email VARCHAR2(50) NOT NULL,
    userId VARCHAR2(50) NOT NULL,
    userPwd VARCHAR2(200) NOT NULL,
    userName VARCHAR2(50) NOT NULL,
    nickname VARCHAR2(50),
    created_date DATE DEFAULT SYSDATE,
    updated_date DATE DEFAULT SYSDATE,
    phone VARCHAR2(50),
    enabled NUMBER(1) DEFAULT 1,
    CONSTRAINT pk_member_num PRIMARY KEY(num)
);

CREATE SEQUENCE member_seq
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

-- 공지사항

CREATE TABLE notice(
    num NUMBER,
    userNo NUMBER NOT NULL,
    subject VARCHAR2(255) NOT NULL,
    content VARCHAR2(4000) NOT NULL,
    views NUMBER DEFAULT 0,
    created_date DATE DEFAULT SYSDATE,
    updated_date DATE DEFAULT SYSDATE,
    CONSTRAINT pk_notice_num PRIMARY KEY(num),
    CONSTRAINT fk_notice_userNo FOREIGN KEY(userNo) REFERENCES member(userNo)
);

CREATE SEQUENCE notice_seq
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

-- 이벤트

CREATE TABLE event(
    num NUMBER,
    userNo NUMBER NOT NULL,
    subject VARCHAR2(255) NOT NULL,
    content VARCHAR2(4000) NOT NULL,
    views NUMBER DEFAULT 0,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    created_date DATE DEFAULT SYSDATE,
    updated_date DATE DEFAULT SYSDATE,
    CONSTRAINT pk_notice_num PRIMARY KEY(num),
    CONSTRAINT fk_notice_userNo FOREIGN KEY(userNo) REFERENCES member(userNo)
);

CREATE SEQUENCE event_seq
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

-------------
-- 메뉴 구분

CREATE TABLE menu_category(
    category NUMBER,
    categoryName VARCHAR2(255),
    CONSTRAINT pk_menu_category PRIMARY KEY(category)
);

CREATE SEQUENCE menu_category_seq
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;


CREATE TABLE menu(
    num NUMBER,
    category NUMBER NOT NULL,
    menuName VARCHAR2(255) NOT NULL,
    text VARCHAR2(4000) NOT NULL,
    price NUMBER DEFAULT 0,
    CONSTRAINT pk_menu_num PRIMARY KEY(num),
    CONSTRAINT fk_menu_category FOREIGN KEY(category) REFERENCES menu_category(category)
);

CREATE SEQUENCE menu_seq
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE
    NOCYCLE
    NOCACHE;

----------------
--구매 메뉴